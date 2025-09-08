package com.busbuddy.adminservice.location.controller;

import com.busbuddy.adminservice.common.dto.ApiResponse;
import com.busbuddy.adminservice.common.util.ResponseBuilder;
import com.busbuddy.adminservice.location.model.Location;
import com.busbuddy.adminservice.location.service.LocationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/locations")
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Location>> createLocation(@Valid @RequestBody Location location) {
        Location savedLocation = locationService.createLocation(location);
        return ResponseBuilder.buildSuccessResponse(savedLocation);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    public ResponseEntity<ApiResponse<List<Location>>> getAllLocations() {
        List<Location> locations = locationService.getAllLocations();
        return ResponseBuilder.buildSuccessResponse(locations);
    }

    @GetMapping("/{locationId}")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    public ResponseEntity<ApiResponse<Location>> getLocationById(@PathVariable Long locationId) {
        Location location = locationService.getLocationById(locationId);
        return ResponseBuilder.buildSuccessResponse(location);
    }

    @PutMapping("/{locationId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Location>> updateLocation(
            @PathVariable Long locationId,
            @Valid @RequestBody Location locationDetails) {
        Location updatedLocation = locationService.updateLocation(locationId, locationDetails);
        return ResponseBuilder.buildSuccessResponse(updatedLocation);
    }

    @DeleteMapping("/{locationId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<String>> deleteLocation(@PathVariable Long locationId) {
        locationService.deleteLocation(locationId);
        return ResponseBuilder.buildSuccessResponse("Location deleted successfully");
    }
}
