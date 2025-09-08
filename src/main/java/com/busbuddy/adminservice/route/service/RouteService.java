package com.busbuddy.adminservice.route.service;

import com.busbuddy.adminservice.route.dto.RouteRequest;
import com.busbuddy.adminservice.route.dto.RouteResponse;

import java.util.List;

public interface RouteService {
    RouteResponse addRoute(RouteRequest request);
    RouteResponse updateRoute(Long routeId, RouteRequest request);
    void deleteRoute(Long routeId);
    RouteResponse getRouteById(Long routeId);
    List<RouteResponse> getAllRoutes();
}
