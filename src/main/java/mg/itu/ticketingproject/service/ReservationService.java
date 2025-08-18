package mg.itu.ticketingproject.service;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import mg.itu.ticketingproject.entity.*;
import mg.itu.ticketingproject.util.JPAUtil;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
                WHERE r.id = :id
                """, Reservation.class)
                .setParameter("id", id)
                .getResultList();
        return list.isEmpty() ? null : list.get(0);
    }

    public Reservation create(Integer userId,
                              Integer flightId,
                              LocalDateTime reservationTime,
                              List<Integer> seatTypeIds,
                              List<String> passengerNames,
                              List<Integer> ages,
                              List<String> passports,
                              List<BigDecimal> prices) throws Exception {

        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

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
            if (reservationTime.isBefore(flight.getDepartureTime().minusHours(param.getReservationTime()))) {
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
            reservation.setUser(user);
            reservation.setFlight(flight);
            reservation.setReservationTime(reservationTime);
            reservation.setTotalPrice(prices.stream()
                    .reduce(BigDecimal.ZERO, BigDecimal::add));

            em.persist(reservation);



            for (int i = 0; i < passengerNames.size(); i++) {
                SeatType seatType = em.find(SeatType.class, seatTypeIds.get(i));

//                BigDecimal price = planeSeatService.findByIdFlight(flightId).get(seatTypeIds.get(i)).getPrice();

                ReservationDetail detail = new ReservationDetail();
                detail.setReservation(reservation);
                detail.setSeatType(seatType);
                detail.setPassengerName(passengerNames.get(i));
                detail.setAge(ages.get(i));
                detail.setPassport(passports.get(i));
                detail.setPrice(prices.get(i));
                detail.setStatus("PENDING");

                em.persist(detail);
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
    public List<Reservation> findUserReservationsByDetailStatus(Integer userId,
                                                                String status,
                                                                boolean onlyFuture) {
        em = JPAUtil.getEntityManager();
        String base = """
            SELECT DISTINCT r
            FROM ReservationDetail rd
            JOIN rd.reservation r
            JOIN FETCH r.user u
            JOIN FETCH r.flight f
            WHERE u.id = :uid
              AND rd.status = :status
        """;

        String withFuture = base + " AND f.departureTime > :now ORDER BY f.departureTime, r.id";
        String withoutFuture = base + " ORDER BY f.departureTime, r.id";

        TypedQuery<Reservation> q = em.createQuery(onlyFuture ? withFuture : withoutFuture, Reservation.class)
                .setParameter("uid", userId)
                .setParameter("status", status);

        if (onlyFuture) {
            q.setParameter("now", LocalDateTime.now());
        }

        return q.getResultList();
    }

}
