package com.papenko.project.servlet;

import com.papenko.project.entity.Bus;
import com.papenko.project.entity.Driver;
import com.papenko.project.entity.Route;
import com.papenko.project.service.DriverService;
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
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DriverPageServletTest {
    @Spy
    @InjectMocks
    DriverPageServlet driverPageServlet;
    @Mock
    DriverService driverService;
    @Mock
    HttpServletRequest httpServletRequest;
    @Mock
    HttpServletResponse httpServletResponse;
    @Mock
    ServletContext servletContext;
    @Mock
    RequestDispatcher requestDispatcher;
    @Mock
    HttpSession httpSession;

    @Test
    void doGet_shouldForwardToAdminPage_andSetDriverToRequest() throws ServletException, IOException {
        // GIVEN
        doReturn(servletContext).when(driverPageServlet).getServletContext();
        doReturn(requestDispatcher).when(servletContext).getRequestDispatcher(anyString());
        doReturn(httpSession).when(httpServletRequest).getSession();
        doReturn("patrick.star@bikini.bottom").when(httpSession).getAttribute("email");
        doReturn(new Driver("patrick.star@bikini.bottom", new Bus("99ggg99", new Route("77")), true))
                .when(driverService).findDriverByEmail("patrick.star@bikini.bottom");
        // WHEN
        driverPageServlet.doGet(httpServletRequest, httpServletResponse);
        // THEN
        verify(servletContext).getRequestDispatcher("/WEB-INF/driver.jsp");
        verify(requestDispatcher).forward(httpServletRequest, httpServletResponse);
        verify(httpServletRequest).setAttribute("driver",
                new Driver("patrick.star@bikini.bottom", new Bus("99ggg99", new Route("77")), true));
    }


    @Test
    void doPost_shouldInitiateSettingDriverAwarenessToTrue_andRedirectToDriverPage() throws IOException {
        // GIVEN
        doReturn(httpSession).when(httpServletRequest).getSession();
        doReturn("yokel@driver.com").when(httpSession).getAttribute("email");
        // WHEN
        driverPageServlet.doPost(httpServletRequest, httpServletResponse);
        // THEN
        verify(driverService).setDriverAwarenessToTrue("yokel@driver.com");
        verify(httpServletResponse).sendRedirect("/driver");
    }
}