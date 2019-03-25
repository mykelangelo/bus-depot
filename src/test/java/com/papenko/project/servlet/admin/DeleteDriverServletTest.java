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
import static com.papenko.project.constant.RequestParametersNames.DRIVER_EMAIL;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteDriverServletTest {
    @Spy
    @InjectMocks
    DeleteDriverServlet deleteDriverServlet;
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
        doReturn(mock(DataSource.class)).when(deleteDriverServlet).getDataSource();

        deleteDriverServlet.init();
    }

    @Test
    void doPost_shouldDeleteFromDatabaseDriverWithEmailGiven_andRedirectToAdminPage_andSetLastSubmitStatusMessageAsParameter() throws IOException {
        // GIVEN
        doReturn(session).when(httpServletRequest).getSession();
        doReturn("You deleted driver with email jackie.Jr@janitor.com").when(localization).getMessage(httpServletRequest, "status_delete-driver", "jackie.Jr@janitor.com");
        doReturn("jackie.Jr@janitor.com").when(httpServletRequest).getParameter(DRIVER_EMAIL);
        // WHEN
        deleteDriverServlet.doPost(httpServletRequest, httpServletResponse);
        // THEN
        verify(adminService).deleteDriver("jackie.Jr@janitor.com");
        verify(session).setAttribute(LAST_SUBMIT_STATUS_MESSAGE, "You deleted driver with email jackie.Jr@janitor.com");
        verify(httpServletResponse).sendRedirect(ADMIN_PAGE_URI);
    }
}