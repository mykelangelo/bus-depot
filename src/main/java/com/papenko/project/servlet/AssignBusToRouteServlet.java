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

import static com.papenko.project.constant.ApplicationEndpointsURIs.AdminPage.ASSIGN_BUS_TO_ROUTE_FORM_URI;
import static com.papenko.project.constant.RequestParametersNames.BUS_SERIAL;
import static com.papenko.project.constant.RequestParametersNames.ROUTE_NAME;


@WebServlet(urlPatterns = ASSIGN_BUS_TO_ROUTE_FORM_URI)
public class AssignBusToRouteServlet extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(AssignBusToRouteServlet.class);
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
        String routeName = request.getParameter(ROUTE_NAME);
        adminService.assignBusToRoute(busSerial, routeName);
        LOGGER.debug("redirecting...");
        response.sendRedirect("/admin?lastSubmitStatusMessage=You assigned bus with serial number " +
                busSerial + " to route with name " + routeName);
        LOGGER.debug("finished POST");
    }
}
