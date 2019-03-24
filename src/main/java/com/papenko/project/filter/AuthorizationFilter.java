package com.papenko.project.filter;

import com.papenko.project.entity.AuthenticatedUserDetails;
import com.papenko.project.entity.UserType;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.papenko.project.constant.ApplicationEndpointsURIs.AdminPage.*;
import static com.papenko.project.constant.ApplicationEndpointsURIs.DRIVER_PAGE_URI;
import static com.papenko.project.constant.MDCKeys.ENDPOINT;
import static com.papenko.project.constant.SessionAttributesNames.USER_DETAILS;

@WebFilter(urlPatterns = "*")
public class AuthorizationFilter extends HttpFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorizationFilter.class);
    private static final String[] ADMIN_URIS = new String[]{
            ADMIN_PAGE_URI,
            ASSIGN_DRIVER_TO_BUS_FORM_URI,
            VACATE_DRIVER_FORM_URI,
            ASSIGN_BUS_TO_ROUTE_FORM_URI,
            ADD_ROUTE_URI,
            DELETE_ROUTE_URI,
            ADD_BUS_URI,
            DELETE_BUS_URI,
            ADD_DRIVER_URI,
            DELETE_DRIVER_URI
    };
    private static final String ADMIN_URIS_REGEX = StringUtils.join(ADMIN_URIS, '|');

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String requestURI = request.getRequestURI();
        MDC.put(ENDPOINT, requestURI);

        LOGGER.debug("about to filter a request");
        AuthenticatedUserDetails userDetails = (AuthenticatedUserDetails) request.getSession().getAttribute(USER_DETAILS);

        if (userDetails == null) {
            if (isAdminPageOrFormURI(requestURI) || isDriverPageOrFormURI(requestURI)) {
                LOGGER.warn("Unauthorized user is trying to access URI: {}", requestURI);
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        } else {
            UserType userType = userDetails.getUserType();
            if (userType == UserType.BUS_DRIVER && isAdminPageOrFormURI(requestURI)
                    || userType == UserType.DEPOT_ADMIN && isDriverPageOrFormURI(requestURI)) {
                LOGGER.warn("{} is trying to access forbidden URI: {}", userType, requestURI);
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }
        }
        LOGGER.debug("request filtered");
        filterChain.doFilter(request, response);
        LOGGER.debug("finished filtering a request");
    }

    private boolean isDriverPageOrFormURI(String requestURI) {
        return requestURI.equals(DRIVER_PAGE_URI);
    }

    private boolean isAdminPageOrFormURI(String requestURI) {
        return requestURI.matches(ADMIN_URIS_REGEX);
    }
}
