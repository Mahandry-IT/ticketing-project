package mg.itu.ticketingproject.service;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import mg.itu.ticketingproject.entity.*;
import mg.itu.ticketingproject.util.JPAUtil;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class ReservationService {

    @PersistenceContext
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

    /**
     * Crée une réservation + détails, sans DTO.
     * Tous les tableaux/listes doivent avoir la même taille (>0).
     * Status des détails = PENDING par défaut.
     */
    @Transactional
    public Reservation create(Integer userId,
                              Integer flightId,
                              LocalDateTime reservationTime, // peut être null => now
                              List<Integer> seatTypeIds,
                              List<String> passengerNames,
                              List<Integer> ages,
                              List<String> passports,
                              List<BigDecimal> prices) {
        em = JPAUtil.getEntityManager();
        // ---- validations basiques
        Objects.requireNonNull(seatTypeIds, "seatTypeIds requis");
        Objects.requireNonNull(passengerNames, "passengerNames requis");
        Objects.requireNonNull(ages, "ages requis");
        Objects.requireNonNull(passports, "passports requis");
        Objects.requireNonNull(prices, "prices requis");

        int n = seatTypeIds.size();
        if (n == 0 || n != passengerNames.size() || n != ages.size() || n != passports.size() || n != prices.size()) {
            throw new IllegalArgumentException("Les listes doivent être non vides et de même taille");
        }

        // ---- résolutions
        Appuser user = em.find(Appuser.class, userId, LockModeType.NONE);
        if (user == null) throw new IllegalArgumentException("appuser introuvable: " + userId);

        Flight flight = em.find(Flight.class, flightId, LockModeType.NONE);
        if (flight == null) throw new IllegalArgumentException("flight introuvable: " + flightId);

        // ---- créer la réservation (sans détails d'abord)
        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setFlight(flight);
        reservation.setReservationTime(reservationTime != null ? reservationTime : LocalDateTime.now());
        reservation.setPassengerCount(n);
        reservation.setTotalPrice(BigDecimal.ZERO); // temporaire

        em.persist(reservation);
        // em.flush(); // décommente si tu veux forcer l’ID tout de suite

        // ---- créer les détails (status = PENDING)
        BigDecimal total = BigDecimal.ZERO;
        for (int i = 0; i < n; i++) {
            Integer stId = seatTypeIds.get(i);
            String name = passengerNames.get(i);
            Integer age = ages.get(i);
            String passport = passports.get(i);
            BigDecimal price = prices.get(i);

            if (stId == null || age == null || passport == null || price == null) {
                throw new IllegalArgumentException("Champs manquants dans la ligne " + i);
            }

            SeatType seatType = em.find(SeatType.class, stId, LockModeType.NONE);
            if (seatType == null) throw new IllegalArgumentException("SeatType introuvable: " + stId);

            ReservationDetail rd = new ReservationDetail();
            rd.setReservation(reservation);       // parent déjà MANAGED
            rd.setSeatType(seatType);
            rd.setPassengerName(name);
            rd.setAge(age);
            rd.setPassport(passport);
            rd.setPrice(price);
            rd.setStatus("PENDING");              // défaut demandé

            em.persist(rd);
            total = total.add(price);
        }

        // ---- mettre à jour le total
        reservation.setTotalPrice(total);

        return reservation; // flush/commit auto à la fin de la TX
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
