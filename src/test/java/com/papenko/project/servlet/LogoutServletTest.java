package com.papenko.project.servlet;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class LogoutServletTest {
    @Spy
    LogoutServlet logoutServlet;
    @Mock
    HttpServletRequest httpServletRequest;
    @Mock
    HttpServletResponse httpServletResponse;
    @Mock
    HttpSession session;

    @Test
    void doGet_shouldInvalidateSession_andRedirectToLoginPage() throws IOException {
        // GIVEN
        doReturn(session).when(httpServletRequest).getSession();
        // WHEN
        logoutServlet.doGet(httpServletRequest, httpServletResponse);
        // THEN
        verify(session).invalidate();
        verify(httpServletResponse).sendRedirect("/login");
    }
}