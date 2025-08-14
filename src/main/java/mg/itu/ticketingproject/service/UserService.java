package mg.itu.ticketingproject.service;

public class UserService {

    public List<Flight> findAll() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Flight> query = em.createQuery(
                "SELECT f FROM Flight f JOIN FETCH f.departureCity JOIN FETCH f.arrivalCity JOIN FETCH f.plane ORDER BY f.departureTime",
                Flight.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

}
