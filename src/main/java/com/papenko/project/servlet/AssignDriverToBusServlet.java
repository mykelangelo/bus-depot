package com.papenko.project.servlet;

import com.papenko.project.DataSourceHolder;
import com.papenko.project.entity.Driver;
import com.papenko.project.repository.BusRepository;
import com.papenko.project.repository.DriverRepository;
import com.papenko.project.repository.RouteRepository;
import com.papenko.project.service.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;

import static com.papenko.project.constant.ApplicationEndpointsURI.AdminPage.ASSIGN_DRIVER_TO_BUS_FORM_URI;


@WebServlet(urlPatterns = ASSIGN_DRIVER_TO_BUS_FORM_URI)
public class AssignDriverToBusServlet extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(AssignDriverToBusServlet.class);
    private AdminService adminService;

    @Override
    public void init() {
        adminService = new AdminService(
                new DriverRepository(
                        getDataSource(),
                        new BusRepository(
                                getDataSource()
                        )
                ),
                new BusRepository(
                        getDataSource()
                ),
                new RouteRepository(
                        getDataSource()
                )
        );
    }


    private DataSource getDataSource() {
        LOGGER.debug("about to get dataSource");
        return DataSourceHolder.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOGGER.debug("POST");
        String driverEmail = request.getParameter("driver-email");
        String busSerial = request.getParameter("bus-serial");
        Driver driverAlreadyInBus = adminService.getDriverInBus(busSerial);
        if (driverAlreadyInBus == null) {
            adminService.assignDriverToBus(driverEmail, busSerial);
            response.sendRedirect("/admin?lastSubmitStatusMessage=You assigned driver with email " +
                    driverEmail + " to bus with serial number " + busSerial);
        } else {
            response.sendRedirect("/admin?lastSubmitStatusMessage=You tried to assign driver with email " +
                    driverEmail + " to bus with serial number " + busSerial +
                    ", but this bus is already used by driver with email " + driverAlreadyInBus.getUserEmail());
        }
    }
}
