package com.papenko.project.servlet;

import com.papenko.project.DataSourceHolder;
import com.papenko.project.entity.AuthenticatedUserDetails;
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.debug("GET");
        var userDetails = request.getSession().getAttribute("user_details");
        var path = (userDetails == null) ? "/WEB-INF/login.jsp" : "/logout";
        getServletContext().getRequestDispatcher(path).forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.debug("POST");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (loginService.checkCredentials(email, password)) {
            UserType userType = loginService.getUserType(email);
            String pageURI = selectPageURI(userType);
            var userDetails = new AuthenticatedUserDetails(email, userType);
            request.getSession().setAttribute("user_details", userDetails);
            response.sendRedirect(pageURI);
        } else {
            request.setAttribute("loginErrorMessage", "Invalid email or password");
            this.getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
        }
    }

    private String selectPageURI(UserType userType) {
        if (userType == UserType.DEPOT_ADMIN) {
            return "/admin";
        } else if (userType == UserType.BUS_DRIVER) {
            return "/driver";
        } else {
            var e = new IllegalStateException("Only " + UserType.DEPOT_ADMIN + " and " + UserType.BUS_DRIVER + " user types have pages");
            LOGGER.error("Invalid user type provided " + userType, e);
            throw e;
        }
    }
}
