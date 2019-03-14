package com.papenko.project.constant;

public interface ApplicationEndpointsURI {
    String DRIVER_PAGE_URI = "/driver";
    String DRIVER_JSP_PATH = "/WEB-INF/driver.jsp";
    String LOGIN_PAGE_URI = "/login";
    String LOGIN_JSP_PATH = "/WEB-INF/login.jsp";
    String LOGOUT_FORM_URI = "/logout";
    String ROOT_URI = "/";

    interface AdminPage {
        String ADMIN_PAGE_URI = "/admin";
        String ADMIN_JSP_PATH = "/WEB-INF/admin.jsp";
        String ASSIGN_DRIVER_TO_BUS_FORM_URI = "/driver-to-bus";
        String VACATE_DRIVER_FORM_URI = "/vacate-driver";
        String ASSIGN_BUS_TO_ROUTE_FORM_URI = "/bus-to-route";

    }
}
