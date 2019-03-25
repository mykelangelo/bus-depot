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

import static com.papenko.project.constant.ApplicationEndpointsURIs.AdminPage.ADMIN_PAGE_URI;
import static com.papenko.project.constant.ApplicationEndpointsURIs.AdminPage.ASSIGN_BUS_TO_ROUTE_FORM_URI;
import static com.papenko.project.constant.RequestAttributesNames.LAST_SUBMIT_STATUS_MESSAGE;
import static com.papenko.project.constant.RequestParametersNames.BUS_SERIAL;
import static com.papenko.project.constant.RequestParametersNames.ROUTE_NAME;


@WebServlet(urlPatterns = ASSIGN_BUS_TO_ROUTE_FORM_URI)
public class AssignBusToRouteServlet extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(AssignBusToRouteServlet.class);
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
        String busSerial = request.getParameter(BUS_SERIAL);
        String routeName = request.getParameter(ROUTE_NAME);
        adminService.assignBusToRoute(busSerial, routeName);
        String statusMessage = localization.getMessage(request, "status_assign-bus-to-route", busSerial, routeName);
        request.getSession().setAttribute(LAST_SUBMIT_STATUS_MESSAGE, statusMessage);
        LOGGER.debug("redirecting...");
        response.sendRedirect(ADMIN_PAGE_URI);
        LOGGER.debug("finished POST");
    }
}
