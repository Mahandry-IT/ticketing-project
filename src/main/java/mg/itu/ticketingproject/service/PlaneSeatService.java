package mg.itu.ticketingproject.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import mg.itu.ticketingproject.data.dto.SeatAvailabilityDTO;
import mg.itu.ticketingproject.data.request.FlightRequest;
import mg.itu.ticketingproject.entity.*;
import mg.itu.ticketingproject.util.JPAUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PlaneSeatService {

    @PersistenceContext
    EntityManager em;
    
    public List<PlaneSeat> findAll() {
        em = JPAUtil.getEntityManager();
        try {
            TypedQuery<PlaneSeat> query = em.createQuery(
                "SELECT p FROM PlaneSeat p JOIN FETCH p.flight JOIN FETCH p.type ORDER BY p.id",
                PlaneSeat.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }



    public PlaneSeat findById(Integer id) {
        em = JPAUtil.getEntityManager();
        try {
            return em.createQuery(
                "SELECT p FROM PlaneSeat p JOIN FETCH p.flight JOIN FETCH p.type WHERE p.id = :id",
                PlaneSeat.class)
                .setParameter("id", id)
                .getSingleResult();
        } finally {
            em.close();
        }
    }

    public List<PlaneSeat> findByIdFlight(Integer id) {
        em = JPAUtil.getEntityManager();
        try {
            return em.createQuery(
                            "SELECT p FROM PlaneSeat p JOIN FETCH p.flight JOIN FETCH p.type WHERE p.flight.id = :id ORDER BY p.id",
                            PlaneSeat.class)
                    .setParameter("id", id)
                    .getResultList();
        } finally {
            em.close();
        }
    }


    public PlaneSeat save(PlaneSeat flight) {
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
            PlaneSeat flight = em.find(PlaneSeat.class, id);
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

    public List<PlaneSeat> savePlaneSeat(FlightRequest request, Flight flight) {
        em = JPAUtil.getEntityManager();
        String[] planeSeatIds = request.getPlaneSeatId();
        List<PlaneSeat> list = new ArrayList<>();
        for (int i = 0; i < request.getSeatId().length; i++) {
            SeatType seatType = em.find(SeatType.class, Integer.parseInt(request.getSeatId()[i]));
            if(flight == null || seatType == null) {
                throw new IllegalArgumentException("Flight or Seat Type not found");
            }

            PlaneSeat planeSeat = new PlaneSeat();
            Integer seatId = (planeSeatIds != null && planeSeatIds[i] != null && !planeSeatIds[i].isEmpty())
                    ? Integer.parseInt(planeSeatIds[i])
                    : null;
            planeSeat.setId(seatId);
            planeSeat.setFlight(flight);
            planeSeat.setPrice(BigDecimal.valueOf(Double.parseDouble(request.getSeatPrice()[i])));
            planeSeat.setQuantity(Integer.parseInt(request.getSeatCount()[i]));
            planeSeat.setType(seatType);

            list.add(planeSeat);
        }
        return list;
    }

    public int placeleft(int flightId, int seatTypeId) {
        em = JPAUtil.getEntityManager();
        try {
            Integer totalSeats = em.createQuery(
                            "SELECT ps.quantity " +
                                    "FROM PlaneSeat ps " +
                                    "WHERE ps.flight.id = :flightId " +
                                    "AND ps.type.id = :seatTypeId", Integer.class)
                    .setParameter("flightId", flightId)
                    .setParameter("seatTypeId", seatTypeId)
                    .getSingleResult();

            if (totalSeats == null || totalSeats <= 0) {
                return 0;
            }

            Long reservedSeats = em.createQuery(
                            "SELECT COUNT(rd) " +
                                    "FROM ReservationDetail rd " +
                                    "WHERE rd.reservation.flight.id = :flightId " +
                                    "AND rd.seatType.id = :seatTypeId ", Long.class)
                    .setParameter("flightId", flightId)
                    .setParameter("seatTypeId", seatTypeId)
                    .getSingleResult();

            return totalSeats.intValue() - reservedSeats.intValue();
        } finally {
            em.close();
        }
    }

    public List<SeatAvailabilityDTO> findSeatAvailabilityByFlight(int flightId) {
        em = JPAUtil.getEntityManager();
        try {
            TypedQuery<SeatAvailabilityDTO> query = em.createQuery(
                    "SELECT new mg.itu.ticketingproject.data.dto.SeatAvailabilityDTO(st.name, ps.price, ps.quantity, ps.quantity - COALESCE(SUM(rdCount.countReserved), 0)) " +
                            "FROM PlaneSeat ps " +
                            "JOIN ps.type st " +
                            "LEFT JOIN (" +
                            "   SELECT rd.seatType.id AS seatTypeId, r.flight.id AS flightId, COUNT(rd) AS countReserved " +
                            "   FROM ReservationDetail rd " +
                            "   JOIN rd.reservation r " +
                            "   GROUP BY rd.seatType.id, r.flight.id" +
                            ") rdCount " +
                            "ON rdCount.seatTypeId = ps.type.id AND rdCount.flightId = ps.flight.id " +
                            "WHERE ps.flight.id = :flightId " +
                            "GROUP BY st.name, ps.price, ps.quantity",
                    SeatAvailabilityDTO.class
            );
            query.setParameter("flightId", flightId);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public boolean checkIfUserCanReserve(int flightId) {
        List<SeatAvailabilityDTO> list = findSeatAvailabilityByFlight(flightId);
        int availableSeats = 0;
        for (SeatAvailabilityDTO seatAvailabilityDTO : list) {
            availableSeats += seatAvailabilityDTO.getAvailableSeats();
        }
        return availableSeats != 0;
    }
}
