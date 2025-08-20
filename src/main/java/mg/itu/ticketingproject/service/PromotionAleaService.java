package mg.itu.ticketingproject.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import mg.itu.ticketingproject.entity.*;
import mg.itu.ticketingproject.util.JPAUtil;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PromotionAleaService {

    @PersistenceContext
    EntityManager em;

    ReservationService service = new ReservationService();

    public List<PromotionAlea> findAll() {
        em = JPAUtil.getEntityManager();
        try {
            TypedQuery<PromotionAlea> query = em.createQuery(
                    "SELECT p FROM PromotionAlea p ORDER BY p.finalDate",
                    PromotionAlea.class
            );
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public List<PromotionAlea> findExpiredPromotions(Date date) {
        em = JPAUtil.getEntityManager();
        try {
            LocalDate localDate = date.toLocalDate();

            TypedQuery<PromotionAlea> query = em.createQuery(
                    "SELECT p FROM PromotionAlea p WHERE p.finalDate <= :date ORDER BY p.finalDate",
                    PromotionAlea.class
            );
            query.setParameter("date", localDate);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public PromotionAlea save(PromotionAlea promotion) {
        em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            if (promotion.getId() == null) {
                em.persist(promotion);
            } else {
                promotion = em.merge(promotion);
            }
            em.getTransaction().commit();
            return promotion;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Transactional
    public void updateReservationAndPromotions(Date today) {
        em = JPAUtil.getEntityManager();
        LocalDate localDate = today.toLocalDate();

        // Étape 1 : Récupérer les promotions expirées AVANT d'annuler
        List<PromotionAlea> expiredPromotions = findExpiredPromotions(today);

        Map<String, Integer> placesToRedistribute = new HashMap<>();

        for (PromotionAlea expired : expiredPromotions) {
            int count = findNotPayedReservation(
                    expired.getIdFlight().getId(),
                    expired.getIdType().getId(),
                    today
            );

            if (count > 0) {
                String key = expired.getIdFlight().getId() + "-" + expired.getIdType().getId();
                placesToRedistribute.put(key, count);
            }
        }

        // Étape 2 : Annuler les réservations expirées
        service.cancelReservation(today);

        // Étape 3 : Redistribuer les places aux promotions suivantes
        for (PromotionAlea expired : expiredPromotions) {
            String key = expired.getIdFlight().getId() + "-" + expired.getIdType().getId();
            Integer count = placesToRedistribute.get(key);

            if (count != null && count > 0) {
                List<PromotionAlea> nextPromos = findOtherPromotionAlea(
                        expired.getIdFlight().getId(),
                        expired.getIdType().getId(),
                        today
                );

                if (!nextPromos.isEmpty()) {
                    PromotionAlea nextPromo = nextPromos.get(0);
                    nextPromo.setNumber(nextPromo.getNumber() + count);
                    save(nextPromo);
                }
            }
        }
    }

    public List<PromotionAlea> findOtherPromotionAlea(Integer flightId, Integer seatId, Date today) {
        em = JPAUtil.getEntityManager();
        try {
            TypedQuery<PromotionAlea> query = em.createQuery(
                    "SELECT p FROM PromotionAlea p " +
                            "WHERE p.finalDate > :date " +
                            "AND p.idFlight.id = :flightId " +
                            "AND p.idType.id = :seatId " +
                            "ORDER BY p.finalDate ASC",
                    PromotionAlea.class
            );
            query.setParameter("date", today.toLocalDate());
            query.setParameter("flightId", flightId);
            query.setParameter("seatId", seatId);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public Integer findNotPayedReservation(Integer flightId, Integer seatId, Date today) {
        em = JPAUtil.getEntityManager();
        try {
            LocalDateTime todayLdt = today.toLocalDate().atStartOfDay();
            TypedQuery<Long> query = em.createQuery(
                    "SELECT COUNT(rd) " +
                            "FROM ReservationDetail rd " +
                            "JOIN rd.reservation r " +
                            "WHERE r.status = 'NOT PAID' " +
                            "AND r.flight.id = :flightId " +
                            "AND rd.seatType.id = :seatId " +
                            "AND r.reservationTime <= :today",
                    Long.class
            );
            query.setParameter("flightId", flightId);
            query.setParameter("seatId", seatId);
            query.setParameter("today", todayLdt);
            return query.getSingleResult().intValue();
        } finally {
            em.close();
        }
    }
}