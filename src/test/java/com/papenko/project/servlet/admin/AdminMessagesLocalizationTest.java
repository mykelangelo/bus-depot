package com.papenko.project.servlet.admin;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.papenko.project.constant.SessionAttributesNames.CURRENT_LANGUAGE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AdminMessagesLocalizationTest {
    @Mock
    HttpServletRequest request;
    @Mock
    HttpSession session;
    private AdminMessagesLocalization messagesLocalization = new AdminMessagesLocalization();

    @Test
    void getStatusMessage_shouldReturnStatusMessageInEnglish_whenCurrentLanguageIsEnglish() {
        // GIVEN
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(CURRENT_LANGUAGE)).thenReturn("en");
        // WHEN
        String statusMessage = messagesLocalization.getMessage(request, "status_add-bus", "AI8901OE");
        // THEN
        assertEquals("You added new bus with serial number AI8901OE", statusMessage);
    }

    @Test
    void getStatusMessage_shouldReturnStatusMessageInUkrainian_whenCurrentLanguageIsUkrainian() {
        // GIVEN
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(CURRENT_LANGUAGE)).thenReturn("uk");
        // WHEN
        String statusMessage = messagesLocalization.getMessage(request, "status_try-assign-driver-to-bus", "driver@mail.com", "AO0422AA", "already.there@mail.com");
        // THEN
        assertEquals("Ви спробували назначити водія з емейлом driver@mail.com до автобуса з номерами AO0422AA, але цей автобус вже використовується водієм з емейлом already.there@mail.com", statusMessage);
    }
}