package mg.itu.ticketingproject.service;

import data.request.LoginRequest;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import mg.itu.ticketingproject.entity.Appuser;
import mg.itu.ticketingproject.util.JPAUtil;

import java.util.Optional;

public class UserService {

    public Optional<Appuser> findUserByCreditential(LoginRequest loginRequest) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            Appuser user = em.createQuery(
                            "SELECT u FROM Appuser u WHERE u.email = :email AND u.pwd = :password", Appuser.class)
                    .setParameter("email", loginRequest.getEmail())
                    .setParameter("password", loginRequest.getPassword())
                    .getSingleResult();
            return Optional.of(user);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }


}
