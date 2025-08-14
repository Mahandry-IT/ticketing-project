package mg.itu.ticketingproject.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import mg.itu.ticketingproject.entity.Plane;
import mg.itu.ticketingproject.util.JPAUtil;

import java.util.List;

public class PlaneService {

    @PersistenceContext
    EntityManager em;

    public List<Plane> findAll() {
        em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Plane> query = em.createQuery(
                    "SELECT p FROM Plane p JOIN FETCH p.model ORDER BY p.name",
                    Plane.class
            );
            return query.getResultList();
        } finally {
            em.close();
        }
    }

}
