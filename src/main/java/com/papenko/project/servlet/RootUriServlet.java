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

@WebServlet(urlPatterns = "/")
public class RootUriServlet extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(RootUriServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOGGER.debug("GET");
        var userDetails = (AuthenticatedUserDetails) request.getSession().getAttribute("user_details");
        String redirectURI = chooseURI(userDetails);
        response.sendRedirect(redirectURI);
    }

    private String chooseURI(AuthenticatedUserDetails userDetails) {
        final String redirectURI;
        if (userDetails == null) {
            redirectURI = "/login";
        } else if (userDetails.getUserType() == UserType.DEPOT_ADMIN) {
            redirectURI = "/admin";
        } else if (userDetails.getUserType() == UserType.BUS_DRIVER) {
            redirectURI = "/driver";
        } else {
            var e = new IllegalStateException("Only " + UserType.DEPOT_ADMIN + " and " + UserType.BUS_DRIVER + " user types have pages");
            LOGGER.error("Invalid user type provided " + userDetails.getUserType(), e);
            throw e;
        }
        return redirectURI;
    }
}
