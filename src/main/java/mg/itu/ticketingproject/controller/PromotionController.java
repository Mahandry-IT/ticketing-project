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
import mg.itu.ticketingproject.entity.PromotionAlea;
import mg.itu.ticketingproject.entity.Reservation;
import mg.itu.ticketingproject.enums.ReservationStatus;
import mg.itu.ticketingproject.service.*;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static mg.itu.ticketingproject.util.ClassUtil.toBigDecimalList;

@AnnotationController
@Setter
public class PromotionController {

    private ModelAndView mv;
    private static PromotionAleaService promotionAleaService = new PromotionAleaService();

    @Get
    @Url("/back/alea")
    @Authenticated(roles = {1})
    public ModelAndView getFrontBackAlea() {
        mv = new ModelAndView();
        mv.setUrl("/WEB-INF/views/back/promotion-alea.jsp");
        return mv;
    }

    @Post
    @Url("/back/alea")
    @Authenticated(roles = {1})
    public ModelAndView traitementDate(@Parametre(name = "date") Date date) {
        mv = new ModelAndView();
        if (date == null) {
            date = new Date(System.currentTimeMillis());
        }
        promotionAleaService.updateReservationAndPromotions(date);
        mv.setUrl("/WEB-INF/views/back/promotion-alea.jsp");
        return mv;
    }
}