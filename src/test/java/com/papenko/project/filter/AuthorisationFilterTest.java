package com.papenko.project.filter;

import com.papenko.project.entity.AuthenticatedUserDetails;
import com.papenko.project.entity.UserType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthorisationFilterTest {
    @Mock
    HttpServletRequest httpServletRequest;
    @Mock
    HttpServletResponse httpServletResponse;
    @Mock
    HttpSession session;
    @Mock
    FilterChain filterChain;
    private AuthorisationFilter authorisationFilter = new AuthorisationFilter();

    @Test
    void doFilter_shouldSendForbiddenStatusError_whenAdminTriesToVisitDiversPageOrSubmitDriverForm() throws Exception {
        // GIVEN
        when(httpServletRequest.getRequestURI()).thenReturn("/driver");
        when(httpServletRequest.getSession()).thenReturn(session);
        var userDetails = new AuthenticatedUserDetails("flexo.22@bending.unit", UserType.DEPOT_ADMIN);
        when(session.getAttribute("user_details")).thenReturn(userDetails);
        // WHEN
        authorisationFilter.doFilter(httpServletRequest, httpServletResponse, filterChain);
        // THEN
        verify(httpServletResponse).sendError(HttpServletResponse.SC_FORBIDDEN);
        verifyZeroInteractions(filterChain);
    }


    @ParameterizedTest
    @ValueSource(strings = {"/login", "/admin", "/driver-to-bus", "/vacate-driver", "/bus-to-route"})
    void doFilter_shouldNotSendError_whenAdminWantsToVisitLoginOrAdminPageOrSubmitAnyOfLoginOrAdminForms(String adminURI) throws Exception {
        // GIVEN
        when(httpServletRequest.getRequestURI()).thenReturn(adminURI);
        when(httpServletRequest.getSession()).thenReturn(session);
        var userDetails = new AuthenticatedUserDetails("rodríguez.22@bending.unit", UserType.DEPOT_ADMIN);
        when(session.getAttribute("user_details")).thenReturn(userDetails);
        // WHEN
        authorisationFilter.doFilter(httpServletRequest, httpServletResponse, filterChain);
        // THEN
        verify(filterChain).doFilter(httpServletRequest, httpServletResponse);
        verifyZeroInteractions(httpServletResponse);
    }

    @ParameterizedTest
    @ValueSource(strings = {"/admin", "/driver-to-bus", "/vacate-driver", "/bus-to-route"})
    void doFilter_shouldSendForbiddenStatusError_whenDriverTriesToVisitAdminPageOrSubmitAnyOfAdminForms(String adminURI) throws Exception {
        // GIVEN
        when(httpServletRequest.getRequestURI()).thenReturn(adminURI);
        when(httpServletRequest.getSession()).thenReturn(session);
        var userDetails = new AuthenticatedUserDetails("scruffy.janitor@office.staff", UserType.BUS_DRIVER);
        when(session.getAttribute("user_details")).thenReturn(userDetails);
        // WHEN
        authorisationFilter.doFilter(httpServletRequest, httpServletResponse, filterChain);
        // THEN
        verify(httpServletResponse).sendError(HttpServletResponse.SC_FORBIDDEN);
        verifyZeroInteractions(filterChain);
    }


    @ParameterizedTest
    @ValueSource(strings = {"/driver", "/login"})
    void doFilter_shouldNotSendError_whenDriverWantsToVisitLoginOrDriverPageOrSubmitLoginOrDriverForm(String loginOrDriverURI) throws Exception {
        // GIVEN
        when(httpServletRequest.getRequestURI()).thenReturn(loginOrDriverURI);
        when(httpServletRequest.getSession()).thenReturn(session);
        var userDetails = new AuthenticatedUserDetails("amy@wong.mars", UserType.BUS_DRIVER);
        when(session.getAttribute("user_details")).thenReturn(userDetails);
        // WHEN
        authorisationFilter.doFilter(httpServletRequest, httpServletResponse, filterChain);
        // THEN
        verify(filterChain).doFilter(httpServletRequest, httpServletResponse);
        verifyZeroInteractions(httpServletResponse);
    }

    @ParameterizedTest
    @ValueSource(strings = {"/driver", "/admin", "/driver-to-bus", "/vacate-driver", "/bus-to-route"})
    void doFilter_shouldSendUnauthorizedStatusError_whenUnauthorizedUserTriesToViewDriverOrAdminPageOrSubmitsAnyOfDriverOrAdminForms(String driverOrAdminURI) throws Exception {
        // GIVEN
        when(httpServletRequest.getRequestURI()).thenReturn(driverOrAdminURI);
        when(httpServletRequest.getSession()).thenReturn(session);
        when(session.getAttribute("user_details")).thenReturn(null);
        // WHEN
        authorisationFilter.doFilter(httpServletRequest, httpServletResponse, filterChain);
        // THEN
        verify(httpServletResponse).sendError(HttpServletResponse.SC_UNAUTHORIZED);
        verifyZeroInteractions(filterChain);
    }


    @Test
    void doFilter_shouldNotSendError_whenUnauthorizedUserWantsToVisitLoginPageOrSubmitLoginForm() throws Exception {
        // GIVEN
        when(httpServletRequest.getRequestURI()).thenReturn("/login");
        when(httpServletRequest.getSession()).thenReturn(session);
        when(session.getAttribute("user_details")).thenReturn(null);
        // WHEN
        authorisationFilter.doFilter(httpServletRequest, httpServletResponse, filterChain);
        // THEN
        verify(filterChain).doFilter(httpServletRequest, httpServletResponse);
        verifyZeroInteractions(httpServletResponse);
    }
}