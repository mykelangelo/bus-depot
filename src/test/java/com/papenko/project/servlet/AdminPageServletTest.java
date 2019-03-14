package com.papenko.project.servlet;

import com.papenko.project.entity.Bus;
import com.papenko.project.entity.Driver;
import com.papenko.project.entity.Route;
import com.papenko.project.service.AdminService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.papenko.project.constant.ApplicationEndpointsURI.AdminPage.ADMIN_JSP_PATH;
import static com.papenko.project.constant.RequestAttributeName.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@DisplayName("AdminPageServlet")
@ExtendWith(MockitoExtension.class)
class AdminPageServletTest {
    @Spy
    @InjectMocks
    AdminPageServlet adminPageServlet;
    @Mock
    AdminService adminService;
    @Mock
    HttpServletRequest httpServletRequest;
    @Mock
    HttpServletResponse httpServletResponse;
    @Mock
    ServletContext servletContext;
    @Mock
    RequestDispatcher requestDispatcher;

    @Test
    void doGet_shouldForwardToAdminPage_andSetDriversEmailsAndBusesSerialsAndRoutesNamesToRequest_andNotSetLastSubmitMessage_whenLastSubmitMessageIsNotPresentInQueryParam() throws ServletException, IOException {
        // GIVEN
        doReturn(servletContext).when(adminPageServlet).getServletContext();
        doReturn(requestDispatcher).when(servletContext).getRequestDispatcher(anyString());
        doReturn(List.of(
                new Driver("alexa@company.com", new Bus("AA4444AA", new Route("7u")), true),
                new Driver("bob.jenkins@gmail.com", new Bus("Il1171lI", new Route("7R")), false))
        ).when(adminService).getDrivers();
        doReturn(List.of(
                new Bus("AA4444AA", new Route("7u")),
                new Bus("Il1171lI", new Route("7R")))
        ).when(adminService).getBuses();
        doReturn(List.of(new Route("7L"), new Route("7k"), new Route("71"))).when(adminService).getRoutes();
        // WHEN
        adminPageServlet.doGet(httpServletRequest, httpServletResponse);
        // THEN
        verify(servletContext).getRequestDispatcher(ADMIN_JSP_PATH);
        verify(requestDispatcher).forward(httpServletRequest, httpServletResponse);
        verify(httpServletRequest).setAttribute(DRIVERS, List.of(
                new Driver("alexa@company.com", new Bus("AA4444AA", new Route("7u")), true),
                new Driver("bob.jenkins@gmail.com", new Bus("Il1171lI", new Route("7R")), false))
        );
        verify(httpServletRequest).setAttribute(BUSES, List.of(
                new Bus("AA4444AA", new Route("7u")),
                new Bus("Il1171lI", new Route("7R")))
        );
        verify(httpServletRequest).setAttribute(ROUTES, List.of(new Route("7L"), new Route("7k"), new Route("71")));
    }

    @Test
    void doGet_shouldForwardToAdminPage_andSetDriversEmailsAndBusesSerialsAndRoutesNamesToRequest_andSetLastSubmitMessage_whenLastSubmitMessageIsPresentInQueryParam() throws ServletException, IOException {
        // GIVEN
        doReturn(servletContext).when(adminPageServlet).getServletContext();
        doReturn(requestDispatcher).when(servletContext).getRequestDispatcher(anyString());
        doReturn(List.of(
                new Driver("alexa@company.com", new Bus("AA4444AA", new Route("7u")), true),
                new Driver("bob.jenkins@gmail.com", new Bus("Il1171lI", new Route("7R")), false))
        ).when(adminService).getDrivers();
        doReturn(List.of(
                new Bus("AA4444AA", new Route("7u")),
                new Bus("Il1171lI", new Route("7R")))
        ).when(adminService).getBuses();
        doReturn(List.of(new Route("7L"), new Route("7k"), new Route("71"))).when(adminService).getRoutes();
        doReturn("Jokes are not funny").when(httpServletRequest).getParameter(LAST_SUBMIT_STATUS_MESSAGE);
        // WHEN
        adminPageServlet.doGet(httpServletRequest, httpServletResponse);
        // THEN
        verify(servletContext).getRequestDispatcher(ADMIN_JSP_PATH);
        verify(requestDispatcher).forward(httpServletRequest, httpServletResponse);
        verify(httpServletRequest).setAttribute(DRIVERS, List.of(
                new Driver("alexa@company.com", new Bus("AA4444AA", new Route("7u")), true),
                new Driver("bob.jenkins@gmail.com", new Bus("Il1171lI", new Route("7R")), false))
        );
        verify(httpServletRequest).setAttribute(BUSES, List.of(
                new Bus("AA4444AA", new Route("7u")),
                new Bus("Il1171lI", new Route("7R")))
        );
        verify(httpServletRequest).setAttribute(ROUTES, List.of(new Route("7L"), new Route("7k"), new Route("71")));
        verify(httpServletRequest).setAttribute(LAST_SUBMIT_STATUS_MESSAGE, "Jokes are not funny");
    }
}