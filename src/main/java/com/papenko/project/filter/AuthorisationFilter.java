package com.papenko.project.filter;

import com.papenko.project.entity.AuthenticatedUserDetails;
import com.papenko.project.entity.UserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = "*")
public class AuthorisationFilter extends HttpFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorisationFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String requestURI = request.getRequestURI();
        AuthenticatedUserDetails userDetails = (AuthenticatedUserDetails) request.getSession().getAttribute("user_details");

        if (userDetails == null) {
            if (isAdminPageOrFormURI(requestURI) || isDriverPageOrFormURI(requestURI)) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        } else {
            UserType userType = userDetails.getUserType();
            if (userType == UserType.BUS_DRIVER && isAdminPageOrFormURI(requestURI)
                    || userType == UserType.DEPOT_ADMIN && isDriverPageOrFormURI(requestURI)) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private boolean isDriverPageOrFormURI(String requestURI) {
        return requestURI.equals("/driver");
    }

    private boolean isAdminPageOrFormURI(String requestURI) {
        return requestURI.matches("/admin|/driver-to-bus|/vacate-driver|/bus-to-route");
    }
}
