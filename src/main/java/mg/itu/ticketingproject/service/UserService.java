package mg.itu.ticketingproject.service;

import data.request.LoginRequest;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import mg.itu.ticketingproject.entity.Appuser;
import mg.itu.ticketingproject.util.JPAUtil;

public class UserService {

    public Appuser findUserByCreditential(LoginRequest request) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Appuser> query = em.createQuery(
                "SELECT u FROM Appuser u WHERE u.username = :username AND u.pwd = :password",
                    Appuser.class);
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }

}
