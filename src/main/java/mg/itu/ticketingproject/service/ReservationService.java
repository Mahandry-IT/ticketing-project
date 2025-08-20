package mg.itu.ticketingproject.service;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import mg.itu.ticketingproject.data.dto.PriceDTO;
import mg.itu.ticketingproject.data.dto.SeatAvailabilityDTO;
import mg.itu.ticketingproject.entity.*;
import mg.itu.ticketingproject.enums.ReservationStatus;
import mg.itu.ticketingproject.util.JPAUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class ReservationService {

    private EntityManager em;

    public List<Reservation> findAll() {
        em = JPAUtil.getEntityManager();
        return em.createQuery("""
                SELECT r FROM Reservation r
                JOIN FETCH r.user
                JOIN FETCH r.flight
                ORDER BY r.id
                """, Reservation.class).getResultList();
    }

    public Long countReservationsByFlight(int flightId) {
        em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Long> query = em.createQuery(
                    "SELECT COUNT(r) " +
                            "FROM Reservation r " +
                            "WHERE r.flight.id = :flightId",
                    Long.class
            );
            query.setParameter("flightId", flightId);
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }



    public List<Reservation> findByUserId(Integer userId) {
        em = JPAUtil.getEntityManager();
        return em.createQuery("""
                SELECT r FROM Reservation r
                JOIN FETCH r.user
                JOIN FETCH r.flight
                WHERE r.user.id = :userId
                ORDER BY r.id
                """, Reservation.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    public List<Reservation> findUpcomingByUserId(Integer userId) {
        em = JPAUtil.getEntityManager();
        return em.createQuery("""
            SELECT r FROM Reservation r
            JOIN FETCH r.user
            JOIN FETCH r.flight f
            JOIN FETCH f.arrivalCity
            JOIN FETCH f.departureCity
            WHERE r.user.id = :userId
              AND f.departureTime > CURRENT_TIMESTAMP
            ORDER BY f.departureTime
            """, Reservation.class)
                .setParameter("userId", userId)
                .getResultList();
    }


    public Reservation findById(Integer id) {
        em = JPAUtil.getEntityManager();
        List<Reservation> list = em.createQuery("""
                SELECT r FROM Reservation r
                JOIN FETCH r.user
                JOIN FETCH r.flight
                JOIN FETCH r.reservationDetails
                WHERE r.id = :id
                """, Reservation.class)
                .setParameter("id", id)
                .getResultList();
        return list.isEmpty() ? null : list.get(0);
    }

    public Reservation save(Integer userId,
                              Integer flightId,
                              Integer reservationIds,
                              LocalDateTime reservationTime,
                              List<Integer> seatTypeIds,
                              List<String> passengerNames,
                              List<Integer> detailIds,
                              List<Integer> ages,
                              List<String> passports,
                              List<BigDecimal> prices) throws Exception {

        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            Reservation myReservation = null;

            if (reservationIds != null && passports.isEmpty()) {
                myReservation = em.find(Reservation.class, reservationIds);
                passports = myReservation.getReservationDetails().stream()
                        .map(ReservationDetail::getPassport)
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList());
            }

            Appuser user = em.find(Appuser.class, userId);
            if (user == null) {
                throw new IllegalArgumentException("Utilisateur introuvable");
            }

            Flight flight = em.find(Flight.class, flightId);
            if (flight == null) {
                throw new IllegalArgumentException("Vol introuvable");
            }

            ReservationParamService reservationParamService = new ReservationParamService();
            ReservationParam param = reservationParamService.getLast();
            if (param == null) {
                param = new ReservationParam();
                param.setCancelTime(0);
                param.setReservationTime(0);
            }
            if (reservationTime.isAfter(flight.getDepartureTime().minusHours(param.getReservationTime()))) {
                throw new IllegalStateException("Réservation impossible : délai dépassé");
            }

            int n = passengerNames.size();
            if (seatTypeIds.size() != n || ages.size() != n || passports.size() != n || prices.size() != n) {
                throw new IllegalArgumentException("Listes passagers incohérentes");
            }

            PlaneSeatService planeSeatService = new PlaneSeatService();

            Map<Integer, Long> counts = seatTypeIds.stream()
                    .collect(Collectors.groupingBy(i -> i, Collectors.counting()));

            counts.forEach((key, value) -> {
                long availableSeats = planeSeatService.placeleft(flightId, key);
                if (availableSeats < value) {
                    throw new IllegalStateException("Pas assez de sièges disponibles pour le type " + key);
                }
            });


            Reservation reservation = new Reservation();
            reservation.setId(reservationIds);
            reservation.setUser(user);
            reservation.setFlight(flight);
            reservation.setReservationTime(reservationTime);
            reservation.setTotalPrice(prices.stream()
                    .reduce(BigDecimal.ZERO, BigDecimal::add));
            reservation.setPassengerCount(passengerNames.size());
            reservation.setStatus(ReservationStatus.NOT_PAID.getStatus());

            if (reservation.getId() != null) {
                if (myReservation != null){
                    if (!myReservation.getStatus().equals(ReservationStatus.NOT_PAID.getStatus())) {
                        throw new IllegalStateException("Impossible de modifier une réservation qui n'est pas en attente");
                    }
                }
                reservation = em.merge(reservation);
            } else {
                em.persist(reservation);
            }

            for (int i = 0; i < passengerNames.size(); i++) {
                SeatType seatType = em.find(SeatType.class, seatTypeIds.get(i));

//                BigDecimal price = planeSeatService.findByIdFlight(flightId).get(seatTypeIds.get(i)).getPrice();

                ReservationDetail detail = new ReservationDetail();

                detail.setId(detailIds != null && i < detailIds.size() ? detailIds.get(i) : null);
                detail.setReservation(reservation);
                detail.setSeatType(seatType);
                detail.setPassengerName(passengerNames.get(i));
                detail.setAge(ages.get(i));
                detail.setPassport(passports.get(i));
                detail.setPrice(prices.get(i));

                if (detail.getId() != null) {
                    detail = em.merge(detail);
                }
                else {
                    em.persist(detail);
                }
            }

            tx.commit();
            return reservation;

        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    /**
     * Récupère les réservations d’un utilisateur dont AU MOINS UN détail a le statut donné.
     * Si onlyFuture = true, on ne garde que les réservations dont le vol n’est pas encore parti.
     * Résultat DISTINCT pour éviter les doublons (plusieurs détails par réservation).
     */
    public List<Reservation> findUserReservationsByDetailStatus(Integer userId) {
        em = JPAUtil.getEntityManager();
        String jpql = """
        SELECT r
        FROM ReservationDetail rd
        JOIN rd.reservation r
        JOIN FETCH r.user u
        JOIN FETCH r.flight f
        WHERE u.id = :uid
        ORDER BY f.departureTime, r.id
    """;

        TypedQuery<Reservation> q = em.createQuery(jpql, Reservation.class)
                .setParameter("uid", userId);

        // Chargement en mémoire + suppression des doublons côté Java
        return q.getResultList().stream().distinct().toList();
    }

    public void updateReservationStatus(Integer reservationId, ReservationStatus status) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = null;

        try {
            tx = em.getTransaction();
            tx.begin();

            String jpql = """
            UPDATE Reservation r
            SET r.status = :status
            WHERE r.id = :id
        """;

            em.createQuery(jpql)
                    .setParameter("status", status.getStatus())
                    .setParameter("id", reservationId)
                    .executeUpdate();

            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public PriceDTO getFinalSeatPrice(Integer flightId, Integer seatTypeId, int passengerAge) {
        em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Object[]> query = em.createQuery(
                    "SELECT ps.price, " +
                            "       ps.price " +
                            "       * (CASE WHEN (" +
                            "            SELECT COUNT(rd2) " +
                            "            FROM ReservationDetail rd2 " +
                            "            WHERE rd2.seatType = ps.type " +
                            "            AND rd2.reservation.flight.id = ps.flight.id " +
                            "            AND rd2.reservation.status <> 'CANCELED' " +
                            "         ) < COALESCE(o.number, 0) " +
                            "         THEN (1 - COALESCE(o.offer, 0)/100) " +
                            "         ELSE 1 " +
                            "       END) " +
                            "       * (1 - COALESCE(ao.offer, 0)/100) " +
                            "FROM PlaneSeat ps " +
                            "LEFT JOIN Offer o ON o.type = ps.type AND o.flight.id = ps.flight.id " +
                            "LEFT JOIN AgeOffer ao ON ao.category.id = (" +
                            "    SELECT ac.id FROM AgeCategory ac " +
                            "    WHERE :passengerAge BETWEEN ac.minimal AND ac.maximal" +
                            ") " +
                            "WHERE ps.flight.id = :flightId AND ps.type.id = :seatTypeId",
                    Object[].class
            );

            query.setParameter("flightId", flightId);
            query.setParameter("seatTypeId", seatTypeId);
            query.setParameter("passengerAge", passengerAge);

            List<Object[]> results = query.getResultList();
            if (results.isEmpty()) {
                return new PriceDTO(BigDecimal.ZERO, BigDecimal.ZERO); // ou null selon ton choix
            }

            Object[] result = results.get(0);
            BigDecimal basePrice = (BigDecimal) result[0];
            BigDecimal finalPrice = (BigDecimal) result[1];

            return new PriceDTO(finalPrice, basePrice);

        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    public void cancelReservation(Date date) {
        em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            LocalDateTime dateLDT = date.toLocalDate().atStartOfDay();
            tx.begin();
            String jpql = """
                UPDATE Reservation r
                SET r.status = :status
                WHERE r.reservationTime <= :date
                AND r.status <> 'PAID'
                AND r.status <> 'CANCELED'
            """;
            em.createQuery(jpql)
                    .setParameter("date", dateLDT)
                    .setParameter("status", ReservationStatus.CANCELED.getStatus())
                    .executeUpdate();
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

}
