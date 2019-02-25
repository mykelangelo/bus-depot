package com.papenko.project.servlet;

import com.papenko.project.DataSourceHolder;
import com.papenko.project.repository.BusRepository;
import com.papenko.project.repository.DriverRepository;
import com.papenko.project.repository.RouteRepository;
import com.papenko.project.repository.UserRepository;
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

@WebServlet(urlPatterns = "/admin")
public class AdminPageServlet extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdminPageServlet.class);
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("lastSubmitStatusMessage", request.getParameter("lastSubmitStatusMessage"));
        request.setAttribute("drivers", adminService.getDrivers());
        request.setAttribute("buses", adminService.getBuses());
        request.setAttribute("routes", adminService.getRoutes());
        LOGGER.debug("GET");
        getServletContext().getRequestDispatcher("/admin.jsp").forward(request, response);
    }
}
