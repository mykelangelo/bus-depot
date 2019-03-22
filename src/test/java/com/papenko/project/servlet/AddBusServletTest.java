package com.papenko.project.servlet;

import com.papenko.project.service.AdminService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.papenko.project.constant.RequestParametersNames.BUS_SERIAL;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AddBusServletTest {
    @Spy
    @InjectMocks
    AddBusServlet addBusServlet;
    @Mock
    AdminService adminService;
    @Mock
    HttpServletRequest httpServletRequest;
    @Mock
    HttpServletResponse httpServletResponse;

    @Test
    void doPost_shouldAddToDatabaseNewBusWithSerialGiven_andRedirectToAdminPage_andSetLastSubmitStatusMessageAsParameter() throws IOException {
        // GIVEN
        doReturn("AI2352IA").when(httpServletRequest).getParameter(BUS_SERIAL);
        // WHEN
        addBusServlet.doPost(httpServletRequest, httpServletResponse);
        // THEN
        verify(adminService).addBus("AI2352IA");
        verify(httpServletResponse).sendRedirect("/admin?lastSubmitStatusMessage=You added new bus with serial number AI2352IA");
    }
}