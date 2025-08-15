package mg.itu.ticketingproject.controller;

import mg.itu.annotation.AnnotationController;
import mg.itu.annotation.Authenticated;
import mg.itu.annotation.Get;
import mg.itu.annotation.Url;
import mg.itu.prom16.ModelAndView;
import mg.itu.ticketingproject.service.FlightService;
import mg.itu.ticketingproject.service.PlaneService;
import mg.itu.ticketingproject.service.ReservationDetailService;
import mg.itu.ticketingproject.service.ReservationService;

@AnnotationController
public class ReservationDetailController {

    private static final FlightService flightService = new FlightService();
    private static final PlaneService planeService = new PlaneService();
    private static  final ReservationService reservationService = new ReservationService();
    private static final ReservationDetailService reservationDetailService = new ReservationDetailService();

    private ModelAndView mv;

    @Get
    @Url("/back/reservation")
    public ModelAndView getReservation() {
        mv = new ModelAndView();
        mv.setUrl("/WEB-INF/views/back/reservations.jsp");
        return mv;
    }

}
