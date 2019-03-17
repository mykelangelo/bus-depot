package com.papenko.project.servlet;

import com.papenko.project.DataSourceHolder;
import com.papenko.project.repository.BusRepository;
import com.papenko.project.repository.DriverRepository;
import com.papenko.project.repository.RouteRepository;
import com.papenko.project.service.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;

import static com.papenko.project.constant.ApplicationEndpointsURIs.AdminPage.ADMIN_JSP_PATH;
import static com.papenko.project.constant.ApplicationEndpointsURIs.AdminPage.ADMIN_PAGE_URI;
import static com.papenko.project.constant.RequestAttributesNames.*;

@WebServlet(urlPatterns = ADMIN_PAGE_URI)
public class AdminPageServlet extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdminPageServlet.class);
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.debug("about to GET");
        request.setAttribute(LAST_SUBMIT_STATUS_MESSAGE, request.getParameter(LAST_SUBMIT_STATUS_MESSAGE));
        request.setAttribute(DRIVERS, adminService.getDrivers());
        request.setAttribute(BUSES, adminService.getBuses());
        request.setAttribute(ROUTES, adminService.getRoutes());
        LOGGER.debug("forwarding...");
        getServletContext().getRequestDispatcher(ADMIN_JSP_PATH).forward(request, response);
        LOGGER.debug("finished GET");
    }
}
