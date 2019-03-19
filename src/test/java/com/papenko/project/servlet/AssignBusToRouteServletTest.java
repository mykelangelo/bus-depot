package com.papenko.project.servlet;

import com.papenko.project.service.AdminService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.papenko.project.constant.RequestParametersNames.BUS_SERIAL;
import static com.papenko.project.constant.RequestParametersNames.ROUTE_NAME;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AssignBusToRouteServletTest {
    @InjectMocks
    AssignBusToRouteServlet assignBusToRouteServlet;
    @Mock
    AdminService adminService;
    @Mock
    HttpServletRequest httpServletRequest;
    @Mock
    HttpServletResponse httpServletResponse;


    @Test
    void doPost_shouldSetBusToRoute_andRedirectToAdminPage_andSetLastSubmitStatusMessageAsParameter() throws IOException {
        // GIVEN
        doReturn("AI7007AA").when(httpServletRequest).getParameter(BUS_SERIAL);
        doReturn("7L").when(httpServletRequest).getParameter(ROUTE_NAME);
        // WHEN
        assignBusToRouteServlet.doPost(httpServletRequest, httpServletResponse);
        // THEN
        verify(adminService).assignBusToRoute("AI7007AA", "7L");
        verify(httpServletResponse).sendRedirect("/admin?lastSubmitStatusMessage=You assigned bus with serial number AI7007AA to route with name 7L");
    }
}