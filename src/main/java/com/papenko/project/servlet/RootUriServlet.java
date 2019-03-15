package com.papenko.project.servlet;

import com.papenko.project.entity.AuthenticatedUserDetails;
import com.papenko.project.entity.UserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.papenko.project.constant.ApplicationEndpointsURIs.AdminPage.ADMIN_PAGE_URI;
import static com.papenko.project.constant.ApplicationEndpointsURIs.*;
import static com.papenko.project.constant.SessionAttributesNames.USER_DETAILS;

@WebServlet(urlPatterns = ROOT_URI)
public class RootUriServlet extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(RootUriServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOGGER.debug("GET");
        var userDetails = (AuthenticatedUserDetails) request.getSession().getAttribute(USER_DETAILS);
        String redirectURI = chooseURI(userDetails);
        response.sendRedirect(redirectURI);
    }

    private String chooseURI(AuthenticatedUserDetails userDetails) {
        final String redirectURI;
        if (userDetails == null) {
            redirectURI = LOGIN_PAGE_URI;
        } else if (userDetails.getUserType() == UserType.DEPOT_ADMIN) {
            redirectURI = ADMIN_PAGE_URI;
        } else if (userDetails.getUserType() == UserType.BUS_DRIVER) {
            redirectURI = DRIVER_PAGE_URI;
        } else {
            throw new IllegalStateException("Invalid user type provided " + userDetails.getUserType() + ". Only " + UserType.DEPOT_ADMIN + " and " + UserType.BUS_DRIVER + " user types have pages");
        }
        return redirectURI;
    }
}
