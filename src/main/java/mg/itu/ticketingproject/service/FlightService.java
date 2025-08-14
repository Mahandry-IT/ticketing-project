package mg.itu.ticketingproject.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import mg.itu.ticketingproject.data.request.FlightRequest;
import mg.itu.ticketingproject.entity.City;
import mg.itu.ticketingproject.entity.Flight;
import mg.itu.ticketingproject.entity.Plane;
import mg.itu.ticketingproject.entity.PlaneSeat;
import mg.itu.ticketingproject.util.JPAUtil;

import java.time.LocalDateTime;
import java.util.List;

public class FlightService {

    @PersistenceContext
    EntityManager em;
    
    public List<Flight> findAll() {
        em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Flight> query = em.createQuery(
                "SELECT f FROM Flight f JOIN FETCH f.departureCity JOIN FETCH f.arrivalCity JOIN FETCH f.plane ORDER BY f.departureTime",
                Flight.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public List<Flight> getTodayFlight() {
        em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Flight> query = em.createQuery(
                    "SELECT f FROM Flight f JOIN FETCH f.departureCity JOIN FETCH f.arrivalCity JOIN FETCH f.plane WHERE f.departureTime =  current_timestamp ORDER BY f.departureTime",
                    Flight.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public Flight findById(Integer id) {
        em = JPAUtil.getEntityManager();
        try {
            return em.createQuery(
                "SELECT f FROM Flight f JOIN FETCH f.departureCity JOIN FETCH f.arrivalCity JOIN FETCH f.plane WHERE f.id = :id",
                Flight.class)
                .setParameter("id", id)
                .getSingleResult();
        } finally {
            em.close();
        }
    }

    public List<Flight> searchFlights(String departureCity, String arrivalCity,
                                      LocalDateTime departureDate, LocalDateTime arrivalDate) {
        em = JPAUtil.getEntityManager();
        try {
            StringBuilder jpql = new StringBuilder(
                    "SELECT f FROM Flight f JOIN FETCH f.departureCity dc JOIN FETCH f.arrivalCity ac JOIN FETCH f.plane WHERE 1=1");

            if (departureCity != null && !departureCity.trim().isEmpty()) {
                jpql.append(" AND LOWER(dc.name) LIKE LOWER(:departureCity)");
            }
            if (arrivalCity != null && !arrivalCity.trim().isEmpty()) {
                jpql.append(" AND LOWER(ac.name) LIKE LOWER(:arrivalCity)");
            }
            if (departureDate != null) {
                jpql.append(" AND f.departureTime >= :departureDate");
            }
            if (arrivalDate != null) {
                jpql.append(" AND f.arrivalTime <= :arrivalDate");
            }

            jpql.append(" ORDER BY f.departureTime");

            TypedQuery<Flight> query = em.createQuery(jpql.toString(), Flight.class);

            if (departureCity != null && !departureCity.trim().isEmpty()) {
                query.setParameter("departureCity", "%" + departureCity.trim() + "%");
            }
            if (arrivalCity != null && !arrivalCity.trim().isEmpty()) {
                query.setParameter("arrivalCity", "%" + arrivalCity.trim() + "%");
            }
            if (departureDate != null) {
                query.setParameter("departureDate", departureDate);
            }
            if (arrivalDate != null) {
                query.setParameter("arrivalDate", arrivalDate);
            }

            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public Flight save(Flight flight) {
        em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            if (flight.getId() == null) {
                em.persist(flight);
            } else {
                flight = em.merge(flight);
            }
            em.getTransaction().commit();
            return flight;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public void delete(Integer id) {
        em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Flight flight = em.find(Flight.class, id);
            if (flight != null) {
                em.remove(flight);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public List<City> findAllCities() {
        em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT c FROM City c ORDER BY c.name", City.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public Flight generateFlight(FlightRequest request) {
        em = JPAUtil.getEntityManager();
        City departureCity = em.find(City.class, Integer.parseInt(request.getDepartureCity()));
        City arrivalCity = em.find(City.class, Integer.parseInt(request.getArrivalCity()));
        Plane plane = em.find(Plane.class, Integer.parseInt(request.getPlane()));

        if(departureCity == null || arrivalCity == null || plane == null) {
            throw new IllegalArgumentException("City or Plane not found");
        }

        Flight flight = new Flight();
        flight.setDepartureCity(departureCity);
        flight.setArrivalCity(arrivalCity);
        flight.setPlane(plane);
        flight.setDepartureTime(LocalDateTime.parse(request.getDepartureTime()));
        flight.setArrivalTime(LocalDateTime.parse(request.getArrivalTime()));
        return flight;
    }

//    public PlaneSeat generatePlaneSeat(FlightRequest request) {
//        em = JPAUtil.getEntityManager();
//        City departureCity = em.find(City.class, Integer.parseInt(request.getDepartureCity()));
//        City arrivalCity = em.find(City.class, Integer.parseInt(request.getArrivalCity()));
//        Plane plane = em.find(Plane.class, Integer.parseInt(request.getPlane()));
//
//        if(departureCity == null || arrivalCity == null || plane == null) {
//            throw new IllegalArgumentException("City or Plane not found");
//        }
//
//        Flight flight = new Flight();
//        flight.setDepartureCity(departureCity);
//        flight.setArrivalCity(arrivalCity);
//        flight.setPlane(plane);
//        flight.setDepartureTime(LocalDateTime.parse(request.getDepartureTime()));
//        flight.setArrivalTime(LocalDateTime.parse(request.getArrivalTime()));
//        return flight;
//    }
}
