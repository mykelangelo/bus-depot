package com.papenko.project.servlet.admin;

import com.papenko.project.DataSourceHolder;
import com.papenko.project.entity.Driver;
import com.papenko.project.repository.BusRepository;
import com.papenko.project.repository.DriverRepository;
import com.papenko.project.repository.RouteRepository;
import com.papenko.project.repository.UserRepository;
import com.papenko.project.service.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;

import static com.papenko.project.constant.ApplicationEndpointsURIs.AdminPage.ASSIGN_DRIVER_TO_BUS_FORM_URI;
import static com.papenko.project.constant.RequestParametersNames.BUS_SERIAL;
import static com.papenko.project.constant.RequestParametersNames.DRIVER_EMAIL;


@WebServlet(urlPatterns = ASSIGN_DRIVER_TO_BUS_FORM_URI)
public class AssignDriverToBusServlet extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(AssignDriverToBusServlet.class);
    private AdminService adminService;
    private AdminMessagesLocalization localization;

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
                ),
                new UserRepository(
                        getDataSource()
                )
        );
        localization = new AdminMessagesLocalization();
    }


    DataSource getDataSource() {
        return DataSourceHolder.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOGGER.debug("about to POST");
        String driverEmail = request.getParameter(DRIVER_EMAIL);
        String busSerial = request.getParameter(BUS_SERIAL);
        Driver driverAlreadyInBus = adminService.getDriverInBus(busSerial);
        final String redirectURI;
        if (driverAlreadyInBus == null) {
            adminService.assignDriverToBus(driverEmail, busSerial);
            redirectURI = "/admin?lastSubmitStatusMessage=" + localization.getMessage(request, "status_assign-driver-to-bus", driverEmail, busSerial);
        } else {
            LOGGER.debug("can't assign driver - bus is used");
            String driverInBusEmail = driverAlreadyInBus.getUserEmail();
            redirectURI = "/admin?lastSubmitStatusMessage=" + localization.getMessage(request, "status_try-assign-driver-to-bus", driverEmail, busSerial, driverInBusEmail);
        }
        LOGGER.debug("redirecting...");
        response.sendRedirect(redirectURI);
        LOGGER.debug("finished POST");
    }
}
