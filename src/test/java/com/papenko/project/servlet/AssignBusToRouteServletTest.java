package com.papenko.project.servlet;

import com.papenko.project.service.AdminService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@DisplayName("AssignBusToRouteServlet")
@ExtendWith(MockitoExtension.class)
class AssignBusToRouteServletTest {
    @Spy
    @InjectMocks
    AssignBusToRouteServlet assignBusToRouteServlet;
    @Mock
    AdminService adminService;
    @Mock
    HttpServletRequest httpServletRequest;
    @Mock
    HttpServletResponse httpServletResponse;


    @Test
    void doPost_shouldSetBusToRoute_andSetLastSubmitStatusMessageToRequest_whenBothParametersAreSet() throws IOException {
        // GIVEN
        doReturn("AI7007AA").when(httpServletRequest).getParameter("bus-serial");
        doReturn("7L").when(httpServletRequest).getParameter("route-name");
        // WHEN
        assignBusToRouteServlet.doPost(httpServletRequest, httpServletResponse);
        // THEN
        verify(adminService).assignBusToRoute("AI7007AA", "7L");
        verify(httpServletResponse).sendRedirect("/admin?lastSubmitStatusMessage=You assigned bus with serial number AI7007AA to route with name 7L");
    }
}