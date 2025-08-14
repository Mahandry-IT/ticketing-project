package mg.itu.ticketingproject.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import mg.itu.ticketingproject.entity.City;
import mg.itu.ticketingproject.util.JPAUtil;

import java.util.List;

public class CityService {

    @PersistenceContext
    EntityManager em;

    public List<City> findAll() {
        em = JPAUtil.getEntityManager();
        try {
            TypedQuery<City> query = em.createQuery(
                    "SELECT c FROM City c ORDER BY c.name",
                    City.class
            );
            return query.getResultList();
        } finally {
            em.close();
        }
    }

}
