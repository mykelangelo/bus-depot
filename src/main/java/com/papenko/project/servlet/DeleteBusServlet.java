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

import static com.papenko.project.constant.ApplicationEndpointsURIs.AdminPage.DELETE_BUS_URI;
import static com.papenko.project.constant.RequestParametersNames.BUS_SERIAL;

@WebServlet(urlPatterns = DELETE_BUS_URI)
public class DeleteBusServlet extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeleteBusServlet.class);
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
        return DataSourceHolder.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOGGER.debug("about to POST");
        String busSerial = request.getParameter(BUS_SERIAL);
        Driver driverInBus = adminService.getDriverInBus(busSerial);
        final String lastSubmitStatusMessage;
        if (driverInBus == null) {
            adminService.deleteBus(busSerial);
            lastSubmitStatusMessage = "You deleted bus with serial number " + busSerial;
        } else {
            LOGGER.debug("can't delete - route is used");
            lastSubmitStatusMessage = "You tried to delete bus with serial number " + busSerial +
                    " but it's used - please assign driver " + driverInBus.getUserEmail() + " to other bus before deleting this bus";
        }
        LOGGER.debug("redirecting...");
        response.sendRedirect("/admin?lastSubmitStatusMessage=" + lastSubmitStatusMessage);
        LOGGER.debug("finished POST");
    }
}
