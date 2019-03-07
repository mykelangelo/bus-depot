package com.papenko.project.filter;


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

import static org.apache.commons.lang3.StringUtils.*;

/**
 * <a href="https://devcenter.heroku.com/articles/preparing-a-java-web-app-for-production-on-heroku#force-the-use-of-https">
 * Taken from Heroku Documentation
 * <a/>
 */
@WebFilter(urlPatterns = "*")
public class HttpsEnforcerFilter extends HttpFilter {
    static final String X_FORWARDED_PROTO = "X-Forwarded-Proto";
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpsEnforcerFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String productionOnlyHeader = request.getHeader(X_FORWARDED_PROTO);
        if (isNotBlank(productionOnlyHeader) && !productionOnlyHeader.contains("https")) {
            String pathInfo = defaultIfBlank(request.getRequestURI(), EMPTY);
            String secureUrl = "https://" + request.getServerName() + pathInfo;
            LOGGER.warn("Request [{}] is not secured, redirecting to [{}]", pathInfo, secureUrl);
            response.sendRedirect(secureUrl);
            return;
        }

        filterChain.doFilter(request, response);
    }
}
