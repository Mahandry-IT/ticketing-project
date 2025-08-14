package mg.itu.ticketingproject.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import mg.itu.ticketingproject.entity.ReservationParam;
import mg.itu.ticketingproject.util.JPAUtil;

public class ReservationParamService {
    @PersistenceContext
    EntityManager em;

    public ReservationParam createOrUpdate(ReservationParam param) {
        em = JPAUtil.getEntityManager();
        ReservationParam existing = getLast();

        if (existing != null) {
            existing.setCancelTime(param.getCancelTime());
            existing.setReservationTime(param.getReservationTime());
            em.getTransaction().commit();
            return em.merge(existing);
        } else {
            // Create new
            em.persist(param);
            em.getTransaction().commit();
            return param;
        }
    }

    public ReservationParam getLast() {
        em = JPAUtil.getEntityManager();
        try {
            return em.createQuery(
                            "SELECT rp FROM ReservationParam rp ORDER BY rp.id DESC",
                            ReservationParam.class
                    )
                    .setMaxResults(1)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public ReservationParam createOrUpdate(Integer cancelTime, Integer reservationTime) {
        ReservationParam param = new ReservationParam();
        param.setCancelTime(cancelTime);
        param.setReservationTime(reservationTime);
        return createOrUpdate(param);
    }

}
