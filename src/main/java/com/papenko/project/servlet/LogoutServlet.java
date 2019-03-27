package com.papenko.project.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.papenko.project.constant.ApplicationEndpointsURIs.LOGIN_PAGE_URI;
import static com.papenko.project.constant.ApplicationEndpointsURIs.LOGOUT_FORM_URI;
import static com.papenko.project.constant.SessionAttributesNames.CURRENT_LANGUAGE;

@WebServlet(urlPatterns = LOGOUT_FORM_URI)
public class LogoutServlet extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(LogoutServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOGGER.debug("about to GET");
        HttpSession postAuthorizationSession = request.getSession();
        String currentLanguage = (String) postAuthorizationSession.getAttribute(CURRENT_LANGUAGE);
        postAuthorizationSession.invalidate();
        HttpSession preAuthorizationSession = request.getSession();
        preAuthorizationSession.setAttribute(CURRENT_LANGUAGE, currentLanguage);
        LOGGER.debug("redirecting...");
        response.sendRedirect(LOGIN_PAGE_URI);
        LOGGER.debug("finished GET");
    }
}
