package mg.itu.ticketingproject.controller;

import jakarta.validation.Valid;
import lombok.Setter;
import mg.itu.annotation.*;
import mg.itu.prom16.ModelAndView;
import mg.itu.ticketingproject.data.request.FlightMultiSearchRequest;
import mg.itu.ticketingproject.data.request.FlightRequest;
import mg.itu.ticketingproject.entity.*;
import mg.itu.ticketingproject.service.*;

import java.util.List;

@AnnotationController
@Setter
public class FlightController {

    private static final FlightService service = new FlightService();
    private static final PlaneService planeService = new PlaneService();
    private static final CityService cityService = new CityService();
    private static final SeatTypeService seatTypeService = new SeatTypeService();
    private static final PlaneSeatService planeSeatService = new PlaneSeatService();
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
        loadData(mv);
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

    @Get
    @Url("/back/modify/flight")
    @Authenticated(roles = {1})
    public ModelAndView getFlightsModifyingFormBackOffice(@Parametre(name = "id") Integer id) {
        Flight flight = service.findById(id);
        List<PlaneSeat> planeSeats = planeSeatService.findByIdFlight(id);
        mv = new ModelAndView();
        loadData(mv);
        mv.addObject("flight", flight);
        mv.addObject("planeSeats", planeSeats);
        mv.setUrl("/WEB-INF/views/back/flight-modify.jsp");
        return mv;
    }

    @Get
    @Url("/back/delete/flight")
    @Authenticated(roles = {1})
    public String deleteFlight(@Parametre(name = "id") Integer id) {
        service.delete(id);
        return "redirect:/back/flights";
    }

    @Get
    @Url("/back/detail/flight")
    @Authenticated(roles = {1})
    public ModelAndView getFlightsDetailingFormBackOffice(@Parametre(name = "id") Integer id) {
        Flight flight = service.findById(id);
        List<PlaneSeat> planeSeats = planeSeatService.findByIdFlight(id);
        mv = new ModelAndView();
        loadData(mv);
        mv.addObject("flight", flight);
        mv.addObject("planeSeats", planeSeats);
        mv.setUrl("/WEB-INF/views/back/flight-detail.jsp");
        return mv;
    }

    @Post
    @Url("/back/search/flight")
    @Authenticated(roles = {1})
    public ModelAndView getFlightResearch(@RequestBody @Valid FlightMultiSearchRequest request) {
        mv = new ModelAndView();
        loadData(mv);
        mv.addObject("flights", service.searchFlights(request));
        mv.setUrl("/WEB-INF/views/back/flights.jsp");
        return mv;
    }

    @Post
    @Url("/back/add/flight")
    @Authenticated(roles = {1})
    public String addFlightsBackOffice(@RequestBody @Valid FlightRequest request) {
        Flight flight = service.save(service.generateFlight(request));
        List<PlaneSeat> list = planeSeatService.savePlaneSeat(request, flight);
        savePlaneSeats(list);
        return "redirect:/back/flights";
    }

    @Post
    @Url("/back/modify/flight")
    @Authenticated(roles = {1})
    public String modifyFlightsBackOffice(@RequestBody @Valid FlightRequest request) {
        Flight flight = service.save(service.generateFlight(request));
        List<PlaneSeat> list = planeSeatService.savePlaneSeat(request, flight);
        savePlaneSeats(list);
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

    private void savePlaneSeats(List<PlaneSeat> planeSeats) {
        for (PlaneSeat planeSeat : planeSeats) {
            planeSeatService.save(planeSeat);
        }
    }
}
