package com.papenko.project.servlet;

import com.papenko.project.DataSourceHolder;
import com.papenko.project.entity.UserType;
import com.papenko.project.repository.UserRepository;
import com.papenko.project.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;

import static java.util.Arrays.asList;

@WebServlet(urlPatterns = "/login")
public class LoginPageServlet extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginPageServlet.class);
    private LoginService loginService;

    @Override
    public void init() {
        loginService = new LoginService(
                new UserRepository(
                        getDataSource()
                )
        );
    }

    private DataSource getDataSource() {
        LOGGER.debug("about to get dataSource");
        return DataSourceHolder.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.debug("GET");
        this.getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.debug("POST");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (loginService.checkCredentials(email, password)) {
            UserType userType = loginService.getUserType(email);
            String landingPage = generateLandingPagePath(userType);
            request.getSession().setAttribute("email", email);
            response.sendRedirect(landingPage);
        } else {
            request.setAttribute("loginErrorMessage", "Invalid email or password");
            this.getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
        }
    }

    private String generateLandingPagePath(UserType userType) {
        if (userType == UserType.DEPOT_ADMIN) {
            return "/admin";
        } else if (userType == UserType.BUS_DRIVER) {
            return "/driver";
        } else {
            throw new IllegalStateException("Only " + asList(UserType.values()) + " user types have landing pages");
        }
    }
}
