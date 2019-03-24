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
import java.util.Locale;

import static com.papenko.project.constant.ApplicationEndpointsURIs.AdminPage.ADMIN_PAGE_URI;
import static com.papenko.project.constant.ApplicationEndpointsURIs.*;
import static com.papenko.project.constant.RequestAttributesNames.DISPLAY_LOGIN_ERROR_MESSAGE;
import static com.papenko.project.constant.RequestParametersNames.LOGIN_EMAIL;
import static com.papenko.project.constant.RequestParametersNames.LOGIN_PASSWORD;
import static com.papenko.project.constant.SessionAttributesNames.CURRENT_LANGUAGE;
import static com.papenko.project.constant.SessionAttributesNames.USER_DETAILS;
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
    private String language = "en";
    private Locale locale = new Locale(language);

    @Test
    void doGet_shouldForwardToLoginPage_andSetBrowserLanguageToSession_whenUserIsNotLoggedIn_andCurrentLanguageIsNull() throws Exception {
        // GIVEN
        doReturn(servletContext).when(loginPageServlet).getServletContext();
        doReturn(requestDispatcher).when(servletContext).getRequestDispatcher(anyString());
        doReturn(session).when(httpServletRequest).getSession();
        doReturn(null).when(session).getAttribute(USER_DETAILS);
        doReturn(locale).when(httpServletRequest).getLocale();
        doReturn(null).when(session).getAttribute(CURRENT_LANGUAGE);
        // WHEN
        loginPageServlet.doGet(httpServletRequest, httpServletResponse);
        // THEN
        verify(session).setAttribute(CURRENT_LANGUAGE, language);
        verify(servletContext).getRequestDispatcher(LOGIN_JSP_PATH);
        verify(requestDispatcher).forward(httpServletRequest, httpServletResponse);
    }

    @Test
    void doGet_shouldForwardToLoginPage_andNotSetBrowserLanguageToSession_whenUserIsNotLoggedIn_andCurrentLanguageIsNotNull() throws Exception {
        // GIVEN
        doReturn(servletContext).when(loginPageServlet).getServletContext();
        doReturn(requestDispatcher).when(servletContext).getRequestDispatcher(anyString());
        doReturn(session).when(httpServletRequest).getSession();
        doReturn(null).when(session).getAttribute(USER_DETAILS);
        doReturn(language).when(session).getAttribute(CURRENT_LANGUAGE);
        // WHEN
        loginPageServlet.doGet(httpServletRequest, httpServletResponse);
        // THEN
        verifyNoMoreInteractions(session);
        verify(servletContext).getRequestDispatcher(LOGIN_JSP_PATH);
        verify(requestDispatcher).forward(httpServletRequest, httpServletResponse);
    }

    @Test
    void doGet_shouldForwardToLogoutFirst_andNotSetBrowserLanguageToSession_whenUserIsLoggedIn() throws Exception {
        // GIVEN
        doReturn(servletContext).when(loginPageServlet).getServletContext();
        doReturn(requestDispatcher).when(servletContext).getRequestDispatcher(anyString());
        doReturn(session).when(httpServletRequest).getSession();
        doReturn(new AuthenticatedUserDetails(null, null)).when(session).getAttribute(USER_DETAILS);
        // WHEN
        loginPageServlet.doGet(httpServletRequest, httpServletResponse);
        // THEN
        verifyNoMoreInteractions(session);
        verify(servletContext).getRequestDispatcher(LOGOUT_FORM_URI);
        verify(requestDispatcher).forward(httpServletRequest, httpServletResponse);
    }

    @Test
    void doPost_shouldRedirectAdminToAdminPage_andSetAuthenticatedUserDetailsToSession_whenCorrectCredentialsSubmitted() throws ServletException, IOException {
        //GIVEN
        doReturn("correct@email.yes").when(httpServletRequest).getParameter(LOGIN_EMAIL);
        doReturn("correct_password").when(httpServletRequest).getParameter(LOGIN_PASSWORD);
        doReturn(true).when(loginService).checkCredentials("correct@email.yes", "correct_password");
        doReturn(session).when(httpServletRequest).getSession();
        doReturn(UserType.DEPOT_ADMIN).when(loginService).getUserType("correct@email.yes");
        var userDetails = new AuthenticatedUserDetails("correct@email.yes", UserType.DEPOT_ADMIN);
        //WHEN
        loginPageServlet.doPost(httpServletRequest, httpServletResponse);
        //THEN
        verify(httpServletResponse).sendRedirect(ADMIN_PAGE_URI);
        verify(session).setAttribute(USER_DETAILS, userDetails);
    }

    @Test
    void doPost_shouldRedirectDriverToDriverPage_andSetAuthenticatedUserDetailsToSession_whenCorrectCredentialsSubmitted() throws ServletException, IOException {
        //GIVEN
        doReturn("correct@email.yes").when(httpServletRequest).getParameter(LOGIN_EMAIL);
        doReturn("correct_password").when(httpServletRequest).getParameter(LOGIN_PASSWORD);
        doReturn(true).when(loginService).checkCredentials("correct@email.yes", "correct_password");
        doReturn(session).when(httpServletRequest).getSession();
        doReturn(UserType.BUS_DRIVER).when(loginService).getUserType("correct@email.yes");
        var userDetails = new AuthenticatedUserDetails("correct@email.yes", UserType.BUS_DRIVER);
        //WHEN
        loginPageServlet.doPost(httpServletRequest, httpServletResponse);
        //THEN
        verify(httpServletResponse).sendRedirect(DRIVER_PAGE_URI);
        verify(session).setAttribute(USER_DETAILS, userDetails);
    }

    @Test
    void doPost_shouldForwardToLoginPage_andSetErrorMessageToRequest_whenWrongCredentialsSubmitted() throws ServletException, IOException {
        // GIVEN
        doReturn("wrong@email.yes").when(httpServletRequest).getParameter(LOGIN_EMAIL);
        doReturn("wrong_password").when(httpServletRequest).getParameter(LOGIN_PASSWORD);
        doReturn(servletContext).when(loginPageServlet).getServletContext();
        doReturn(requestDispatcher).when(servletContext).getRequestDispatcher(anyString());
        doReturn(false).when(loginService).checkCredentials(anyString(), anyString());
        //WHEN
        loginPageServlet.doPost(httpServletRequest, httpServletResponse);
        //THEN
        verify(servletContext).getRequestDispatcher(LOGIN_JSP_PATH);
        verify(requestDispatcher).forward(httpServletRequest, httpServletResponse);
        verify(httpServletRequest).setAttribute(DISPLAY_LOGIN_ERROR_MESSAGE, true);
    }
}
