package mg.itu.ticketingproject.controller;

import mg.itu.annotation.*;
import mg.itu.prom16.ModelAndView;

@AnnotationController
public class UserController {

    @Get
    @Url("/back/login")
    public ModelAndView getLoginBackOffice() {
        ModelAndView mv = new ModelAndView();
        mv.setUrl("/WEB-INF/views/back/login.jsp");
        return mv;
    }

    @Get
    @Url("/front/login")
    public ModelAndView getLoginFrontffice() {
        ModelAndView mv = new ModelAndView();
        mv.setUrl("/WEB-INF/views/front/login.jsp");
        return mv;
    }


}
