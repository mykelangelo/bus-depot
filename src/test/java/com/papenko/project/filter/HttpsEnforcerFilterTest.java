package com.papenko.project.filter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.papenko.project.constant.ApplicationEndpointsURIs.LOGIN_PAGE_URI;
import static com.papenko.project.filter.HttpsEnforcerFilter.X_FORWARDED_PROTO;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HttpsEnforcerFilterTest {
    @Mock
    HttpServletRequest httpServletRequest;
    @Mock
    HttpServletResponse httpServletResponse;
    @Mock
    FilterChain filterChain;
    private HttpsEnforcerFilter httpsEnforcerFilter = new HttpsEnforcerFilter();

    @Test
    void doFilter_shouldNotRedirect_whenAppIsRunningLocally() throws Exception {
        // GIVEN
        // WHEN
        httpsEnforcerFilter.doFilter(httpServletRequest, httpServletResponse, filterChain);
        // THEN
        verify(filterChain).doFilter(httpServletRequest, httpServletResponse);
    }

    @Test
    void doFilter_shouldNotRedirect_whenAppIsRunningInProduction_andWithHttps() throws Exception {
        // GIVEN
        when(httpServletRequest.getHeader(X_FORWARDED_PROTO)).thenReturn("https://the-app.com");
        // WHEN
        httpsEnforcerFilter.doFilter(httpServletRequest, httpServletResponse, filterChain);
        // THEN
        verify(filterChain).doFilter(httpServletRequest, httpServletResponse);
    }

    @Test
    void doFilter_shouldRedirect_whenAppIsRunningInProduction_andHttp() throws Exception {
        // GIVEN
        when(httpServletRequest.getHeader(X_FORWARDED_PROTO)).thenReturn("http://the-app.com");
        when(httpServletRequest.getServerName()).thenReturn("the-app.com");
        when(httpServletRequest.getRequestURI()).thenReturn(LOGIN_PAGE_URI);
        // WHEN
        httpsEnforcerFilter.doFilter(httpServletRequest, httpServletResponse, filterChain);
        // THEN
        verify(httpServletResponse).sendRedirect("https://the-app.com/login");
        verifyZeroInteractions(filterChain);
    }
}