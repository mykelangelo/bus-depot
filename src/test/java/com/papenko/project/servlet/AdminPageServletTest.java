package com.papenko.project.servlet;

import com.papenko.project.service.AdminService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@DisplayName("AdminPageServlet")
@ExtendWith(MockitoExtension.class)
class AdminPageServletTest {
    @Spy
    @InjectMocks
    AdminPageServlet adminPage;
    @Mock
    AdminService adminService;
    @Mock
    HttpServletRequest httpServletRequest;
    @Mock
    HttpServletResponse httpServletResponse;
    @Mock
    ServletContext servletContext;
    @Mock
    RequestDispatcher requestDispatcher;

    @Test
    void doGet_shouldForwardToAdminPage_andSetDriversEmailsAndBusesSerialsAndRoutesNamesToRequest_andNoSetLastSubmitMessage_whenLastSubmitMessageIsNotPresentInQueryParam() throws ServletException, IOException {
        // GIVEN
        doReturn(servletContext).when(adminPage).getServletContext();
        doReturn(requestDispatcher).when(servletContext).getRequestDispatcher(anyString());
        doReturn(List.of("alexa@company.com", "bob.jenkins@gmail.com")).when(adminService).getDriversEmails();
        doReturn(List.of("AA4444AA", "II1111II")).when(adminService).getBusesSerials();
        doReturn(List.of("7L", "7k", "71")).when(adminService).getRoutesNames();
        // WHEN
        adminPage.doGet(httpServletRequest, httpServletResponse);
        // THEN
        verify(servletContext).getRequestDispatcher("/admin.jsp");
        verify(requestDispatcher).forward(httpServletRequest, httpServletResponse);
        verify(httpServletRequest).setAttribute("driversEmails", List.of("alexa@company.com", "bob.jenkins@gmail.com"));
        verify(httpServletRequest).setAttribute("busesSerials", List.of("AA4444AA", "II1111II"));
        verify(httpServletRequest).setAttribute("routesNames", List.of("7L", "7k", "71"));
    }

    @Test
    void doGet_shouldForwardToAdminPage_andSetDriversEmailsAndBusesSerialsAndRoutesNamesToRequest_andSetLastSubmitMessage_whenLastSubmitMessageIsPresentInQueryParam() throws ServletException, IOException {
        // GIVEN
        doReturn(servletContext).when(adminPage).getServletContext();
        doReturn(requestDispatcher).when(servletContext).getRequestDispatcher(anyString());
        doReturn(List.of("alexa@company.com", "bob.jenkins@gmail.com")).when(adminService).getDriversEmails();
        doReturn(List.of("AA4444AA", "II1111II")).when(adminService).getBusesSerials();
        doReturn(List.of("7L", "7k", "71")).when(adminService).getRoutesNames();
        doReturn("Jokes are not funny").when(httpServletRequest).getParameter("lastSubmitStatusMessage");
        // WHEN
        adminPage.doGet(httpServletRequest, httpServletResponse);
        // THEN
        verify(servletContext).getRequestDispatcher("/admin.jsp");
        verify(requestDispatcher).forward(httpServletRequest, httpServletResponse);
        verify(httpServletRequest).setAttribute("driversEmails", List.of("alexa@company.com", "bob.jenkins@gmail.com"));
        verify(httpServletRequest).setAttribute("busesSerials", List.of("AA4444AA", "II1111II"));
        verify(httpServletRequest).setAttribute("routesNames", List.of("7L", "7k", "71"));
        verify(httpServletRequest).setAttribute("lastSubmitStatusMessage", "Jokes are not funny");
    }
}