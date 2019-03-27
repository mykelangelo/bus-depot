package com.papenko.project.servlet;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.papenko.project.constant.ApplicationEndpointsURIs.LOGIN_PAGE_URI;
import static com.papenko.project.constant.SessionAttributesNames.CURRENT_LANGUAGE;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class LogoutServletTest {
    @Mock
    HttpServletRequest httpServletRequest;
    @Mock
    HttpServletResponse httpServletResponse;
    @Mock
    HttpSession postAuthorizationSession;
    @Mock
    HttpSession preAuthorizationSession;
    private LogoutServlet logoutServlet = new LogoutServlet();

    @Test
    void doGet_shouldInvalidateSession_butPreserveLanguage_andRedirectToLoginPage() throws IOException {
        // GIVEN
        doReturn("uk").when(postAuthorizationSession).getAttribute(CURRENT_LANGUAGE);
        doReturn(postAuthorizationSession, preAuthorizationSession).when(httpServletRequest).getSession();
        // WHEN
        logoutServlet.doGet(httpServletRequest, httpServletResponse);
        // THEN
        verify(postAuthorizationSession).invalidate();
        verify(preAuthorizationSession).setAttribute(CURRENT_LANGUAGE, "uk");
        verify(httpServletResponse).sendRedirect(LOGIN_PAGE_URI);
    }
}