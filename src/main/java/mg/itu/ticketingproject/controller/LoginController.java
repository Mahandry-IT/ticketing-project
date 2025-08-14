package mg.itu.ticketingproject.controller;

import mg.itu.annotation.*;
import mg.itu.prom16.ModelAndView;

@AnnotationController
public class LoginController {

    @Get
    @Url("/back/login")
    public ModelAndView getLoginBackOffice() {
        ModelAndView mv = new ModelAndView();
        mv.setUrl("/WEB-INF/views/back/login.jsp");
        return mv;
    }


}
