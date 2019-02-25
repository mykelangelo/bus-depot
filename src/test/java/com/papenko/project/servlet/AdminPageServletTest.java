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

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@DisplayName("AdminPageServlet")
@ExtendWith(MockitoExtension.class)
class AdminPageServletTest {
    @Spy
    @InjectMocks
    AdminPageServlet adminPage;
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
    void doGet_shouldForwardToAdminPage_andSetDriversEmailsAndBusesSerialsAndRoutesNamesToRequest_andNoSetLastSubmitMessage_whenLastSubmitMessageIsNotPresentInQueryParam() throws ServletException, IOException {
        // GIVEN
        doReturn(servletContext).when(adminPage).getServletContext();
        doReturn(requestDispatcher).when(servletContext).getRequestDispatcher(anyString());
        doReturn(List.of(new Driver("alexa@company.com", "AA4444AA"), new Driver("bob.jenkins@gmail.com", "Il1171lI"))).when(adminService).getDrivers();
        doReturn(List.of(new Bus("AA4444AA", "7L"), new Bus("Il1171lI", "7k"))).when(adminService).getBuses();
        doReturn(List.of(new Route("7L"), new Route("7k"), new Route("71"))).when(adminService).getRoutes();
        // WHEN
        adminPage.doGet(httpServletRequest, httpServletResponse);
        // THEN
        verify(servletContext).getRequestDispatcher("/admin.jsp");
        verify(requestDispatcher).forward(httpServletRequest, httpServletResponse);
        verify(httpServletRequest).setAttribute("drivers", List.of(new Driver("alexa@company.com", "AA4444AA"), new Driver("bob.jenkins@gmail.com", "Il1171lI")));
        verify(httpServletRequest).setAttribute("buses", List.of(new Bus("AA4444AA", "7L"), new Bus("Il1171lI", "7k")));
        verify(httpServletRequest).setAttribute("routes", List.of(new Route("7L"), new Route("7k"), new Route("71")));
    }

    @Test
    void doGet_shouldForwardToAdminPage_andSetDriversEmailsAndBusesSerialsAndRoutesNamesToRequest_andSetLastSubmitMessage_whenLastSubmitMessageIsPresentInQueryParam() throws ServletException, IOException {
        // GIVEN
        doReturn(servletContext).when(adminPage).getServletContext();
        doReturn(requestDispatcher).when(servletContext).getRequestDispatcher(anyString());
        doReturn(List.of(new Driver("alexa@company.com", "AA4444AA"), new Driver("bob.jenkins@gmail.com", "Il1171lI"))).when(adminService).getDrivers();
        doReturn(List.of(new Bus("AA4444AA", "7L"), new Bus("Il1171lI", "7k"))).when(adminService).getBuses();
        doReturn(List.of(new Route("7L"), new Route("7k"), new Route("71"))).when(adminService).getRoutes();
        doReturn("Jokes are not funny").when(httpServletRequest).getParameter("lastSubmitStatusMessage");
        // WHEN
        adminPage.doGet(httpServletRequest, httpServletResponse);
        // THEN
        verify(servletContext).getRequestDispatcher("/admin.jsp");
        verify(requestDispatcher).forward(httpServletRequest, httpServletResponse);
        verify(httpServletRequest).setAttribute("drivers", List.of(new Driver("alexa@company.com", "AA4444AA"), new Driver("bob.jenkins@gmail.com", "Il1171lI")));
        verify(httpServletRequest).setAttribute("buses", List.of(new Bus("AA4444AA", "7L"), new Bus("Il1171lI", "7k")));
        verify(httpServletRequest).setAttribute("routes", List.of(new Route("7L"), new Route("7k"), new Route("71")));
        verify(httpServletRequest).setAttribute("lastSubmitStatusMessage", "Jokes are not funny");
    }
}