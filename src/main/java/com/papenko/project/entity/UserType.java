package com.papenko.project.entity;

import static com.papenko.project.constant.ApplicationEndpointsURIs.AdminPage.ADMIN_PAGE_URI;
import static com.papenko.project.constant.ApplicationEndpointsURIs.DRIVER_PAGE_URI;

public enum UserType {
    DEPOT_ADMIN(ADMIN_PAGE_URI),
    BUS_DRIVER(DRIVER_PAGE_URI);

    private final String pageUri;

    UserType(String pageUri) {
        this.pageUri = pageUri;
    }

    public String getPageUri() {
        return pageUri;
    }
}
