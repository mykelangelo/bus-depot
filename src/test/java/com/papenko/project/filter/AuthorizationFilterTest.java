package com.papenko.project.filter;

import com.papenko.project.entity.AuthenticatedUserDetails;
import com.papenko.project.entity.UserType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.MDC;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.papenko.project.constant.ApplicationEndpointsURIs.AdminPage.*;
import static com.papenko.project.constant.ApplicationEndpointsURIs.DRIVER_PAGE_URI;
import static com.papenko.project.constant.ApplicationEndpointsURIs.LOGIN_PAGE_URI;
import static com.papenko.project.constant.MDCKeys.ENDPOINT;
import static com.papenko.project.constant.MDCKeys.LOGGED_USER_EMAIL;
import static com.papenko.project.constant.SessionAttributesNames.USER_DETAILS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthorizationFilterTest {
    @Mock
    HttpServletRequest httpServletRequest;
    @Mock
    HttpServletResponse httpServletResponse;
    @Mock
    HttpSession session;
    @Mock
    FilterChain filterChain;
    private AuthorizationFilter authorizationFilter = new AuthorizationFilter();

    @BeforeEach
    @AfterEach
    void clearMDC() {
        MDC.clear();
    }

    @Test
    void doFilter_shouldSendForbiddenStatusError_whenAdminTriesToVisitDiversPageOrSubmitDriverForm() throws Exception {
        // GIVEN
        when(httpServletRequest.getRequestURI()).thenReturn(DRIVER_PAGE_URI);
        when(httpServletRequest.getSession()).thenReturn(session);
        var userDetails = new AuthenticatedUserDetails("flexo.22@bending.unit", UserType.DEPOT_ADMIN);
        when(session.getAttribute(USER_DETAILS)).thenReturn(userDetails);
        // WHEN
        authorizationFilter.doFilter(httpServletRequest, httpServletResponse, filterChain);
        // THEN
        verify(httpServletResponse).sendError(HttpServletResponse.SC_FORBIDDEN);
        verifyZeroInteractions(filterChain);
        assertEquals(DRIVER_PAGE_URI, MDC.get(ENDPOINT));
        assertEquals("flexo.22@bending.unit", MDC.get(LOGGED_USER_EMAIL));
    }


    @ParameterizedTest
    @ValueSource(strings = {LOGIN_PAGE_URI, ADMIN_PAGE_URI, ASSIGN_DRIVER_TO_BUS_FORM_URI, VACATE_DRIVER_FORM_URI, ASSIGN_BUS_TO_ROUTE_FORM_URI, ADD_ROUTE_URI, DELETE_ROUTE_URI, ADD_BUS_URI, DELETE_BUS_URI, ADD_DRIVER_URI, DELETE_DRIVER_URI})
    void doFilter_shouldNotSendError_whenAdminWantsToVisitLoginOrAdminPageOrSubmitAnyOfLoginOrAdminForms(String adminURI) throws Exception {
        // GIVEN
        when(httpServletRequest.getRequestURI()).thenReturn(adminURI);
        when(httpServletRequest.getSession()).thenReturn(session);
        var userDetails = new AuthenticatedUserDetails("rodríguez.22@bending.unit", UserType.DEPOT_ADMIN);
        when(session.getAttribute(USER_DETAILS)).thenReturn(userDetails);
        // WHEN
        authorizationFilter.doFilter(httpServletRequest, httpServletResponse, filterChain);
        // THEN
        verify(filterChain).doFilter(httpServletRequest, httpServletResponse);
        verifyZeroInteractions(httpServletResponse);
        assertEquals(adminURI, MDC.get(ENDPOINT));
        assertEquals("rodríguez.22@bending.unit", MDC.get(LOGGED_USER_EMAIL));
    }

    @ParameterizedTest
    @ValueSource(strings = {ADMIN_PAGE_URI, ASSIGN_DRIVER_TO_BUS_FORM_URI, VACATE_DRIVER_FORM_URI, ASSIGN_BUS_TO_ROUTE_FORM_URI, ADD_ROUTE_URI, DELETE_ROUTE_URI, ADD_BUS_URI, DELETE_BUS_URI, ADD_DRIVER_URI, DELETE_DRIVER_URI})
    void doFilter_shouldSendForbiddenStatusError_whenDriverTriesToVisitAdminPageOrSubmitAnyOfAdminForms(String adminURI) throws Exception {
        // GIVEN
        when(httpServletRequest.getRequestURI()).thenReturn(adminURI);
        when(httpServletRequest.getSession()).thenReturn(session);
        var userDetails = new AuthenticatedUserDetails("scruffy.janitor@office.staff", UserType.BUS_DRIVER);
        when(session.getAttribute(USER_DETAILS)).thenReturn(userDetails);
        // WHEN
        authorizationFilter.doFilter(httpServletRequest, httpServletResponse, filterChain);
        // THEN
        verify(httpServletResponse).sendError(HttpServletResponse.SC_FORBIDDEN);
        verifyZeroInteractions(filterChain);
        assertEquals(adminURI, MDC.get(ENDPOINT));
        assertEquals("scruffy.janitor@office.staff", MDC.get(LOGGED_USER_EMAIL));
    }


    @ParameterizedTest
    @ValueSource(strings = {DRIVER_PAGE_URI, LOGIN_PAGE_URI})
    void doFilter_shouldNotSendError_whenDriverWantsToVisitLoginOrDriverPageOrSubmitLoginOrDriverForm(String loginOrDriverURI) throws Exception {
        // GIVEN
        when(httpServletRequest.getRequestURI()).thenReturn(loginOrDriverURI);
        when(httpServletRequest.getSession()).thenReturn(session);
        var userDetails = new AuthenticatedUserDetails("amy@wong.mars", UserType.BUS_DRIVER);
        when(session.getAttribute(USER_DETAILS)).thenReturn(userDetails);
        // WHEN
        authorizationFilter.doFilter(httpServletRequest, httpServletResponse, filterChain);
        // THEN
        verify(filterChain).doFilter(httpServletRequest, httpServletResponse);
        verifyZeroInteractions(httpServletResponse);
        assertEquals(loginOrDriverURI, MDC.get(ENDPOINT));
        assertEquals("amy@wong.mars", MDC.get(LOGGED_USER_EMAIL));
    }

    @ParameterizedTest
    @ValueSource(strings = {DRIVER_PAGE_URI, ADMIN_PAGE_URI, ASSIGN_DRIVER_TO_BUS_FORM_URI, VACATE_DRIVER_FORM_URI, ASSIGN_BUS_TO_ROUTE_FORM_URI, ADD_ROUTE_URI, DELETE_ROUTE_URI, ADD_BUS_URI, DELETE_BUS_URI, ADD_DRIVER_URI, DELETE_DRIVER_URI})
    void doFilter_shouldSendUnauthorizedStatusError_whenUnauthorizedUserTriesToViewDriverOrAdminPageOrSubmitsAnyOfDriverOrAdminForms(String driverOrAdminURI) throws Exception {
        // GIVEN
        when(httpServletRequest.getRequestURI()).thenReturn(driverOrAdminURI);
        when(httpServletRequest.getSession()).thenReturn(session);
        when(session.getAttribute(USER_DETAILS)).thenReturn(null);
        // WHEN
        authorizationFilter.doFilter(httpServletRequest, httpServletResponse, filterChain);
        // THEN
        verify(httpServletResponse).sendError(HttpServletResponse.SC_UNAUTHORIZED);
        verifyZeroInteractions(filterChain);
        assertEquals(driverOrAdminURI, MDC.get(ENDPOINT));
        assertNull(MDC.get(LOGGED_USER_EMAIL));
    }


    @Test
    void doFilter_shouldNotSendError_whenUnauthorizedUserWantsToVisitLoginPageOrSubmitLoginForm() throws Exception {
        // GIVEN
        when(httpServletRequest.getRequestURI()).thenReturn(LOGIN_PAGE_URI);
        when(httpServletRequest.getSession()).thenReturn(session);
        when(session.getAttribute(USER_DETAILS)).thenReturn(null);
        // WHEN
        authorizationFilter.doFilter(httpServletRequest, httpServletResponse, filterChain);
        // THEN
        verify(filterChain).doFilter(httpServletRequest, httpServletResponse);
        verifyZeroInteractions(httpServletResponse);
        assertEquals(LOGIN_PAGE_URI, MDC.get(ENDPOINT));
        assertNull(MDC.get(LOGGED_USER_EMAIL));
    }
}