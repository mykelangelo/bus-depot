package com.papenko.project.servlet;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.papenko.project.constant.ApplicationEndpointsURIs.GO_HOME_URI;
import static com.papenko.project.constant.RequestParametersNames.SELECTED_LANGUAGE;
import static com.papenko.project.constant.SessionAttributesNames.CURRENT_LANGUAGE;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class LocalizationServletTest {
    @Mock
    HttpServletRequest httpServletRequest;
    @Mock
    HttpServletResponse httpServletResponse;
    @Mock
    HttpSession httpSession;
    private LocalizationServlet localizationServlet = new LocalizationServlet();

    @ParameterizedTest
    @ValueSource(strings = {"en", "uk", "ru"})
    void doPost_shouldSaveSelectedLanguageParameterToSession_andRedirectToGoHomeServlet(String chosenLanguage) throws Exception {
        // GIVEN
        doReturn(chosenLanguage).when(httpServletRequest).getParameter(SELECTED_LANGUAGE);
        doReturn(httpSession).when(httpServletRequest).getSession();
        // WHEN
        localizationServlet.doPost(httpServletRequest, httpServletResponse);
        // THEN
        verify(httpSession).setAttribute(CURRENT_LANGUAGE, chosenLanguage);
        verify(httpServletResponse).sendRedirect(GO_HOME_URI);
    }
}