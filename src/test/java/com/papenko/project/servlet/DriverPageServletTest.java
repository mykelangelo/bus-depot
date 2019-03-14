package com.papenko.project.servlet;

import com.papenko.project.entity.*;
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

import static com.papenko.project.constant.ApplicationEndpointsURI.DRIVER_JSP_PATH;
import static com.papenko.project.constant.ApplicationEndpointsURI.DRIVER_PAGE_URI;
import static com.papenko.project.constant.SessionAttributeName.USER_DETAILS;
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
        var userDetails = new AuthenticatedUserDetails("patrick.star@bikini.bottom", UserType.BUS_DRIVER);
        doReturn(userDetails).when(httpSession).getAttribute(USER_DETAILS);
        doReturn(new Driver("patrick.star@bikini.bottom", new Bus("99ggg99", new Route("77")), true))
                .when(driverService).findDriverByEmail("patrick.star@bikini.bottom");
        // WHEN
        driverPageServlet.doGet(httpServletRequest, httpServletResponse);
        // THEN
        verify(servletContext).getRequestDispatcher(DRIVER_JSP_PATH);
        verify(requestDispatcher).forward(httpServletRequest, httpServletResponse);
        verify(httpServletRequest).setAttribute("driver",
                new Driver("patrick.star@bikini.bottom", new Bus("99ggg99", new Route("77")), true));
    }


    @Test
    void doPost_shouldInitiateSettingDriverAwarenessToTrue_andRedirectToDriverPage() throws IOException {
        // GIVEN
        doReturn(httpSession).when(httpServletRequest).getSession();
        var userDetails = new AuthenticatedUserDetails("yokel@driver.com", UserType.BUS_DRIVER);
        doReturn(userDetails).when(httpSession).getAttribute(USER_DETAILS);
        // WHEN
        driverPageServlet.doPost(httpServletRequest, httpServletResponse);
        // THEN
        verify(driverService).setDriverAwarenessToTrue("yokel@driver.com");
        verify(httpServletResponse).sendRedirect(DRIVER_PAGE_URI);
    }
}