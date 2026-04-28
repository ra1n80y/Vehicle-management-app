package com.godfrey.fleet.security;

public final class Permissions {

    public static final String VEHICLE_READ = "VEHICLE_READ";
    public static final String VEHICLE_CREATE = "VEHICLE_CREATE";
    public static final String VEHICLE_UPDATE = "VEHICLE_UPDATE";
    public static final String VEHICLE_DELETE = "VEHICLE_DELETE";
    public static final String VEHICLE_OWNERSHIP_OVERRIDE = "VEHICLE_OWNERSHIP_OVERRIDE";
    public static final String AUDIT_READ = "AUDIT_READ";

    public static final String USER_READ = "USER_READ";
    public static final String USER_CREATE = "USER_CREATE";
    public static final String USER_UPDATE = "USER_UPDATE";
    public static final String USER_DELETE = "USER_DELETE";

    public static final String ROLE_READ = "ROLE_READ";
    public static final String ROLE_MANAGE = "ROLE_MANAGE";

    private Permissions() {
    }
}