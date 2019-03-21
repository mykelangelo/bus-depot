package com.papenko.project.servlet;

import com.papenko.project.entity.Bus;
import com.papenko.project.entity.Driver;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteBusServletTest {
    @Spy
    @InjectMocks
    DeleteBusServlet deleteBusServlet;
    @Mock
    AdminService adminService;
    @Mock
    HttpServletRequest httpServletRequest;
    @Mock
    HttpServletResponse httpServletResponse;

    @Test
    void doPost_shouldDeleteFromDatabaseBusWithSerialGiven_andRedirectToAdminPage_andSetLastSubmitStatusMessageAsParameter_whenBusIsUnused() throws IOException {
        // GIVEN
        doReturn("MY F8").when(httpServletRequest).getParameter(BUS_SERIAL);
        doReturn(null).when(adminService).getDriverInBus("MY F8");
        // WHEN
        deleteBusServlet.doPost(httpServletRequest, httpServletResponse);
        // THEN
        verify(adminService).deleteBus("MY F8");
        verify(httpServletResponse).sendRedirect("/admin?lastSubmitStatusMessage=You deleted bus with serial number MY F8");
    }

    @Test
    void doPost_shouldNotDeleteFromDatabaseBusWithSerialGiven_andRedirectToAdminPage_andSetLastSubmitStatusMessageAsParameter_whenBusIsUsed() throws IOException {
        // GIVEN
        doReturn("KY73").when(httpServletRequest).getParameter(BUS_SERIAL);
        doReturn(new Driver("dude@drives.car", new Bus("KY73", null), false)).when(adminService).getDriverInBus("KY73");
        // WHEN
        deleteBusServlet.doPost(httpServletRequest, httpServletResponse);
        // THEN
        verifyNoMoreInteractions(adminService);
        verify(httpServletResponse).sendRedirect("/admin?lastSubmitStatusMessage=You tried to delete bus with serial number KY73 but it's used - please assign driver dude@drives.car to other bus before deleting this bus");
    }
}