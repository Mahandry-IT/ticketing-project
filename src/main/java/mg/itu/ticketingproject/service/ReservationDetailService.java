package mg.itu.ticketingproject.service;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import mg.itu.ticketingproject.entity.Reservation;
import mg.itu.ticketingproject.entity.ReservationDetail;
import mg.itu.ticketingproject.entity.SeatType;
import mg.itu.ticketingproject.util.JPAUtil;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

public class ReservationDetailService {

    @PersistenceContext
    private EntityManager em;

    private static final Set<String> ALLOWED_STATUS = Set.of("CONFIRMED", "CANCELED", "PENDING");

    /** CREATE unitaire — status par défaut = PENDING */
    @Transactional
    public ReservationDetail create(Integer reservationId,
                                    Integer seatTypeId,
                                    String passengerName,
                                    int age,
                                    String passport,
                                    BigDecimal price,
                                    String statusOrNull) {
        em = JPAUtil.getEntityManager();
        Objects.requireNonNull(price, "price requis");
        Objects.requireNonNull(passport, "passport requis");

        String status = (statusOrNull == null) ? "PENDING" : statusOrNull;
        if (!ALLOWED_STATUS.contains(status)) {
            throw new IllegalArgumentException("Status invalide: " + status);
        }

        Reservation reservation = em.find(Reservation.class, reservationId, LockModeType.NONE);
        if (reservation == null) throw new IllegalArgumentException("Reservation introuvable: " + reservationId);

        // Vérifier qu’on ne dépasse pas passengerCount
        long currentCount = em.createQuery("""
            select count(d) from ReservationDetail d
            where d.reservation.id = :rid
        """, Long.class)
                .setParameter("rid", reservationId)
                .getSingleResult();

        if (currentCount + 1 > reservation.getPassengerCount()) {
            throw new IllegalStateException("Nombre de détails dépassé (passengerCount=" + reservation.getPassengerCount() + ")");
        }

        SeatType seatType = em.find(SeatType.class, seatTypeId, LockModeType.NONE);
        if (seatType == null) throw new IllegalArgumentException("SeatType introuvable: " + seatTypeId);

        ReservationDetail rd = new ReservationDetail();
        rd.setReservation(reservation);
        rd.setSeatType(seatType);
        rd.setPassengerName(passengerName);
        rd.setAge(age);
        rd.setPassport(passport);
        rd.setPrice(price);
        rd.setStatus(status); // <= PENDING par défaut

        em.persist(rd);
        em.flush();
        return rd;
    }

    /** READ by id (fetch associations utiles) */
    public ReservationDetail findById(Integer id) {
        em = JPAUtil.getEntityManager();
        List<ReservationDetail> list = em.createQuery("""
                SELECT rd
                FROM ReservationDetail rd
                JOIN FETCH rd.reservation r
                JOIN FETCH r.flight f
                JOIN FETCH rd.seatType st
                WHERE rd.id = :id
                """, ReservationDetail.class)
                .setParameter("id", id)
                .getResultList();
        return list.isEmpty() ? null : list.get(0);
    }

    /** READ all paginé (si besoin) */
    public List<ReservationDetail> findAll(int page, int size) {
        em = JPAUtil.getEntityManager();
        return em.createQuery("""
                SELECT rd
                FROM ReservationDetail rd
                JOIN FETCH rd.reservation r
                JOIN FETCH r.flight f
                JOIN FETCH rd.seatType st
                ORDER BY rd.id
                """, ReservationDetail.class)
                .setFirstResult(Math.max(0, page) * Math.max(1, size))
                .setMaxResults(Math.max(1, size))
                .getResultList();
    }

    /** READ par réservation */
    public List<ReservationDetail> findByReservation(Integer reservationId) {
        em = JPAUtil.getEntityManager();
        return em.createQuery("""
                SELECT rd
                FROM ReservationDetail rd
                JOIN FETCH rd.reservation r
                JOIN FETCH r.flight f
                JOIN FETCH rd.seatType st
                WHERE r.id = :rid
                ORDER BY rd.id
                """, ReservationDetail.class)
                .setParameter("rid", reservationId)
                .getResultList();
    }

    /** READ par vol */
    public List<ReservationDetail> findByFlight(Integer flightId) {
        em = JPAUtil.getEntityManager();
        return em.createQuery("""
                SELECT rd
                FROM ReservationDetail rd
                JOIN FETCH rd.reservation r
                JOIN FETCH r.flight f
                JOIN FETCH rd.seatType st
                WHERE f.id = :fid
                ORDER BY rd.id
                """, ReservationDetail.class)
                .setParameter("fid", flightId)
                .getResultList();
    }

    /** READ — seulement les détails avec un status donné ET sur des vols futurs */
    public List<ReservationDetail> findAllFutureByStatus(String status, int page, int size) {
        em = JPAUtil.getEntityManager();
        if (!ALLOWED_STATUS.contains(status)) {
            throw new IllegalArgumentException("Status invalide: " + status);
        }
        LocalDateTime now = LocalDateTime.now();

        return em.createQuery("""
                SELECT rd
                FROM ReservationDetail rd
                JOIN FETCH rd.reservation r
                JOIN FETCH r.flight f
                JOIN FETCH rd.seatType st
                WHERE rd.status = :status
                  AND f.departureTime > :now
                ORDER BY f.departureTime, rd.id
                """, ReservationDetail.class)
                .setParameter("status", status)
                .setParameter("now", now)
                .setFirstResult(Math.max(0, page) * Math.max(1, size))
                .setMaxResults(Math.max(1, size))
                .getResultList();
    }

