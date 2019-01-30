package com.papenko.project;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;

@WebServlet(urlPatterns = "/login")
public class LoginAction extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginAction.class);
    LoginService loginService;

    @Override
    public void init() {
        loginService = new LoginService(
                new UserRepository(
                        getDataSource()
                )
        );
    }

    DataSource getDataSource() {
        return new HikariDataSource(
                new HikariConfig("/db/connection-pool.properties")
        );
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.debug("GET");
        this.getServletContext().getRequestDispatcher("/login.jsp").forward(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.debug("POST");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        UserType userType = loginService.getUserType(email);
        String landingPage = generateLandingPagePath(userType);
        if (loginService.checkCredentials(email, password)) {
            request.getSession().setAttribute("email", email);
            response.sendRedirect(landingPage);
        } else {
            request.setAttribute("loginErrorMessage", "Invalid email or password");
            this.getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }

    private String generateLandingPagePath(UserType userType) {
        if (userType == UserType.DEPOT_ADMIN) {
            return "/admin.jsp";
        } else if (userType == UserType.BUS_DRIVER) {
            return "/driver.jsp";
        } else {
            return "/login.jsp";
        }
    }
}
