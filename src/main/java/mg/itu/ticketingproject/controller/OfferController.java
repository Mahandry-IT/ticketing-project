package mg.itu.ticketingproject.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.Setter;
import mg.itu.annotation.*;
import mg.itu.prom16.ModelAndView;
import mg.itu.ticketingproject.data.request.FlightMultiSearchRequest;
import mg.itu.ticketingproject.data.request.FlightRequest;
import mg.itu.ticketingproject.data.request.OfferRequest;
import mg.itu.ticketingproject.entity.*;
import mg.itu.ticketingproject.service.*;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.util.List;

@AnnotationController
@Setter
public class OfferController {

    private static final FlightService service = new FlightService();
    private static final OfferService offerService = new OfferService();
    private static final SeatTypeService seatTypeService = new SeatTypeService();
    private ModelAndView mv;

    @Get
    @Url("/back/offers")
    @Authenticated(roles = {1})
    public ModelAndView getOffersBackOffice() {
        mv = new ModelAndView();
        mv.addObject("offers", offerService.findAll());
        mv.addObject("flights", service.getFlightsWithoutAllOffers());
        mv.addObject("seatTypes", seatTypeService.findAll());
        mv.setUrl("/WEB-INF/views/back/promotions.jsp");
        return mv;
    }


    @Get
    @Url("/back/modify/offer")
    @Authenticated(roles = {1})
    public ModelAndView getOffersModifyingFormBackOffice(@Parametre(name = "id") Integer id) {
        mv = new ModelAndView();
        mv.addObject("flights", service.findAll());
        mv.addObject("offer", offerService.findById(id));
        mv.addObject("seatTypes", seatTypeService.findAll());
        mv.setUrl("/WEB-INF/views/back/offer-modify.jsp");
        return mv;
    }

    @Get
    @Url("/back/delete/offer")
    @Authenticated(roles = {1})
    public String deleteOffer(@Parametre(name = "id") Integer id) {
        offerService.delete(id);
        return "redirect:/back/offers";
    }

    @Post
    @Url("/back/add/offer")
    @Authenticated(roles = {1})
    public String addOfferBackOffice(@RequestBody @Valid OfferRequest request, HttpServletRequest httpRequest, HttpServletResponse response) throws Exception {
        try {
            Offer offer = offerService.save(offerService.generateOffer(request));
            offerService.save(offer);
        } catch (Exception e) {
            String message = e.getMessage().isEmpty() ? "An error occurred while saving the offer." : e.getMessage();
            httpRequest.setAttribute("error", message);
            httpRequest.setAttribute("offers", offerService.findAll());
            httpRequest.setAttribute("flights", service.getFlightsWithoutAllOffers());
            httpRequest.setAttribute("seatTypes", seatTypeService.findAll());
            httpRequest.getRequestDispatcher("/WEB-INF/views/back/promotions.jsp").forward(httpRequest, response);
        }
        return "redirect:/back/offers";
    }

    @Post
    @Url("/back/modify/offer")
    @Authenticated(roles = {1})
    public String modifyOffersBackOffice(@RequestBody @Valid OfferRequest request, HttpServletRequest httpRequest, HttpServletResponse response) throws Exception {
        try {
            offerService.save(offerService.generateOffer(request));
            return "redirect:/back/offers";
        } catch (Exception e) {
            String message = e.getMessage().isEmpty() ? "An error occurred while updating the offer." : e.getMessage();
            httpRequest.setAttribute("error", message);
            httpRequest.setAttribute("offer", offerService.findById(request.getId_offer()));
            httpRequest.setAttribute("flights", service.findAll());
            httpRequest.setAttribute("seatTypes", seatTypeService.findAll());
            httpRequest.getRequestDispatcher("/WEB-INF/views/back/offer-modify.jsp").forward(httpRequest, response);
        }
        return "redirect:/back/offers";
    }

}
