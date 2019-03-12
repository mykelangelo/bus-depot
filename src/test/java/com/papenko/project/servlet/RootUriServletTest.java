package com.papenko.project.servlet;

import com.papenko.project.entity.AuthenticatedUserDetails;
import com.papenko.project.entity.UserType;
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
class RootUriServletTest {
    @Spy
    RootUriServlet rootUriServlet;
    @Mock
    HttpServletRequest httpServletRequest;
    @Mock
    HttpServletResponse httpServletResponse;
    @Mock
    HttpSession session;

    @Test
    void doGet_shouldRedirectToLoginPage_whenUserIsNotAuthorized() throws IOException {
        // GIVEN
        doReturn(session).when(httpServletRequest).getSession();
        doReturn(null).when(session).getAttribute("user_details");
        // WHEN
        rootUriServlet.doGet(httpServletRequest, httpServletResponse);
        // THEN
        verify(httpServletResponse).sendRedirect("/login");

    }

    @Test
    void doGet_shouldRedirectToAdminPage_whenAdminIsAuthorized() throws IOException {
        // GIVEN
        doReturn(session).when(httpServletRequest).getSession();
        doReturn(new AuthenticatedUserDetails(null, UserType.DEPOT_ADMIN))
                .when(session).getAttribute("user_details");
        // WHEN
        rootUriServlet.doGet(httpServletRequest, httpServletResponse);
        // THEN
        verify(httpServletResponse).sendRedirect("/admin");
    }

    @Test
    void doGet_shouldRedirectToDriverPage_whenDriverIsAuthorized() throws IOException {
        // GIVEN
        doReturn(session).when(httpServletRequest).getSession();
        doReturn(new AuthenticatedUserDetails(null, UserType.BUS_DRIVER))
                .when(session).getAttribute("user_details");
        // WHEN
        rootUriServlet.doGet(httpServletRequest, httpServletResponse);
        // THEN
        verify(httpServletResponse).sendRedirect("/driver");
    }
}