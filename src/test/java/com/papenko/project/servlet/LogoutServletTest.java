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
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class LogoutServletTest {
    @Mock
    HttpServletRequest httpServletRequest;
    @Mock
    HttpServletResponse httpServletResponse;
    @Mock
    HttpSession session;
    private LogoutServlet logoutServlet = new LogoutServlet();

    @Test
    void doGet_shouldInvalidateSession_andRedirectToLoginPage() throws IOException {
        // GIVEN
        doReturn(session).when(httpServletRequest).getSession();
        // WHEN
        logoutServlet.doGet(httpServletRequest, httpServletResponse);
        // THEN
        verify(session).invalidate();
        verify(httpServletResponse).sendRedirect(LOGIN_PAGE_URI);
    }
}