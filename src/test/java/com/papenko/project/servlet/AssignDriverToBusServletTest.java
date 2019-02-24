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

@DisplayName("AssignDriverToBusServlet")
@ExtendWith(MockitoExtension.class)
class AssignDriverToBusServletTest {
    @Spy
    @InjectMocks
    AssignDriverToBusServlet assignDriverToBus;
    @Mock
    AdminService adminService;
    @Mock
    HttpServletRequest httpServletRequest;
    @Mock
    HttpServletResponse httpServletResponse;

    @Test
    void doPost_shouldSetDriverToBus_andSetLastSubmitStatusMessageToRequest_whenBothParametersAreSet() throws ServletException, IOException {
        // GIVEN
        doReturn("bob.jenkins@gmail.com").when(httpServletRequest).getParameter("driver-email");
        doReturn("AA4444AA").when(httpServletRequest).getParameter("bus-serial");
        // WHEN
        assignDriverToBus.doPost(httpServletRequest, httpServletResponse);
        // THEN
        verify(adminService).assignDriverToBus("bob.jenkins@gmail.com", "AA4444AA");
        verify(httpServletResponse).sendRedirect("/admin?lastSubmitStatusMessage=You assigned driver with email bob.jenkins@gmail.com to bus with serial number AA4444AA");
    }
}