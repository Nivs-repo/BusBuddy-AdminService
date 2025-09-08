package com.busbuddy.adminservice.common.constants;

public class ErrorCodes {

    // ---------- Generic Errors ----------
    public static final String VALIDATION_ERROR = "VALIDATION_ERROR";
    public static final String INTERNAL_SERVER_ERROR = "INTERNAL_SERVER_ERROR";
    public static final String TECHNICAL_ERROR = "TECHNICAL_ERROR";

    // ---------- Location Feature Errors ----------
    public static final String LOCATION_NOT_FOUND = "LOCATION_NOT_FOUND";
    public static final String LOCATION_ALREADY_EXISTS = "LOCATION_ALREADY_EXISTS";

    // ---------- Bus Feature Errors ----------
    public static final String BUS_NOT_FOUND = "BUS_NOT_FOUND";
    public static final String DUPLICATE_BUS = "DUPLICATE_BUS";

    // ---------- Future Features (Drivers, Stops, Routes) ----------
    public static final String DRIVER_NOT_FOUND = "DRIVER_NOT_FOUND";
    public static final String DRIVER_ALREADY_EXISTS = "DRIVER_ALREADY_EXISTS";

    public static final String DRIVER_DUPLICATE_PHONE = "DRIVER_DUPLICATE_PHONE";
    public static final String DRIVER_DUPLICATE_LICENSE = "DRIVER_DUPLICATE_LICENSE";
    public static final String INVALID_STATUS = "INVALID_STATUS";

    public static final String STOP_DUPLICATE_NAME = "STOP_DUPLICATE_NAME";
    public static final String STOP_DUPLICATE_COORDINATES = "STOP_DUPLICATE_COORDINATES";
    public static final String STOP_INVALID_LAT_LNG = "STOP_INVALID_LAT_LNG";
    public static final String DUPLICATE_ASSIGNMENT = "DUPLICATE_ASSIGNMENT";
    public static final String ASSIGNMENT_NOT_FOUND = "ASSIGNMENT_NOT_FOUND";

    // ====== Route Errors ======
    public static final String DUPLICATE_ROUTE = "DUPLICATE_ROUTE";
    public static final String ROUTE_CREATION_FAILED = "ROUTE_CREATION_FAILED";
    public static final String ROUTE_UPDATE_FAILED = "ROUTE_UPDATE_FAILED";
    public static final String ROUTE_DELETE_FAILED = "ROUTE_DELETE_FAILED";
    public static final String ROUTE_FETCH_FAILED = "ROUTE_FETCH_FAILED";

    // ====== Stop Errors ======
    public static final String DUPLICATE_STOP = "DUPLICATE_STOP";
    public static final String STOP_CREATION_FAILED = "STOP_CREATION_FAILED";
    public static final String STOP_UPDATE_FAILED = "STOP_UPDATE_FAILED";
    public static final String STOP_DELETE_FAILED = "STOP_DELETE_FAILED";
    public static final String STOP_FETCH_FAILED = "STOP_FETCH_FAILED";

      // ✅ Generic
    public static final String NOT_FOUND = "NOT_FOUND";
    public static final String DUPLICATE_ENTRY = "DUPLICATE_ENTRY";

    // ✅ Route
    public static final String ROUTE_NOT_FOUND = "ROUTE_NOT_FOUND";
    public static final String ROUTE_ALREADY_EXISTS = "ROUTE_ALREADY_EXISTS";

    // ✅ Stop
    public static final String STOP_NOT_FOUND = "STOP_NOT_FOUND";
    public static final String STOP_ALREADY_EXISTS = "STOP_ALREADY_EXISTS";

    // ✅ RouteStop (mapping table)
    public static final String ROUTE_STOP_NOT_FOUND = "ROUTE_STOP_NOT_FOUND";
    public static final String ROUTE_STOP_DUPLICATE = "ROUTE_STOP_DUPLICATE";


    private ErrorCodes() {
        // Prevent instantiation
    }
}

