package com.papenko.project.servlet.admin;

import com.papenko.project.DataSourceHolder;
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

import static com.papenko.project.constant.ApplicationEndpointsURIs.AdminPage.ADD_DRIVER_URI;
import static com.papenko.project.constant.RequestParametersNames.DRIVER_EMAIL;
import static com.papenko.project.constant.RequestParametersNames.DRIVER_PASSWORD;

@WebServlet(urlPatterns = ADD_DRIVER_URI)
public class AddDriverServlet extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(AddDriverServlet.class);
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
        String driverPassword = request.getParameter(DRIVER_PASSWORD);
        adminService.addDriver(driverEmail, driverPassword);
        LOGGER.debug("redirecting...");
        response.sendRedirect("/admin?lastSubmitStatusMessage=" + localization.getMessage(request, "status_add-driver", driverEmail));
        LOGGER.debug("finished POST");
    }
}