    /** DELETE */
    @Transactional
    public void delete(Integer id) {
        em = JPAUtil.getEntityManager();
        ReservationDetail rd = em.find(ReservationDetail.class, id, LockModeType.PESSIMISTIC_WRITE);
        if (rd != null) em.remove(rd);
    }

    public List<ReservationDetail> findAllFutureByStatus(String status){
        em = JPAUtil.getEntityManager();
        if (!ALLOWED_STATUS.contains(status)) {
            throw new IllegalArgumentException("Status invalide: " + status);
        }
        LocalDateTime now = LocalDateTime.now();

        return em.createQuery("""
            SELECT rd
            FROM ReservationDetail rd
            JOIN FETCH rd.reservation r
            JOIN FETCH r.flight f
            JOIN FETCH rd.seatType st
            WHERE rd.status = :status
              AND f.departureTime > :now
            ORDER BY f.departureTime, rd.id
            """, ReservationDetail.class)
                .setParameter("status", status)
                .setParameter("now", now)
                .getResultList();
    }

    public List<ReservationDetail> findAllFutureByStatusAndReservation(String status, Integer reservationId) {
        em = JPAUtil.getEntityManager();
        if (!ALLOWED_STATUS.contains(status)) {
            throw new IllegalArgumentException("Status invalide: " + status);
        }
        LocalDateTime now = LocalDateTime.now();

        return em.createQuery("""
            SELECT rd
            FROM ReservationDetail rd
            JOIN FETCH rd.reservation r
            JOIN FETCH r.flight f
            JOIN FETCH rd.seatType st
            WHERE rd.status = :status
              AND f.departureTime > :now
                AND r.id = :reservationId
            ORDER BY f.departureTime, rd.id
            """, ReservationDetail.class)
                .setParameter("status", status)
                .setParameter("now", now)
                .setParameter("reservationId", reservationId)
                .getResultList();
    }

    @Transactional
    public ReservationDetail update(Integer detailId,
                                    Integer seatTypeId,        // nullable -> pas de changement
                                    String passengerName,      // nullable -> pas de changement
                                    Integer age,               // nullable -> pas de changement
                                    String passport,           // nullable -> pas de changement
                                    BigDecimal price,          // nullable -> pas de changement (sauf CANCELED)
                                    String statusOrNull) {     // nullable -> pas de changement
        em = JPAUtil.getEntityManager();
        ReservationDetail rd = em.find(ReservationDetail.class, detailId, LockModeType.PESSIMISTIC_WRITE);
        if (rd == null) throw new IllegalArgumentException("ReservationDetail introuvable: " + detailId);

        // maj simples
        if (seatTypeId != null) {
            SeatType st = em.find(SeatType.class, seatTypeId, LockModeType.NONE);
            if (st == null) throw new IllegalArgumentException("SeatType introuvable: " + seatTypeId);
            rd.setSeatType(st);
        }
        if (passengerName != null) rd.setPassengerName(passengerName);
        if (age != null) rd.setAge(age);
        if (passport != null) rd.setPassport(passport);
        if (price != null) rd.setPrice(price);

        // statut : si CANCELED => price = 0 (toujours)
        if (statusOrNull != null) {
            if (!ALLOWED_STATUS.contains(statusOrNull)) {
                throw new IllegalArgumentException("Status invalide: " + statusOrNull);
            }
            rd.setStatus(statusOrNull);
            if ("CANCELED".equals(statusOrNull)) {
                rd.setPrice(BigDecimal.ZERO);
            }
        }

        em.flush();

        // Recalculer la réservation mère (total + passengerCount)
        recomputeReservationAggregates(rd.getReservation().getId());

        return rd;
    }

    /* ---- Variante utilitaire : changer juste le statut ---- */
    @Transactional
    public ReservationDetail changeStatus(Integer detailId, String status) {
        em = JPAUtil.getEntityManager();
        if (!ALLOWED_STATUS.contains(status)) {
            throw new IllegalArgumentException("Status invalide: " + status);
        }
        ReservationDetail rd = em.find(ReservationDetail.class, detailId, LockModeType.PESSIMISTIC_WRITE);
        if (rd == null) throw new IllegalArgumentException("ReservationDetail introuvable: " + detailId);

        rd.setStatus(status);
        if ("CANCELED".equals(status)) {
            rd.setPrice(BigDecimal.ZERO);
        }
        em.flush();
        recomputeReservationAggregates(rd.getReservation().getId());
        return rd;
    }

    /* ---- Helper : recalcule totalPrice et passengerCount sur la réservation mère ---- */
    private void recomputeReservationAggregates(Integer reservationId) {
        em = JPAUtil.getEntityManager();
        // verrouiller la réservation pour éviter une écriture concurrente
        Reservation r = em.find(Reservation.class, reservationId, LockModeType.PESSIMISTIC_WRITE);
        if (r == null) return;

        BigDecimal total = em.createQuery("""
                        select coalesce(sum(d.price), 0)
                        from ReservationDetail d
                        where d.reservation.id = :rid
                        """, BigDecimal.class)
                .setParameter("rid", reservationId)
                .getSingleResult();

        Long cnt = em.createQuery("""
                        select count(d)
                        from ReservationDetail d
                        where d.reservation.id = :rid
                        """, Long.class)
                .setParameter("rid", reservationId)
                .getSingleResult();

        r.setTotalPrice(total);
        r.setPassengerCount(cnt.intValue());
        em.flush();
    }

}
