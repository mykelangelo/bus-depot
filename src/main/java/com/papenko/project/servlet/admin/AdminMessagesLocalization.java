package com.papenko.project.servlet.admin;

import javax.servlet.http.HttpServletRequest;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

import static com.papenko.project.constant.SessionAttributesNames.CURRENT_LANGUAGE;

public class AdminMessagesLocalization {
    public String getMessage(HttpServletRequest request, String key, String... args) {
        String lang = (String) request.getSession().getAttribute(CURRENT_LANGUAGE);
        String pattern = ResourceBundle.getBundle("admin-texts", new Locale(lang)).getString(key);
        return MessageFormat.format(pattern, (Object[]) args);
    }
}
