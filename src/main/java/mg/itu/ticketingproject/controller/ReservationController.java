package mg.itu.ticketingproject.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import lombok.Setter;
import mg.itu.annotation.*;
import mg.itu.prom16.FileSave;
import mg.itu.prom16.ModelAndView;
import mg.itu.ticketingproject.data.dto.PriceDTO;
import mg.itu.ticketingproject.data.request.PriceRequest;
import mg.itu.ticketingproject.data.request.ReservationRequest;
import mg.itu.ticketingproject.entity.Flight;
import mg.itu.ticketingproject.entity.Reservation;
import mg.itu.ticketingproject.entity.ReservationParam;
import mg.itu.ticketingproject.enums.ReservationStatus;
import mg.itu.ticketingproject.service.*;
import mg.itu.ticketingproject.util.ClassUtil;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static mg.itu.ticketingproject.util.ClassUtil.toBigDecimalList;

@AnnotationController
@Setter
public class ReservationController {

    private ModelAndView mv;
    private static PlaneSeatService planeService = new PlaneSeatService();
    private static SeatTypeService seatTypeService = new SeatTypeService();
    private static FlightService flightService = new FlightService();
    private static ReservationService reservationService = new ReservationService();
    private static ReservationParamService reservationParamService = new ReservationParamService();
    private MySession session;

    @Get
    @Url("/front/reserve/flight")
    @Authenticated(roles = {2})
    public ModelAndView getFlightReservation(@Parametre(name = "id") Integer id) {
        mv = new ModelAndView();
        mv.addObject("flight", flightService.findById(id));
        mv.addObject("seatTypes", seatTypeService.findAll());
        mv.setUrl("/WEB-INF/views/front/reservation-add.jsp");
        return mv;
    }

    @Get
    @Url("/front/reservations")
    @Authenticated(roles = {2})
    public ModelAndView getReservations() {
        mv = new ModelAndView();
        Integer iduser = (Integer) session.get("iduser");
        if (iduser == null) {
            throw new RuntimeException("Veuillez vous connecter pour acceder a cette page");
        }
        mv.addObject("reservations", reservationService.findUserReservationsByDetailStatus(iduser));
        mv.setUrl("/WEB-INF/views/front/reservations.jsp");
        return mv;
    }

    @Get
    @Url("/front/detail/reservation")
    @Authenticated(roles = {2})
    public ModelAndView getFlightReservationDetail(@Parametre(name = "id") Integer id) {
        mv = new ModelAndView();
        mv.addObject("reservation", reservationService.findById(id));
        mv.addObject("seatTypes", seatTypeService.findAll());
        mv.setUrl("/WEB-INF/views/front/reservation-details.jsp");
        return mv;
    }

    @Get
    @Url("/front/cancel/reservation")
    @Authenticated(roles = {2})
    public String cancelReservation(@Parametre(name = "id") Integer id, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Reservation reservation = reservationService.findById(id);
        if (reservation.getReservationTime().isAfter(reservation.getFlight().getDepartureTime().minusHours(reservationParamService.getLast().getCancelTime()))) {
            Integer iduser = (Integer) session.get("iduser");
            if (iduser == null) {
                throw new RuntimeException("Veuillez vous connecter pour acceder a cette page");
            }
            request.setAttribute("error", "Vous ne pouvez pas annuler cette reservation car le vol est imminent.");
            request.setAttribute("reservations", reservationService.findUserReservationsByDetailStatus(iduser));
            request.getRequestDispatcher("/WEB-INF/views/front/reservations.jsp").forward(request, response);
        }
        reservationService.updateReservationStatus(id, ReservationStatus.CANCELED);
        return "redirect:/front/reservations";
    }

    @Get
    @Url("/front/modify/reservation")
    @Authenticated(roles = {2})
    public ModelAndView modifyReservation(@Parametre(name = "id") Integer id) {
        mv = new ModelAndView();
        mv.addObject("reservation", reservationService.findById(id));
        mv.addObject("seatTypes", seatTypeService.findAll());
        mv.setUrl("/WEB-INF/views/front/reservation-modify.jsp");
        return mv;
    }

    @Get
    @Url("/back/confirm/reservation")
    @Authenticated(roles = {1})
    public String confimReservationAdmin(@Parametre(name = "id") Integer id) {
        reservationService.updateReservationStatus(id, ReservationStatus.PAID);
        return "redirect:/back/reservations";
    }

    @Post
    @Url("/front/price/flight")
    @RestAPI
    @Authenticated(roles = {2})
    public PriceDTO getPrice(@RequestBody PriceRequest request) {
        return reservationService.getFinalSeatPrice(request.getIdFlight(), request.getIdSeatType(), request.getAge());
    }

    @Post
    @Url("/front/reservation/add")
    @Authenticated(roles = {2})
    public String addRReservation(@Parametre(name = "passport") List<Part> passport, @RequestBody ReservationRequest requete, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Integer iduser = (Integer) request.getSession().getAttribute("iduser");
        if (iduser == null) {
            return "redirect:/";
        }
        try {
            reservationService.save(iduser, requete.getFlight_id(), requete.getReservation_id(), requete.getReservation_time(),
                    requete.getSeat_type(), requete.getName(), requete.getDetail_id(), requete.getAge(), getPassportList(passport, request),
                    toBigDecimalList(requete.getPrice()));
        } catch (Exception e) {
            String message = e.getMessage().isEmpty() ? "An error occurred while saving the reservation." : e.getMessage();
            request.setAttribute("error", message);
            request.setAttribute("flight", flightService.findById(requete.getFlight_id()));
            request.setAttribute("seatTypes", seatTypeService.findAll());
            request.getRequestDispatcher("/WEB-INF/views/front/reservation-add.jsp").forward(request, response);
        }
        return "redirect:/front/flights";
    }

    private List<String> getPassportList(List<Part> passportParts, HttpServletRequest request) throws Exception {
        List<String> passportList = new ArrayList<>();
        for (Part part : passportParts) {
            if (part.getSubmittedFileName() != null && !part.getSubmittedFileName().isEmpty()) {
                passportList.add(FileSave.saveFile(part, request));
            }
        }
        return passportList;
    }
}