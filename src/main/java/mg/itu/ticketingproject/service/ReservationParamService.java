package mg.itu.ticketingproject.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceContext;
import mg.itu.ticketingproject.entity.ReservationParam;
import mg.itu.ticketingproject.util.JPAUtil;

public class ReservationParamService {

    public ReservationParam createOrUpdate(ReservationParam param) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        ReservationParam result;

        try {
            tx.begin();

            ReservationParam existing = getLast();

            if (existing != null) {
                existing.setCancelTime(param.getCancelTime());
                existing.setReservationTime(param.getReservationTime());
                result = em.merge(existing);  // merge DANS la transaction
            } else {
                em.persist(param);
                result = param;
            }

            tx.commit();  // commit après persist/merge
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }

        return result;
    }


    public ReservationParam getLast() {
        EntityManager em = JPAUtil.getEntityManager();
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
