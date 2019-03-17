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
        return DataSourceHolder.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.debug("about to GET");
        var userDetails = request.getSession().getAttribute(USER_DETAILS);
        var path = (userDetails == null) ? LOGIN_JSP_PATH : LOGOUT_FORM_URI;
        LOGGER.debug("forwarding...");
        this.getServletContext().getRequestDispatcher(path).forward(request, response);
        LOGGER.debug("finished GET");
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.debug("about to POST");
        String email = request.getParameter(EMAIL);
        String password = request.getParameter(PASSWORD);

        if (loginService.checkCredentials(email, password)) {
            UserType userType = loginService.getUserType(email);

            var userDetails = new AuthenticatedUserDetails(email, userType);
            request.getSession().setAttribute(USER_DETAILS, userDetails);

            String pageURI = userType.getPageUri();
            LOGGER.debug("redirecting...");
            response.sendRedirect(pageURI);
        } else {
            request.setAttribute(LOGIN_ERROR_MESSAGE, "Invalid email or password");
            LOGGER.debug("forwarding...");
            this.getServletContext().getRequestDispatcher(LOGIN_JSP_PATH).forward(request, response);
        }
        LOGGER.debug("finished POST");
    }
}
