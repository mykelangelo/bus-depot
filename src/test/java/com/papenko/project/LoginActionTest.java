package com.papenko.project;

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
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoginActionTest {
    @Spy
    @InjectMocks
    LoginAction loginAction;
    @Mock
    LoginService loginService;
    @Mock
    HttpServletRequest httpServletRequest;
    @Mock
    HttpServletResponse httpServletResponse;
    @Mock
    ServletContext servletContext;
    @Mock
    RequestDispatcher requestDispatcher;
    @Mock
    HttpSession session;

    @Test
    void init_createsLoginService() {
        DataSource dataSourceMock = mock(DataSource.class);
        // GIVEN
        loginAction.loginService = null;
        doReturn(dataSourceMock).when(loginAction).getDataSource();
        // WHEN
        loginAction.init();
        // THEN
        assertNotNull(loginAction.loginService);
    }

    @Test
    void doGet_shouldForwardToLoginPage() throws Exception {
        // GIVEN
        doReturn(servletContext).when(loginAction).getServletContext();
        doReturn(requestDispatcher).when(servletContext).getRequestDispatcher(anyString());
        // WHEN
        loginAction.doGet(httpServletRequest, httpServletResponse);
        // THEN
        verify(servletContext).getRequestDispatcher("/login.jsp");
        verify(requestDispatcher).forward(httpServletRequest, httpServletResponse);
    }

    @Test
    void doPost_shouldRedirectAdminToAdminLandingPage_andSetEmailToSession_whenCorrectCredentialsSubmitted() throws ServletException, IOException {
        //GIVEN
        doReturn("correct@email.yes").when(httpServletRequest).getParameter("email");
        doReturn("correct_password").when(httpServletRequest).getParameter("password");
        doReturn(true).when(loginService).checkCredentials("correct@email.yes", "correct_password");
        doReturn(session).when(httpServletRequest).getSession();
        doReturn("/admin.jsp").when(loginService).getLandingAdminOrDriverPageDependingOnTypeOfUser("correct@email.yes");
        //WHEN
        loginAction.doPost(httpServletRequest, httpServletResponse);
        //THEN
        verify(httpServletResponse).sendRedirect("/admin.jsp");
        verify(session).setAttribute("email", "correct@email.yes");
    }

    @Test
    void doPost_shouldRedirectDriverToDriverLandingPage_andSetEmailToSession_whenCorrectCredentialsSubmitted() throws ServletException, IOException {
        //GIVEN
        doReturn("correct@email.yes").when(httpServletRequest).getParameter("email");
        doReturn("correct_password").when(httpServletRequest).getParameter("password");
        doReturn(true).when(loginService).checkCredentials("correct@email.yes", "correct_password");
        doReturn(session).when(httpServletRequest).getSession();
        doReturn("/driver.jsp").when(loginService).getLandingAdminOrDriverPageDependingOnTypeOfUser("correct@email.yes");
        //WHEN
        loginAction.doPost(httpServletRequest, httpServletResponse);
        //THEN
        verify(httpServletResponse).sendRedirect("/driver.jsp");
        verify(session).setAttribute("email", "correct@email.yes");
    }

    @Test
    void doPost_shouldForwardToLoginPage_andSetErrorMessageToRequest_whenWrongCredentialsSubmitted() throws ServletException, IOException {
        // GIVEN
        doReturn("wrong@email.yes").when(httpServletRequest).getParameter("email");
        doReturn("wrong_password").when(httpServletRequest).getParameter("password");
        doReturn(servletContext).when(loginAction).getServletContext();
        doReturn(requestDispatcher).when(servletContext).getRequestDispatcher(anyString());
        doReturn(false).when(loginService).checkCredentials(anyString(), anyString());
        //WHEN
        loginAction.doPost(httpServletRequest, httpServletResponse);
        //THEN
        verify(servletContext).getRequestDispatcher("/login.jsp");
        verify(requestDispatcher).forward(httpServletRequest, httpServletResponse);
        verify(httpServletRequest).setAttribute("loginErrorMessage", "Invalid email or password");
    }
}
