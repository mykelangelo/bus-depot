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
import static com.papenko.project.constant.RequestParametersNames.DRIVER_PASSWORD;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AddDriverServletTest {
    @Spy
    @InjectMocks
    AddDriverServlet addDriverServlet;
    @Mock
    AdminService adminService;
    @Mock
    HttpServletRequest httpServletRequest;
    @Mock
    HttpServletResponse httpServletResponse;

    @Test
    void doPost_shouldAddToDatabaseNewDriverWithEmailAndPasswordGiven_andRedirectToAdminPage_andSetLastSubmitStatusMessageAsParameter() throws IOException {
        // GIVEN
        doReturn("black@mirror.com").when(httpServletRequest).getParameter(DRIVER_EMAIL);
        doReturn("somePasswordThatIsBlackInMirror").when(httpServletRequest).getParameter(DRIVER_PASSWORD);
        // WHEN
        addDriverServlet.doPost(httpServletRequest, httpServletResponse);
        // THEN
        verify(adminService).addDriver("black@mirror.com", "somePasswordThatIsBlackInMirror");
        verify(httpServletResponse).sendRedirect("/admin?lastSubmitStatusMessage=You added new driver with email black@mirror.com");
    }
}