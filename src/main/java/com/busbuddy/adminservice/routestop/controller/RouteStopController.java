package com.busbuddy.adminservice.routestop.controller;

import com.busbuddy.adminservice.common.util.ResponseBuilder;
import com.busbuddy.adminservice.routestop.dto.RouteStopRequest;
import com.busbuddy.adminservice.routestop.service.RouteStopService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/route-stops")
@RequiredArgsConstructor
public class RouteStopController {

    private final RouteStopService routeStopService;

    /**
     * Add a stop to a route
     */
    @PostMapping
    public ResponseEntity<?> addRouteStop(@Valid @RequestBody RouteStopRequest request) {
        return ResponseBuilder.buildSuccessResponse(routeStopService.addRouteStop(request));
    }

    /**
     * Get all stops for a route (by route name)
     */
    @GetMapping("/{routeName}")
    public ResponseEntity<?> getStopsForRoute(@PathVariable String routeName) {
        return ResponseBuilder.buildSuccessResponse(routeStopService.getStopsForRoute(routeName));
    }

    /**
     * Delete a route-stop mapping by ID
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRouteStop(@PathVariable Long id) {
        routeStopService.deleteRouteStop(id);
        return ResponseBuilder.buildSuccessResponse("Route stop deleted successfully");
    }
}
