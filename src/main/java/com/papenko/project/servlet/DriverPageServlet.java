package com.papenko.project.servlet;

import com.papenko.project.DataSourceHolder;
import com.papenko.project.entity.Driver;
import com.papenko.project.repository.DriverRepository;
import com.papenko.project.service.DriverService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;


@WebServlet(urlPatterns = "/driver")
public class DriverPageServlet extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(DriverPageServlet.class);
    private DriverService driverService;

    @Override
    public void init() {
        driverService = new DriverService(
                new DriverRepository(
                        getDataSource()
                )
        );
    }

    private DataSource getDataSource() {
        LOGGER.debug("about to get dataSource");
        return DataSourceHolder.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.debug("GET");
        String email = (String) request.getSession().getAttribute("email");
        Driver driver = driverService.findDriverByEmail(email);
        request.setAttribute("driver", driver);
        getServletContext().getRequestDispatcher("/driver.jsp").forward(request, response);
    }
}
