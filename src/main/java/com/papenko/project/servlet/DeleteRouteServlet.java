package com.papenko.project.servlet;

import com.papenko.project.DataSourceHolder;
import com.papenko.project.entity.Bus;
import com.papenko.project.repository.BusRepository;
import com.papenko.project.repository.DriverRepository;
import com.papenko.project.repository.RouteRepository;
import com.papenko.project.service.AdminService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static com.papenko.project.constant.ApplicationEndpointsURIs.AdminPage.DELETE_ROUTE_URI;
import static com.papenko.project.constant.RequestParametersNames.ROUTE_NAME;

@WebServlet(urlPatterns = DELETE_ROUTE_URI)
public class DeleteRouteServlet extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeleteRouteServlet.class);
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
        String routeName = request.getParameter(ROUTE_NAME);
        List<Bus> busesOnRoute = adminService.getBusesOnRoute(routeName);
        final String lastSubmitStatusMessage;
        if (busesOnRoute.isEmpty()) {
            adminService.deleteRoute(routeName);
            lastSubmitStatusMessage = "You deleted route with name " + routeName;
        } else {
            LOGGER.debug("can't delete - route is used");
            List<String> busesSerials = busesOnRoute.stream().map(Bus::getSerialNumber).collect(Collectors.toList());
            String busesSerialsString = StringUtils.join(busesSerials, ", ");
            lastSubmitStatusMessage = "You tried to delete route with name " + routeName +
                    " but it's used - please assign bus(es) " + busesSerialsString + " to other route(s) before deleting this route";
        }
        LOGGER.debug("redirecting...");
        response.sendRedirect("/admin?lastSubmitStatusMessage=" + lastSubmitStatusMessage);
        LOGGER.debug("finished POST");
    }
}
