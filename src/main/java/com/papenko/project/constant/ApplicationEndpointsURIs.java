package com.papenko.project.constant;

public interface ApplicationEndpointsURIs {
    String DRIVER_PAGE_URI = "/driver";
    String DRIVER_JSP_PATH = "/WEB-INF/driver.jsp";
    String LOGIN_PAGE_URI = "/login";
    String LOGIN_JSP_PATH = "/WEB-INF/login.jsp";
    String LOGOUT_FORM_URI = "/logout";
    String GO_HOME_URI = "/go-home";

    interface AdminPage {
        String ADMIN_PAGE_URI = "/admin";
        String ADMIN_JSP_PATH = "/WEB-INF/admin.jsp";
        String ASSIGN_DRIVER_TO_BUS_FORM_URI = "/driver-to-bus";
        String VACATE_DRIVER_FORM_URI = "/vacate-driver";
        String ASSIGN_BUS_TO_ROUTE_FORM_URI = "/bus-to-route";
        String ADD_ROUTE_URI = "/add-route";
        String DELETE_ROUTE_URI = "/delete-route";
        String ADD_BUS_URI = "/add-bus";
        String DELETE_BUS_URI = "/delete-bus";
        String ADD_DRIVER_URI = "/add-driver";
        String DELETE_DRIVER_URI = "/delete-driver";
    }
}
