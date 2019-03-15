package com.papenko.project.servlet;

import com.papenko.project.service.AdminService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.papenko.project.constant.RequestParametersNames.DRIVER_EMAIL;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@DisplayName("AssignDriverToBusServlet")
@ExtendWith(MockitoExtension.class)
class VacateDriverServletTest {
    @Spy
    @InjectMocks
    VacateDriverServlet vacateDriver;
    @Mock
    AdminService adminService;
    @Mock
    HttpServletRequest httpServletRequest;
    @Mock
    HttpServletResponse httpServletResponse;

    @Test
    void doPost_shouldVacateDriverFromBus_andRedirectToAdminPage_andSetLastSubmitStatusMessageAsParameter() throws IOException {
        // GIVEN
        doReturn("bob.jenkins@gmail.com").when(httpServletRequest).getParameter(DRIVER_EMAIL);
        // WHEN
        vacateDriver.doPost(httpServletRequest, httpServletResponse);
        // THEN
        verify(adminService).vacateDriverFromBus("bob.jenkins@gmail.com");
        verify(httpServletResponse).sendRedirect("/admin?lastSubmitStatusMessage=You vacated driver with email bob.jenkins@gmail.com");
    }
}