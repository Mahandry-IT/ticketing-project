package mg.itu.ticketingproject.controller;

import data.request.LoginRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import mg.itu.annotation.*;
import mg.itu.prom16.ModelAndView;
import mg.itu.ticketingproject.entity.Appuser;
import mg.itu.ticketingproject.service.UserService;

import java.util.Optional;

@AnnotationController
public class UserController {

    private final UserService userService = new UserService();
    private MySession session;

    @Get
    @Url("/back/login")
    public ModelAndView getLoginBackOffice() {
        ModelAndView mv = new ModelAndView();
        mv.setUrl("/WEB-INF/views/back/login.jsp");
        return mv;
    }

    @Post
    @Url("/back/login")
    public String loginWithCreditential(@RequestBody @Valid LoginRequest loginRequest,
                                              HttpServletRequest request,
                                              HttpServletResponse response) throws Exception {
        Optional<Appuser> result = userService.findUserByCreditential(loginRequest);

        if (result.isEmpty()) {
            request.setAttribute("error", "Credential not found");
            request.getRequestDispatcher("/WEB-INF/views/back/login.jsp").forward(request, response);
        }

        Appuser user = result.get();
        if (user.getRole().getName().equals("ADMIN")) {
            session.add("idrole", user.getId());
        } else {
            request.setAttribute("error", "You are not authorized to access this page");
            request.getRequestDispatcher("/WEB-INF/views/back/login.jsp").forward(request, response);
        }
        return "redirect:/back/dashboard";
    }

    @Get
    @Url("/front/login")
    public ModelAndView getLoginFrontffice() {
        ModelAndView mv = new ModelAndView();
        mv.setUrl("/WEB-INF/views/front/login.jsp");
        return mv;
    }


}
