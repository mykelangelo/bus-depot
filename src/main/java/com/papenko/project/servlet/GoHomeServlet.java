package com.papenko.project.servlet;

import com.papenko.project.entity.AuthenticatedUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.papenko.project.constant.ApplicationEndpointsURIs.GO_HOME_URI;
import static com.papenko.project.constant.ApplicationEndpointsURIs.LOGIN_PAGE_URI;
import static com.papenko.project.constant.SessionAttributesNames.USER_DETAILS;

@WebServlet(urlPatterns = GO_HOME_URI)
public class GoHomeServlet extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(GoHomeServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOGGER.debug("about to GET");
        var userDetails = (AuthenticatedUserDetails) request.getSession().getAttribute(USER_DETAILS);
        String redirectURI = chooseURI(userDetails);
        LOGGER.debug("forwarding...");
        response.sendRedirect(redirectURI);
        LOGGER.debug("finished GET");
    }

    private String chooseURI(AuthenticatedUserDetails userDetails) {
        if (userDetails == null) {
            return LOGIN_PAGE_URI;
        } else {
            var userType = userDetails.getUserType();
            return userType.getPageUri();
        }
    }
}
