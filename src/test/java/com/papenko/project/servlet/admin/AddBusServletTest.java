package com.papenko.project.servlet.admin;

import com.papenko.project.service.AdminService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;

import static com.papenko.project.constant.RequestParametersNames.BUS_SERIAL;
import static org.mockito.Mockito.*;

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
    @Mock
    AdminMessagesLocalization localization;

    @Test
    void init_shouldBeInitialized() {
        doReturn(mock(DataSource.class)).when(addBusServlet).getDataSource();

        addBusServlet.init();
    }

    @Test
    void doPost_shouldAddToDatabaseNewBusWithSerialGiven_andRedirectToAdminPage_andSetLastSubmitStatusMessageAsParameter() throws IOException {
        // GIVEN
        doReturn("You added new bus with serial number AI2352IA").when(localization).getMessage(httpServletRequest, "status_add-bus", "AI2352IA");
        doReturn("AI2352IA").when(httpServletRequest).getParameter(BUS_SERIAL);
        // WHEN
        addBusServlet.doPost(httpServletRequest, httpServletResponse);
        // THEN
        verify(adminService).addBus("AI2352IA");
        verify(httpServletResponse).sendRedirect("/admin?lastSubmitStatusMessage=You added new bus with serial number AI2352IA");
    }
}