package com.tcs.busbuddy.location.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.tcs.busbuddy.common.util.ResponseBuilder;
import com.tcs.busbuddy.common.dto.ApiResponse;
import com.tcs.busbuddy.common.exception.BusinessException;
import com.tcs.busbuddy.location.model.Location;
import com.tcs.busbuddy.location.service.LocationService;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/locations")
public class LocationController {

    @Autowired
    private LocationService locationService;

    //  Create Location
    @PostMapping
    public ResponseEntity<ApiResponse<Location>> createLocation(@Valid @RequestBody Location location, BindingResult result) {
        if (result.hasErrors()) {
            throw new BusinessException("VALIDATION_ERROR", result.getAllErrors().get(0).getDefaultMessage());
        }
        Location savedLocation = locationService.createLocation(location);
        return ResponseBuilder.buildSuccessResponse(savedLocation);
    }

    //  Get All Locations
    @GetMapping
    public ResponseEntity<ApiResponse<List<Location>>> getAllLocations() {
        List<Location> locations = locationService.getAllLocations();
        return ResponseBuilder.buildSuccessResponse(locations);
    }

    //  Get Location by ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Location>> getLocationById(@PathVariable Long id) {
        Location location = locationService.getLocationById(id)
                .orElseThrow(() -> new BusinessException("LOCATION_NOT_FOUND", "Location with ID " + id + " not found"));
        return ResponseBuilder.buildSuccessResponse(location);
    }

    //  Update Location
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Location>> updateLocation(@PathVariable Long id, @Valid @RequestBody Location locationDetails, BindingResult result) {
        if (result.hasErrors()) {
            throw new BusinessException("VALIDATION_ERROR", result.getAllErrors().get(0).getDefaultMessage());
        }
        Location updatedLocation = locationService.updateLocation(id, locationDetails);
        return ResponseBuilder.buildSuccessResponse(updatedLocation);
    }

    //  Delete Location
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteLocation(@PathVariable Long id) {
        locationService.deleteLocation(id);
        return ResponseBuilder.buildSuccessResponse("Location deleted successfully");
    }
}
