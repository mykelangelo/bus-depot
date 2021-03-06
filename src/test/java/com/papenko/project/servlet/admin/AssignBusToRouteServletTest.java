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
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.IOException;

import static com.papenko.project.constant.ApplicationEndpointsURIs.AdminPage.ADMIN_PAGE_URI;
import static com.papenko.project.constant.RequestAttributesNames.LAST_SUBMIT_STATUS_MESSAGE;
import static com.papenko.project.constant.RequestParametersNames.BUS_SERIAL;
import static com.papenko.project.constant.RequestParametersNames.ROUTE_NAME;
import static org.mockito.Mockito.*;

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
    @Mock
    AdminMessagesLocalization localization;
    @Mock
    HttpSession session;

    @Test
    void init_shouldBeInitialized() {
        doReturn(mock(DataSource.class)).when(assignBusToRouteServlet).getDataSource();

        assignBusToRouteServlet.init();
    }

    @Test
    void doPost_shouldSetBusToRoute_andRedirectToAdminPage_andSetLastSubmitStatusMessageAsParameter() throws IOException {
        // GIVEN
        doReturn(session).when(httpServletRequest).getSession();
        doReturn("You assigned bus with serial number AI7007AA to route with name 7L")
                .when(localization).getMessage(httpServletRequest, "status_assign-bus-to-route", "AI7007AA", "7L");
        doReturn("AI7007AA").when(httpServletRequest).getParameter(BUS_SERIAL);
        doReturn("7L").when(httpServletRequest).getParameter(ROUTE_NAME);
        // WHEN
        assignBusToRouteServlet.doPost(httpServletRequest, httpServletResponse);
        // THEN
        verify(adminService).assignBusToRoute("AI7007AA", "7L");
        verify(session).setAttribute(LAST_SUBMIT_STATUS_MESSAGE, "You assigned bus with serial number AI7007AA to route with name 7L");
        verify(httpServletResponse).sendRedirect(ADMIN_PAGE_URI);
    }
}