package mg.itu.ticketingproject.controller;

import jakarta.validation.Valid;
import lombok.Setter;
import mg.itu.annotation.*;
import mg.itu.prom16.ModelAndView;
import mg.itu.ticketingproject.data.request.FlightRequest;
import mg.itu.ticketingproject.entity.City;
import mg.itu.ticketingproject.entity.Flight;
import mg.itu.ticketingproject.entity.Plane;
import mg.itu.ticketingproject.entity.SeatType;
import mg.itu.ticketingproject.service.CityService;
import mg.itu.ticketingproject.service.FlightService;
import mg.itu.ticketingproject.service.PlaneService;
import mg.itu.ticketingproject.service.SeatTypeService;

import java.util.List;

@AnnotationController
@Setter
public class FlightController {

    private static final FlightService service = new FlightService();
    private static final PlaneService planeService = new PlaneService();
    private static final CityService cityService = new CityService();
    private static final SeatTypeService seatTypeService = new SeatTypeService();
    private ModelAndView mv;

    @Get
    @Url("/back/dashboard")
    @Authenticated(roles = {1})
    public ModelAndView getDashboardBackOffice() {
        mv = new ModelAndView();
        mv.setUrl("/WEB-INF/views/back/dashboard.jsp");
        return mv;
    }

    @Get
    @Url("/back/flights")
    @Authenticated(roles = {1})
    public ModelAndView getFlightsBackOffice() {
        mv = new ModelAndView();
        List<Flight> flights = service.findAll();
        mv.addObject("flights", flights);
        mv.setUrl("/WEB-INF/views/back/flights.jsp");
        return mv;
    }

    @Get
    @Url("/back/add/flight")
    @Authenticated(roles = {1})
    public ModelAndView getFlightsAddingFormBackOffice() {
        mv = new ModelAndView();
        loadData(mv);
        mv.setUrl("/WEB-INF/views/back/flight-add.jsp");
        return mv;
    }

    @Post
    @Url("/back/add/flight")
    @Authenticated(roles = {1})
    public String addFlightsBackOffice(@RequestBody @Valid FlightRequest request) {
        mv = new ModelAndView();
        Flight flight = service.save(service.generateFlight(request));
        mv.setUrl("/WEB-INF/views/back/flight-add.jsp");
        return "redirect:/back/flights";
    }

    private void loadData(ModelAndView mv) {
        List<Plane> planes = planeService.findAll();
        List<City> cities = cityService.findAll();
        List<SeatType> seatTypes = seatTypeService.findAll();
        mv.addObject("seats", seatTypes);
        mv.addObject("planes", planes);
        mv.addObject("cities", cities);
    }
}
