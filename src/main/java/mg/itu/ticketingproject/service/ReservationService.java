package mg.itu.ticketingproject.service;

import mg.itu.ticketingproject.entity.Reservation;
import mg.itu.ticketingproject.entity.User;
import mg.itu.ticketingproject.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.UUID;

public class ReservationService {

    public List<Reservation> findByUser(User user) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Reservation> query = em.createQuery(
                "SELECT r FROM Reservation r JOIN FETCH r.flight f JOIN FETCH f.departureCity JOIN FETCH f.arrivalCity WHERE r.user = :user ORDER BY r.reservationTime DESC", 
                Reservation.class);
            query.setParameter("user", user);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public List<Reservation> findAll() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Reservation> query = em.createQuery(
                "SELECT r FROM Reservation r JOIN FETCH r.user JOIN FETCH r.flight f JOIN FETCH f.departureCity JOIN FETCH f.arrivalCity ORDER BY r.reservationTime DESC", 
                Reservation.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public Reservation findById(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery(
                "SELECT r FROM Reservation r JOIN FETCH r.user JOIN FETCH r.flight f JOIN FETCH f.departureCity JOIN FETCH f.arrivalCity WHERE r.idReservation = :id", 
                Reservation.class)
                .setParameter("id", id)
                .getSingleResult();
        } finally {
            em.close();
        }
    }

    public Reservation save(Reservation reservation) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            if (reservation.getIdReservation() == null) {
                // Generate reservation number
                reservation.setReservationNumber(generateReservationNumber());
                em.persist(reservation);
            } else {
                reservation = em.merge(reservation);
            }
            em.getTransaction().commit();
            return reservation;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public void cancelReservation(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Reservation reservation = em.find(Reservation.class, id);
            if (reservation != null) {
                reservation.setStatus("CANCELLED");
                em.merge(reservation);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    private String generateReservationNumber() {
        return "RES" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
