package e2e.constant;

public interface ApplicationEndpointsURLs {
    String ROOT_URL = "http://localhost:8080";
    String DRIVER_PAGE_URL = "http://localhost:8080/driver";
    String LOGIN_PAGE_URL = "http://localhost:8080/login";
    String LOGOUT_FORM_URL = "http://localhost:8080/logout";
    String NO_SUCH_PAGE_EXISTS_URL = "http://localhost:8080/no-such-page-exists";

    interface AdminPage {
        String ADMIN_PAGE_URL = "http://localhost:8080/admin";
        String ASSIGN_DRIVER_TO_BUS_FORM_URL = "http://localhost:8080/driver-to-bus";
        String VACATE_DRIVER_FORM_URL = "http://localhost:8080/vacate-driver";
        String ASSIGN_BUS_TO_ROUTE_FORM_URL = "http://localhost:8080/bus-to-route";
    }
}
