package com.busbuddy.adminservice.route.controller;

import com.busbuddy.adminservice.common.dto.ApiResponse;
import com.busbuddy.adminservice.common.util.ResponseBuilder;
import com.busbuddy.adminservice.route.dto.RouteRequest;
import com.busbuddy.adminservice.route.dto.RouteResponse;
import com.busbuddy.adminservice.route.service.RouteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/routes")
@RequiredArgsConstructor
public class RouteController {

    private final RouteService routeService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<RouteResponse>> createRoute(
            @Valid @RequestBody RouteRequest request) {
        RouteResponse savedRoute = routeService.addRoute(request);
        return ResponseBuilder.buildSuccessResponse(savedRoute);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<RouteResponse>>> getAllRoutes() {
        List<RouteResponse> routes = routeService.getAllRoutes();
        return ResponseBuilder.buildSuccessResponse(routes);
    }

    
    @GetMapping("/{routeId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<RouteResponse>> getRouteById(@PathVariable Long routeId) {
        RouteResponse route = routeService.getRouteById(routeId);
        return ResponseBuilder.buildSuccessResponse(route);
    }

    
    @PutMapping("/{routeId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<RouteResponse>> updateRoute(
            @PathVariable Long routeId,
            @Valid @RequestBody RouteRequest request) {
        RouteResponse updatedRoute = routeService.updateRoute(routeId, request);
        return ResponseBuilder.buildSuccessResponse(updatedRoute);
    }

    
    @DeleteMapping("/{routeId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<String>> deleteRoute(@PathVariable Long routeId) {
        routeService.deleteRoute(routeId);
        return ResponseBuilder.buildSuccessResponse("Route deleted successfully");
    }
}
