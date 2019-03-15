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

import static com.papenko.project.constant.ApplicationEndpointsURIs.AdminPage.ADMIN_PAGE_URI;
import static com.papenko.project.constant.ApplicationEndpointsURIs.DRIVER_PAGE_URI;
import static com.papenko.project.constant.ApplicationEndpointsURIs.LOGIN_PAGE_URI;
import static com.papenko.project.constant.SessionAttributesNames.USER_DETAILS;
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
        doReturn(null).when(session).getAttribute(USER_DETAILS);
        // WHEN
        rootUriServlet.doGet(httpServletRequest, httpServletResponse);
        // THEN
        verify(httpServletResponse).sendRedirect(LOGIN_PAGE_URI);

    }

    @Test
    void doGet_shouldRedirectToAdminPage_whenAdminIsAuthorized() throws IOException {
        // GIVEN
        doReturn(session).when(httpServletRequest).getSession();
        doReturn(new AuthenticatedUserDetails(null, UserType.DEPOT_ADMIN))
                .when(session).getAttribute(USER_DETAILS);
        // WHEN
        rootUriServlet.doGet(httpServletRequest, httpServletResponse);
        // THEN
        verify(httpServletResponse).sendRedirect(ADMIN_PAGE_URI);
    }

    @Test
    void doGet_shouldRedirectToDriverPage_whenDriverIsAuthorized() throws IOException {
        // GIVEN
        doReturn(session).when(httpServletRequest).getSession();
        doReturn(new AuthenticatedUserDetails(null, UserType.BUS_DRIVER))
                .when(session).getAttribute(USER_DETAILS);
        // WHEN
        rootUriServlet.doGet(httpServletRequest, httpServletResponse);
        // THEN
        verify(httpServletResponse).sendRedirect(DRIVER_PAGE_URI);
    }
}