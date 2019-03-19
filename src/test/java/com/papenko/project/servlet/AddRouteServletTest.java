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

import static com.papenko.project.constant.RequestParametersNames.ROUTE_NAME;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AddRouteServletTest {
    @Spy
    @InjectMocks
    AddRouteServlet addRouteServlet;
    @Mock
    AdminService adminService;
    @Mock
    HttpServletRequest httpServletRequest;
    @Mock
    HttpServletResponse httpServletResponse;

    @Test
    void doPost_shouldAddNewRouteWithNameGivenToDatabase_andRedirectToAdminPage_andSetLastSubmitStatusMessageAsParameter() throws IOException {
        // GIVEN
        doReturn("N1").when(httpServletRequest).getParameter(ROUTE_NAME);
        // WHEN
        addRouteServlet.doPost(httpServletRequest, httpServletResponse);
        // THEN
        verify(adminService).addRoute("N1");
        verify(httpServletResponse).sendRedirect("/admin?lastSubmitStatusMessage=You added new route with name N1");
    }
}