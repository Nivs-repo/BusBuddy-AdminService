package com.busbuddy.adminservice.routestop.service;

import com.busbuddy.adminservice.routestop.dto.RouteStopRequest;
import com.busbuddy.adminservice.routestop.dto.RouteStopResponse;

import java.util.List;

public interface RouteStopService {
    List<String> addRouteStop(RouteStopRequest request);
    List<String> getStopsForRoute(String routeName);
    void deleteRouteStop(Long id);
}
