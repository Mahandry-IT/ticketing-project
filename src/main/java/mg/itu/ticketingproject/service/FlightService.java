package mg.itu.ticketingproject.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import mg.itu.ticketingproject.data.request.FlightMultiSearchRequest;
import mg.itu.ticketingproject.data.request.FlightRequest;
import mg.itu.ticketingproject.entity.*;
import mg.itu.ticketingproject.util.JPAUtil;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

    public List<Flight> getFlightsWithoutAllOffers() {
        em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Flight> query = em.createQuery(
                    "SELECT f FROM Flight f JOIN FETCH f.departureCity JOIN FETCH f.arrivalCity JOIN FETCH f.plane " +
                            "LEFT JOIN f.offers o " +
                            "WHERE (SELECT COUNT(o2) FROM Offer o2 WHERE o2.flight = f) < " +
                            "      (SELECT COUNT(st) FROM SeatType st)",
                    Flight.class
            );
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

    public List<Flight> searchFlights(FlightMultiSearchRequest request) {
        em = JPAUtil.getEntityManager();
        try {
            StringBuilder jpql = new StringBuilder(
                    "SELECT f FROM Flight f JOIN FETCH f.departureCity dc JOIN FETCH f.arrivalCity ac JOIN FETCH f.plane WHERE 1=1");

            if (request.getDepartureCity() != null && !request.getDepartureCity().trim().isEmpty()) {
                jpql.append(" AND LOWER(dc.name) LIKE LOWER(:departureCity)");
            }
            if (request.getArrivalCity() != null && !request.getArrivalCity().trim().isEmpty()) {
                jpql.append(" AND LOWER(ac.name) LIKE LOWER(:arrivalCity)");
            }
            if (request.getIdPlane() != null && !request.getIdPlane().trim().isEmpty()) {
                jpql.append(" AND LOWER(f.plane.name) LIKE LOWER(:planeId)");
            }
            if (request.getDepartureDate() != null) {
                jpql.append(" AND f.departureTime >= :departureDate");
            }
            if (request.getArrivalDate() != null) {
                jpql.append(" AND f.arrivalTime <= :arrivalDate");
            }

            jpql.append(" ORDER BY f.departureTime");

            TypedQuery<Flight> query = em.createQuery(jpql.toString(), Flight.class);

            if (request.getDepartureCity() != null && !request.getDepartureCity().trim().isEmpty()) {
                query.setParameter("departureCity", "%" + request.getDepartureCity().trim() + "%");
            }
            if (request.getArrivalCity() != null && !request.getArrivalCity().trim().isEmpty()) {
                query.setParameter("arrivalCity", "%" + request.getArrivalCity().trim() + "%");
            }
            if (request.getIdPlane() != null && !request.getIdPlane().trim().isEmpty()) {
                query.setParameter("planeId", "%" + request.getIdPlane().trim() + "%");
            }
            if (request.getDepartureDate() != null) {
                query.setParameter("departureDate", request.getDepartureDate());
            }
            if (request.getArrivalDate() != null) {
                query.setParameter("arrivalDate", request.getArrivalDate());
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

    @Transactional
    public void createFlightWithSeats(FlightRequest request) {
        // 1. Créer et persister le vol
        Flight flight = generateFlight(request);
        em.persist(flight);

        // 2. Créer et persister les sièges
        List<PlaneSeat> seats = createPlaneSeats(request, flight);
        for (PlaneSeat seat : seats) {
            em.persist(seat);
        }
    }

    private List<PlaneSeat> createPlaneSeats(FlightRequest request, Flight flight) {
        List<PlaneSeat> seats = new ArrayList<>();
        for (int i = 0; i < request.getSeatId().length; i++) {
            PlaneSeat seat = new PlaneSeat();
            SeatType seatType = em.find(SeatType.class, Integer.parseInt(request.getSeatId()[i]));

            if (seatType == null) {
                throw new IllegalArgumentException("Seat Type not found");
            }

            seat.setFlight(flight);
            seat.setPrice(BigDecimal.valueOf(Double.parseDouble(request.getSeatPrice()[i])));
            seat.setQuantity(Integer.parseInt(request.getSeatCount()[i]));
            seat.setType(seatType);
            seats.add(seat);
        }
        return seats;
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
        Integer id = request.getFlightId() != null ? Integer.parseInt(request.getFlightId()) : null;
        flight.setId(id);
        flight.setDepartureCity(departureCity);
        flight.setArrivalCity(arrivalCity);
        flight.setPlane(plane);
        flight.setDepartureTime(LocalDateTime.parse(request.getDepartureTime()));
        flight.setArrivalTime(LocalDateTime.parse(request.getArrivalTime()));
        return flight;
    }
}
