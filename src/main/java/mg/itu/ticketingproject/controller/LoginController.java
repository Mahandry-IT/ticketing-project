package mg.itu.ticketingproject.controller;

import com.google.gson.Gson;
import mg.itu.ticketingproject.entity.User;
import mg.itu.ticketingproject.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mg.itu.annotation.*;
import mg.itu.prom16.ModelAndView;

@AnnotationController
public class LoginController {
    private final UserService userService = new UserService();;
    private MySession session;


    @Get
    @Url("/login/back")
    public ModelAndView getLoginBackOffice() {
        ModelAndView mv = new ModelAndView();
        if (session != null && session.get("idrole") != null) {
            mv.setUrl("/WEB-INF/views/back/vol/vol-list.jsp");
            return mv;
        }
        mv.setUrl("/WEB-INF/views/back/login.jsp");
        return mv;
    }

    @Post
    @Url("/login/back")
    public String loginBackOffice(@Parametre(name = "username")String username, @Parametre(name = "password")String password, HttpServletRequest request ,HttpServletResponse response) throws Exception {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        if (username == null || password == null || username.trim().isEmpty() || password.trim().isEmpty()) {
            request.setAttribute("error", "Username and password are required");
            request.getRequestDispatcher("/WEB-INF/views/back/login.jsp").forward(request, response);
        }

        user = userService.authenticate(username, password);

        if (user != null) {
            session.add("idrole", user.getIdUser());

            Gson gson = new Gson();
            Cookie cookie = new Cookie("user", gson.toJson(user));
            cookie.setMaxAge(24 * 60 * 60);
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            cookie.setSecure(true);
            response.addCookie(cookie);

        } else {
            request.setAttribute("error", "Invalid username or password");
            request.getRequestDispatcher("/WEB-INF/views/back/login.jsp").forward(request, response);
        }
        return "redirect:/back/dashboard";
    }
}
