package com.papenko.project.servlet;

import com.papenko.project.DataSourceHolder;
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


@WebServlet(urlPatterns = "/driver-to-bus")
public class AssignDriverToBusServlet extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(AssignDriverToBusServlet.class);
    private AdminService adminService;

    @Override
    public void init() {
        adminService = new AdminService(
                new DriverRepository(
                        getDataSource()
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
        String driverEmail = request.getParameter("driver-email");
        String busSerial = request.getParameter("bus-serial");
        adminService.assignDriverToBus(driverEmail, busSerial);
        LOGGER.debug("POST");
        response.sendRedirect("/admin?lastSubmitStatusMessage=You assigned driver with email " +
                driverEmail + " to bus with serial number " + busSerial);
    }
}
