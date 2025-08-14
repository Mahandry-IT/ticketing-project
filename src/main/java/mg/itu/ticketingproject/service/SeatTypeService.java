package mg.itu.ticketingproject.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import mg.itu.ticketingproject.entity.SeatType;
import mg.itu.ticketingproject.util.JPAUtil;

import java.util.List;

public class SeatTypeService {

    @PersistenceContext
    EntityManager em;

    public List<SeatType> findAll() {
        em = JPAUtil.getEntityManager();
        try {
            TypedQuery<SeatType> query = em.createQuery(
                    "SELECT s FROM SeatType s ORDER BY s.name",
                    SeatType.class
            );
            return query.getResultList();
        } finally {
            em.close();
        }
    }

}
