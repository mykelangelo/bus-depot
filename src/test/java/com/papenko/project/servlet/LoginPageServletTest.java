package com.papenko.project.servlet;

import com.papenko.project.entity.AuthenticatedUserDetails;
import com.papenko.project.entity.UserType;
import com.papenko.project.service.LoginService;
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
import java.io.IOException;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoginPageServletTest {
    @Spy
    @InjectMocks
    LoginPageServlet loginPageServlet;
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
    void doGet_shouldForwardToLoginPage_andSetNullAsUserDetailsToSession() throws Exception {
        // GIVEN
        doReturn(servletContext).when(loginPageServlet).getServletContext();
        doReturn(requestDispatcher).when(servletContext).getRequestDispatcher(anyString());
        doReturn(session).when(httpServletRequest).getSession();
        // WHEN
        loginPageServlet.doGet(httpServletRequest, httpServletResponse);
        // THEN
        verify(servletContext).getRequestDispatcher("/WEB-INF/login.jsp");
        verify(requestDispatcher).forward(httpServletRequest, httpServletResponse);
        verify(session).setAttribute("user_details", null);
    }

    @Test
    void doPost_shouldRedirectAdminToAdminLandingPage_andSetAuthenticatedUserDetailsToSession_whenCorrectCredentialsSubmitted() throws ServletException, IOException {
        //GIVEN
        doReturn("correct@email.yes").when(httpServletRequest).getParameter("email");
        doReturn("correct_password").when(httpServletRequest).getParameter("password");
        doReturn(true).when(loginService).checkCredentials("correct@email.yes", "correct_password");
        doReturn(session).when(httpServletRequest).getSession();
        doReturn(UserType.DEPOT_ADMIN).when(loginService).getUserType("correct@email.yes");
        var userDetails = new AuthenticatedUserDetails("correct@email.yes", UserType.DEPOT_ADMIN);
        //WHEN
        loginPageServlet.doPost(httpServletRequest, httpServletResponse);
        //THEN
        verify(httpServletResponse).sendRedirect("/admin");
        verify(session).setAttribute("user_details", userDetails);
    }

    @Test
    void doPost_shouldRedirectDriverToDriverLandingPage_andSetAuthenticatedUserDetailsToSession_whenCorrectCredentialsSubmitted() throws ServletException, IOException {
        //GIVEN
        doReturn("correct@email.yes").when(httpServletRequest).getParameter("email");
        doReturn("correct_password").when(httpServletRequest).getParameter("password");
        doReturn(true).when(loginService).checkCredentials("correct@email.yes", "correct_password");
        doReturn(session).when(httpServletRequest).getSession();
        doReturn(UserType.BUS_DRIVER).when(loginService).getUserType("correct@email.yes");
        var userDetails = new AuthenticatedUserDetails("correct@email.yes", UserType.BUS_DRIVER);
        //WHEN
        loginPageServlet.doPost(httpServletRequest, httpServletResponse);
        //THEN
        verify(httpServletResponse).sendRedirect("/driver");
        verify(session).setAttribute("user_details", userDetails);
    }

    @Test
    void doPost_shouldForwardToLoginPage_andSetErrorMessageToRequest_whenWrongCredentialsSubmitted() throws ServletException, IOException {
        // GIVEN
        doReturn("wrong@email.yes").when(httpServletRequest).getParameter("email");
        doReturn("wrong_password").when(httpServletRequest).getParameter("password");
        doReturn(servletContext).when(loginPageServlet).getServletContext();
        doReturn(requestDispatcher).when(servletContext).getRequestDispatcher(anyString());
        doReturn(false).when(loginService).checkCredentials(anyString(), anyString());
        //WHEN
        loginPageServlet.doPost(httpServletRequest, httpServletResponse);
        //THEN
        verify(servletContext).getRequestDispatcher("/WEB-INF/login.jsp");
        verify(requestDispatcher).forward(httpServletRequest, httpServletResponse);
        verify(httpServletRequest).setAttribute("loginErrorMessage", "Invalid email or password");
    }
}
