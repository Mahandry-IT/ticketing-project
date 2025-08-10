package mg.itu.ticketingproject.service;

import mg.itu.ticketingproject.entity.Flight;
import mg.itu.ticketingproject.entity.Offer;
import mg.itu.ticketingproject.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class OfferService {

    public List<Offer> findAll() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Offer> query = em.createQuery(
                "SELECT o FROM Offer o JOIN FETCH o.flight f JOIN FETCH f.departureCity JOIN FETCH f.arrivalCity JOIN FETCH o.seatType WHERE o.isActive = true ORDER BY o.createdAt DESC", 
                Offer.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public List<Offer> findByFlight(Flight flight) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Offer> query = em.createQuery(
                "SELECT o FROM Offer o JOIN FETCH o.seatType WHERE o.flight = :flight AND o.isActive = true", 
                Offer.class);
            query.setParameter("flight", flight);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public Offer findById(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery(
                "SELECT o FROM Offer o JOIN FETCH o.flight f JOIN FETCH f.departureCity JOIN FETCH f.arrivalCity JOIN FETCH o.seatType WHERE o.idOffer = :id", 
                Offer.class)
                .setParameter("id", id)
                .getSingleResult();
        } finally {
            em.close();
        }
    }

    public Offer save(Offer offer) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            if (offer.getIdOffer() == null) {
                em.persist(offer);
            } else {
                offer = em.merge(offer);
            }
            em.getTransaction().commit();
            return offer;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public void delete(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Offer offer = em.find(Offer.class, id);
            if (offer != null) {
                offer.setIsActive(false);
                em.merge(offer);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }
}
