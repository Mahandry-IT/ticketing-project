package mg.itu.ticketingproject.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import mg.itu.ticketingproject.data.request.OfferRequest;
import mg.itu.ticketingproject.entity.*;
import mg.itu.ticketingproject.util.JPAUtil;

import java.math.BigDecimal;
import java.util.List;

public class OfferService {

    @PersistenceContext
    EntityManager em;

    public List<Offer> findAll() {
        em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Offer> query = em.createQuery(
                    "SELECT o FROM Offer o JOIN FETCH o.flight f JOIN FETCH f.departureCity JOIN FETCH f.arrivalCity JOIN FETCH o.type ORDER BY o.id ",
                    Offer.class
            );
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public Offer save(Offer offer) {
        em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();

            if (offer.getId() == null) {
                checkInsertConstraints(offer, em);
                em.persist(offer);
            } else {
                checkUpdateConstraints(offer, em);
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

    private void checkInsertConstraints(Offer offer, EntityManager em) {
        TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(o) FROM Offer o " +
                        "WHERE o.flight = :flight AND o.type = :type", Long.class
        );
        query.setParameter("flight", offer.getFlight());
        query.setParameter("type", offer.getType());

        Long count = query.getSingleResult();
        if (count > 0) {
            throw new IllegalArgumentException("Une offre existe déjà pour ce vol et ce type de siège");
        }

        checkSeatQuantity(offer, em);
    }

    private void checkUpdateConstraints(Offer offer, EntityManager em) {
        checkSeatQuantity(offer, em);
    }

    private void checkSeatQuantity(Offer offer, EntityManager em) {
        TypedQuery<Integer> seatQuery = em.createQuery(
                "SELECT ps.quantity FROM PlaneSeat ps " +
                        "WHERE ps.flight = :flight AND ps.type = :type", Integer.class
        );
        seatQuery.setParameter("flight", offer.getFlight());
        seatQuery.setParameter("type", offer.getType());

        Integer maxQuantity;
        try {
            maxQuantity = seatQuery.getSingleResult();
        } catch (NoResultException e) {
            throw new IllegalArgumentException("Aucune configuration de sièges trouvée pour ce vol et ce type");
        }

        if (offer.getNumber() > maxQuantity) {
            throw new IllegalArgumentException(
                    "Le nombre de sièges demandés (" + offer.getNumber() +
                            ") dépasse la quantité disponible (" + maxQuantity + ")"
            );
        }
    }


    public void delete(Integer id) {
        em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Offer offer = em.find(Offer.class, id);
            if (offer != null) {
                em.remove(offer);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public Offer findById(Integer id) {
        em = JPAUtil.getEntityManager();
        try {
            return em.createQuery(
                            "SELECT o FROM Offer o JOIN FETCH o.flight JOIN FETCH o.type WHERE o.id = :id",
                            Offer.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } finally {
            em.close();
        }
    }

    public Offer generateOffer(OfferRequest request) {
        em = JPAUtil.getEntityManager();
        Flight flight = em.find(Flight.class, request.getFlight());
        SeatType seatType = em.find(SeatType.class, request.getSeat_type());

        if(flight == null || seatType == null) {
            throw new IllegalArgumentException("Flight or SeatType not found");
        }

        Integer id = request.getId_offer() != 0 ? request.getId_offer() : null;
        Offer offer = new Offer();
        offer.setId(id);
        offer.setFlight(flight);
        offer.setOffer(BigDecimal.valueOf(request.getDiscount()));
        offer.setNumber(request.getNumber_seats());
        offer.setType(seatType);
        return offer;
    }


}
