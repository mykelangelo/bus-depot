package com.papenko.project.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.papenko.project.constant.ApplicationEndpointsURIs.LOGIN_PAGE_URI;
import static com.papenko.project.constant.ApplicationEndpointsURIs.LOGOUT_FORM_URI;

@WebServlet(urlPatterns = LOGOUT_FORM_URI)
public class LogoutServlet extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(LogoutServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOGGER.debug("about to GET");
        request.getSession().invalidate();
        LOGGER.debug("redirecting...");
        response.sendRedirect(LOGIN_PAGE_URI);
        LOGGER.debug("finished GET");
    }
}
