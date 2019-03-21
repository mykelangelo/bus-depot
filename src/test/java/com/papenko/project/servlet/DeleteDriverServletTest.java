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

import static com.papenko.project.constant.RequestParametersNames.DRIVER_EMAIL;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

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

    @Test
    void doPost_shouldDeleteFromDatabaseDriverWithEmailGiven_andRedirectToAdminPage_andSetLastSubmitStatusMessageAsParameter() throws IOException {
        // GIVEN
        doReturn("jackie.Jr@janitor.com").when(httpServletRequest).getParameter(DRIVER_EMAIL);
        // WHEN
        deleteDriverServlet.doPost(httpServletRequest, httpServletResponse);
        // THEN
        adminService.deleteDriver("jackie.Jr@janitor.com");
        verify(httpServletResponse).sendRedirect("/admin?lastSubmitStatusMessage=You deleted driver with email jackie.Jr@janitor.com");
    }
}