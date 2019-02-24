package com.papenko.project.servlet;

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


@WebServlet(urlPatterns = "/bus-to-route")
public class AssignBusToRouteServlet extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(AssignBusToRouteServlet.class);
    private AdminService adminService;

    @Override
    public void init() {
        adminService = new AdminService(
                new UserRepository(
                        getDataSource()
                ),
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


    DataSource getDataSource() {
        LOGGER.debug("about to get dataSource");
        return DataSourceHolder.getInstance();
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String busSerial = request.getParameter("bus-serial");
        String routeName = request.getParameter("route-name");
        adminService.assignBusToRoute(busSerial, routeName);
        LOGGER.debug("POST");
        response.sendRedirect("/admin?lastSubmitStatusMessage=You assigned bus with serial number " +
                busSerial + " to route with name " + routeName);
    }
}
