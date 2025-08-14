package mg.itu.ticketingproject.controller;

import lombok.Setter;
import mg.itu.annotation.*;
import mg.itu.prom16.ModelAndView;
import mg.itu.ticketingproject.entity.ReservationParam;
import mg.itu.ticketingproject.service.ReservationParamService;

@AnnotationController
@Setter
public class ReservationParamController {

    private static final ReservationParamService service = new ReservationParamService();
    private ModelAndView mv;

    @Get
    @Url("/back/add/reservation-param")
    @Authenticated(roles = {1})
    public ModelAndView getReservationParamForm() {
        mv = new ModelAndView();
        ReservationParam param = service.getLast();
        mv.addObject("param", param);
        mv.setUrl("/WEB-INF/views/back/reservation-param-add.jsp");
        return mv;
    }

    @Post
    @Url("/back/add/reservation-param")
    @Authenticated(roles = {1})
    public ModelAndView addOrUpdateReservationParam(@Parametre(name = "cancel_time") Integer cancelTime,
                                                    @Parametre(name="reservation_time") Integer reservationTime) {
        mv = new ModelAndView();
        try {
            service.createOrUpdate(cancelTime, reservationTime);
            mv.addObject("successMessage", "Paramètres de réservation enregistrés avec succès !");
        } catch (Exception e) {
            mv.addObject("errorMessage", "Erreur lors de l'enregistrement des paramètres : " + e.getMessage());
        }
        ReservationParam param = service.getLast();
        mv.addObject("param", param);
        mv.setUrl("/WEB-INF/views/back/reservation-param-add.jsp");
        return mv;
    }
}