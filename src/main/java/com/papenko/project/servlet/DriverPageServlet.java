package com.papenko.project.servlet;

import com.papenko.project.DataSourceHolder;
import com.papenko.project.entity.AuthenticatedUserDetails;
import com.papenko.project.entity.Driver;
import com.papenko.project.repository.BusRepository;
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

import static com.papenko.project.constant.ApplicationEndpointsURIs.DRIVER_JSP_PATH;
import static com.papenko.project.constant.ApplicationEndpointsURIs.DRIVER_PAGE_URI;
import static com.papenko.project.constant.RequestAttributesNames.DRIVER;
import static com.papenko.project.constant.SessionAttributesNames.USER_DETAILS;


@WebServlet(urlPatterns = DRIVER_PAGE_URI)
public class DriverPageServlet extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(DriverPageServlet.class);
    private DriverService driverService;

    @Override
    public void init() {
        driverService = new DriverService(
                new DriverRepository(
                        getDataSource(),
                        new BusRepository(
                                getDataSource()
                        )
                )
        );
    }

    private DataSource getDataSource() {
        return DataSourceHolder.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.debug("about to GET");
        var userDetails = (AuthenticatedUserDetails) request.getSession().getAttribute(USER_DETAILS);
        Driver driver = driverService.findDriverByEmail(userDetails.getEmail());
        request.setAttribute(DRIVER, driver);
        LOGGER.debug("forwarding...");
        this.getServletContext().getRequestDispatcher(DRIVER_JSP_PATH).forward(request, response);
        LOGGER.debug("finished GET");
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOGGER.debug("about to POST");
        var userDetails = (AuthenticatedUserDetails) request.getSession().getAttribute(USER_DETAILS);
        driverService.setDriverAwarenessToTrue(userDetails.getEmail());
        LOGGER.debug("redirecting...");
        response.sendRedirect(DRIVER_PAGE_URI);
        LOGGER.debug("finished POST");
    }
}
