package mg.itu.ticketingproject.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import mg.itu.prom16.LocalDateAdapter;
import mg.itu.prom16.LocalDateTimeAdapter;
import mg.itu.ticketingproject.entity.User;
import mg.itu.ticketingproject.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mg.itu.annotation.*;
import mg.itu.prom16.ModelAndView;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;

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

            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                    .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                    .create();

            String safeValue = URLEncoder.encode(user.toMinimalJson(), StandardCharsets.UTF_8);
            Cookie cookie = new Cookie("user", safeValue);
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

    @Get
    @Url("/back/dashboard")
    public ModelAndView getDashboardBackOffice() {
        ModelAndView mv = new ModelAndView();

        mv.setUrl("/WEB-INF/views/back/dashboard.jsp");
        return mv;
    }
}
