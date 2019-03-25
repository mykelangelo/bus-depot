package com.papenko.project.servlet.admin;

import com.papenko.project.entity.Bus;
import com.papenko.project.entity.Driver;
import com.papenko.project.entity.Route;
import com.papenko.project.service.AdminService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.IOException;

import static com.papenko.project.constant.ApplicationEndpointsURIs.AdminPage.ADMIN_PAGE_URI;
import static com.papenko.project.constant.RequestAttributesNames.LAST_SUBMIT_STATUS_MESSAGE;
import static com.papenko.project.constant.RequestParametersNames.BUS_SERIAL;
import static com.papenko.project.constant.RequestParametersNames.DRIVER_EMAIL;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AssignDriverToBusServletTest {
    @Spy
    @InjectMocks
    AssignDriverToBusServlet assignDriverToBusServlet;
    @Mock
    AdminService adminService;
    @Mock
    HttpServletRequest httpServletRequest;
    @Mock
    HttpServletResponse httpServletResponse;
    @Mock
    AdminMessagesLocalization localization;
    @Mock
    HttpSession session;

    @Test
    void init_shouldBeInitialized() {
        doReturn(mock(DataSource.class)).when(assignDriverToBusServlet).getDataSource();

        assignDriverToBusServlet.init();
    }

    @Test
    void doPost_shouldSetDriverToBus_andRedirectToAdminPage_andSetLastSubmitStatusMessageAsParameter_whenBusWasPreviouslyEmpty() throws IOException {
        // GIVEN
        doReturn(session).when(httpServletRequest).getSession();
        doReturn("You assigned driver with email bob.jenkins@gmail.com to bus with serial number AA4444AA")
                .when(localization).getMessage(httpServletRequest, "status_assign-driver-to-bus", "bob.jenkins@gmail.com", "AA4444AA");
        doReturn("bob.jenkins@gmail.com").when(httpServletRequest).getParameter(DRIVER_EMAIL);
        doReturn("AA4444AA").when(httpServletRequest).getParameter(BUS_SERIAL);
        doReturn(null).when(adminService).getDriverInBus("AA4444AA");
        // WHEN
        assignDriverToBusServlet.doPost(httpServletRequest, httpServletResponse);
        // THEN
        verify(adminService).assignDriverToBus("bob.jenkins@gmail.com", "AA4444AA");
        verify(session).setAttribute(LAST_SUBMIT_STATUS_MESSAGE, "You assigned driver with email bob.jenkins@gmail.com to bus with serial number AA4444AA");
        verify(httpServletResponse).sendRedirect(ADMIN_PAGE_URI);
    }

    @Test
    void doPost_shouldRedirectToAdminPage_andSetLastSubmitStatusMessageAsParameter_whenBusWasAlreadyOccupied() throws IOException {
        // GIVEN
        doReturn(session).when(httpServletRequest).getSession();
        doReturn("You tried to assign driver with email new@driver to bus with serial number OI0102OI, but this bus is already used by driver with email old@driver")
                .when(localization).getMessage(httpServletRequest, "status_try-assign-driver-to-bus", "new@driver", "OI0102OI", "old@driver");
        doReturn("new@driver").when(httpServletRequest).getParameter(DRIVER_EMAIL);
        doReturn("OI0102OI").when(httpServletRequest).getParameter(BUS_SERIAL);
        doReturn(new Driver("old@driver", new Bus("OI0102OI", new Route("999")), true))
                .when(adminService).getDriverInBus("OI0102OI");
        // WHEN
        assignDriverToBusServlet.doPost(httpServletRequest, httpServletResponse);
        // THEN
        verify(adminService).getDriverInBus("OI0102OI");
        verifyNoMoreInteractions(adminService);
        verify(session).setAttribute(LAST_SUBMIT_STATUS_MESSAGE, "You tried to assign driver with email new@driver to bus with serial number OI0102OI, but this bus is already used by driver with email old@driver");
        verify(httpServletResponse).sendRedirect(ADMIN_PAGE_URI);
    }
}