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
import java.util.Locale;

import static com.papenko.project.constant.ApplicationEndpointsURIs.*;
import static com.papenko.project.constant.RequestAttributesNames.DISPLAY_LOGIN_ERROR_MESSAGE;
import static com.papenko.project.constant.RequestParametersNames.LOGIN_EMAIL;
import static com.papenko.project.constant.RequestParametersNames.LOGIN_PASSWORD;
import static com.papenko.project.constant.SessionAttributesNames.CURRENT_LANGUAGE;
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

    DataSource getDataSource() {
        return DataSourceHolder.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.debug("about to GET");

        Object userDetails = request.getSession().getAttribute(USER_DETAILS);
        final String path;
        if (userDetails == null) {
            setBrowserLanguage(request);
            path = LOGIN_JSP_PATH;
        } else {
            path = LOGOUT_FORM_URI;
        }
        LOGGER.debug("forwarding...");
        this.getServletContext().getRequestDispatcher(path).forward(request, response);
        LOGGER.debug("finished GET");
    }

    private void setBrowserLanguage(HttpServletRequest request) {
        Object currentLanguage = request.getSession().getAttribute(CURRENT_LANGUAGE);
        if (currentLanguage == null) {
            Locale locale = request.getLocale();
            String browserLanguage = locale.getLanguage();
            request.getSession().setAttribute(CURRENT_LANGUAGE, browserLanguage);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.debug("about to POST");
        String email = request.getParameter(LOGIN_EMAIL);
        String password = request.getParameter(LOGIN_PASSWORD);

        if (loginService.checkCredentials(email, password)) {
            UserType userType = loginService.getUserType(email);

            var userDetails = new AuthenticatedUserDetails(email, userType);
            request.getSession().setAttribute(USER_DETAILS, userDetails);

            String pageURI = userType.getPageUri();
            LOGGER.debug("redirecting...");
            response.sendRedirect(pageURI);
        } else {
            request.setAttribute(DISPLAY_LOGIN_ERROR_MESSAGE, true);
            LOGGER.debug("forwarding...");
            this.getServletContext().getRequestDispatcher(LOGIN_JSP_PATH).forward(request, response);
        }
        LOGGER.debug("finished POST");
    }
}
