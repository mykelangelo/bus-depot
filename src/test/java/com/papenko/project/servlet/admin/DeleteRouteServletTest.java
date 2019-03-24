package com.papenko.project.servlet.admin;

import com.papenko.project.entity.Bus;
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
import java.io.IOException;
import java.util.List;

import static com.papenko.project.constant.RequestParametersNames.ROUTE_NAME;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteRouteServletTest {
    @Spy
    @InjectMocks
    DeleteRouteServlet deleteRouteServlet;
    @Mock
    AdminService adminService;
    @Mock
    HttpServletRequest httpServletRequest;
    @Mock
    HttpServletResponse httpServletResponse;
    @Mock
    AdminMessagesLocalization localization;

    @Test
    void doPost_shouldDeleteFromDatabaseRouteWithNameGiven_andRedirectToAdminPage_andSetLastSubmitStatusMessageAsParameter_whenRouteIsUnused() throws IOException {
        // GIVEN
        doReturn("You deleted route with name F8").when(localization).getMessage(httpServletRequest, "status_delete-route", "F8");
        doReturn("F8").when(httpServletRequest).getParameter(ROUTE_NAME);
        doReturn(List.of()).when(adminService).getBusesOnRoute("F8");
        // WHEN
        deleteRouteServlet.doPost(httpServletRequest, httpServletResponse);
        // THEN
        verify(adminService).deleteRoute("F8");
        verify(httpServletResponse).sendRedirect("/admin?lastSubmitStatusMessage=You deleted route with name F8");
    }

    @Test
    void doPost_shouldNotDeleteFromDatabaseRouteWithNameGiven_andRedirectToAdminPage_andSetLastSubmitStatusMessageAsParameter_whenRouteIsUsed() throws IOException {
        // GIVEN
        doReturn("You tried to delete route with name K1 but it is used - please assign bus(es) NRC 7, PM 6 to other route(s) before deleting this route")
                .when(localization).getMessage(httpServletRequest, "status_try-delete-route", "K1", "NRC 7, PM 6");
        doReturn("K1").when(httpServletRequest).getParameter(ROUTE_NAME);
        doReturn(List.of(new Bus("NRC 7", new Route("K1")), new Bus("PM 6", new Route("K1")))).when(adminService).getBusesOnRoute("K1");
        // WHEN
        deleteRouteServlet.doPost(httpServletRequest, httpServletResponse);
        // THEN
        verifyNoMoreInteractions(adminService);
        verify(httpServletResponse).sendRedirect("/admin?lastSubmitStatusMessage=You tried to delete route with name K1 but it is used - please assign bus(es) NRC 7, PM 6 to other route(s) before deleting this route");
    }
}