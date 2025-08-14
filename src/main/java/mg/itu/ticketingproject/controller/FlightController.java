package mg.itu.ticketingproject.controller;

import mg.itu.annotation.AnnotationController;
import mg.itu.annotation.Get;
import mg.itu.annotation.Url;
import mg.itu.prom16.ModelAndView;

@AnnotationController
public class FlightController {

    @Get
    @Url("/back/dashboard")
    public ModelAndView getDashboardBackOffice() {
        ModelAndView mv = new ModelAndView();
        mv.setUrl("/WEB-INF/views/back/dashboard.jsp");
        return mv;
    }

    @Get
    @Url("/back/flights")
    public ModelAndView getFlightsBackOffice() {
        ModelAndView mv = new ModelAndView();
        mv.setUrl("/WEB-INF/views/back/flights.jsp");
        return mv;
    }
}
