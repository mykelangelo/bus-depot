package com.papenko.project.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.papenko.project.constant.ApplicationEndpointsURIs.GO_HOME_URI;
import static com.papenko.project.constant.ApplicationEndpointsURIs.LOCALIZE_URI;
import static com.papenko.project.constant.RequestParametersNames.SELECTED_LANGUAGE;
import static com.papenko.project.constant.SessionAttributesNames.CURRENT_LANGUAGE;

@WebServlet(urlPatterns = LOCALIZE_URI)
public class LocalizationServlet extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(LocalizationServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOGGER.debug("about to POST");
        String selectedLanguage = request.getParameter(SELECTED_LANGUAGE);
        LOGGER.debug(selectedLanguage);
        request.getSession().setAttribute(CURRENT_LANGUAGE, selectedLanguage);
        LOGGER.debug("redirecting...");
        response.sendRedirect(GO_HOME_URI);
        LOGGER.debug("finished POST");
    }
}
