package com.papenko.project.servlet.admin;

import com.papenko.project.service.AdminService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;

import static com.papenko.project.constant.RequestParametersNames.DRIVER_EMAIL;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VacateDriverServletTest {
    @InjectMocks
    VacateDriverServlet vacateDriverServlet;
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
        doReturn(mock(DataSource.class)).when(vacateDriverServlet).getDataSource();

        vacateDriverServlet.init();
    }

    @Test
    void doPost_shouldVacateDriverFromBus_andRedirectToAdminPage_andSetLastSubmitStatusMessageAsParameter() throws IOException {
        // GIVEN
        doReturn("You vacated driver with email bob.jenkins@gmail.com").when(localization).getMessage(httpServletRequest, "status_vacate-driver", "bob.jenkins@gmail.com");
        doReturn("bob.jenkins@gmail.com").when(httpServletRequest).getParameter(DRIVER_EMAIL);
        // WHEN
        vacateDriverServlet.doPost(httpServletRequest, httpServletResponse);
        // THEN
        verify(adminService).vacateDriverFromBus("bob.jenkins@gmail.com");
        verify(httpServletResponse).sendRedirect("/admin?lastSubmitStatusMessage=You vacated driver with email bob.jenkins@gmail.com");
    }
}