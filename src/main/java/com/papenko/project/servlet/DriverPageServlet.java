package com.papenko.project.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(urlPatterns = "/driver")
public class DriverPageServlet extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(DriverPageServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.debug("GET");
        getServletContext().getRequestDispatcher("/driver.jsp").forward(request, response);
    }
}
