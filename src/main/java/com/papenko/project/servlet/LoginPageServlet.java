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

import static com.papenko.project.constant.ApplicationEndpointsURIs.AdminPage.ADMIN_PAGE_URI;
import static com.papenko.project.constant.ApplicationEndpointsURIs.*;
import static com.papenko.project.constant.RequestAttributesNames.LOGIN_ERROR_MESSAGE;
import static com.papenko.project.constant.RequestParametersNames.EMAIL;
import static com.papenko.project.constant.RequestParametersNames.PASSWORD;
import static com.papenko.project.constant.SessionAttributesNames.USER_DETAILS;

@WebServlet(urlPatterns = LOGIN_PAGE_URI)
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
        var userDetails = request.getSession().getAttribute(USER_DETAILS);
        var path = (userDetails == null) ? LOGIN_JSP_PATH : LOGOUT_FORM_URI;
        this.getServletContext().getRequestDispatcher(path).forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.debug("POST");
        String email = request.getParameter(EMAIL);
        String password = request.getParameter(PASSWORD);

        if (loginService.checkCredentials(email, password)) {
            UserType userType = loginService.getUserType(email);
            String pageURI = selectPageURI(userType);
            var userDetails = new AuthenticatedUserDetails(email, userType);
            request.getSession().setAttribute(USER_DETAILS, userDetails);
            response.sendRedirect(pageURI);
        } else {
            request.setAttribute(LOGIN_ERROR_MESSAGE, "Invalid email or password");
            this.getServletContext().getRequestDispatcher(LOGIN_JSP_PATH).forward(request, response);
        }
    }

    private String selectPageURI(UserType userType) {
        if (userType == UserType.DEPOT_ADMIN) {
            return ADMIN_PAGE_URI;
        } else if (userType == UserType.BUS_DRIVER) {
            return DRIVER_PAGE_URI;
        } else {
            throw new IllegalStateException("Invalid user type provided: " + userType + ". Only " + UserType.DEPOT_ADMIN + " and " + UserType.BUS_DRIVER + " user types have pages");
        }
    }
}
