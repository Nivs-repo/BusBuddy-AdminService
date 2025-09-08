package com.busbuddy.adminservice.driver.controller;

import com.busbuddy.adminservice.common.dto.ApiResponse;
import com.busbuddy.adminservice.common.util.ResponseBuilder;
import com.busbuddy.adminservice.driver.dto.DriverRequest;
import com.busbuddy.adminservice.driver.dto.DriverResponse;
import com.busbuddy.adminservice.driver.service.DriverService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/drivers")
@RequiredArgsConstructor
public class DriverController {

    private final DriverService driverService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<DriverResponse>> createDriver(@Valid @RequestBody DriverRequest request) {
        DriverResponse saved = driverService.createDriver(request);
        return ResponseBuilder.buildSuccessResponse(saved);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<DriverResponse>>> getAllDrivers() {
        List<DriverResponse> drivers = driverService.getAllDrivers();
        return ResponseBuilder.buildSuccessResponse(drivers);
    }

    @GetMapping("/{driverId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<DriverResponse>> getDriverById(@PathVariable Long driverId) {
        DriverResponse driver = driverService.getDriverById(driverId);
        return ResponseBuilder.buildSuccessResponse(driver);
    }

    @PutMapping("/{driverId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<DriverResponse>> updateDriver(@PathVariable Long driverId,
                                                                    @Valid @RequestBody DriverRequest request) {
        DriverResponse updated = driverService.updateDriver(driverId, request);
        return ResponseBuilder.buildSuccessResponse(updated);
    }

    @DeleteMapping("/{driverId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<String>> deleteDriver(@PathVariable Long driverId) {
        driverService.deleteDriver(driverId);
        return ResponseBuilder.buildSuccessResponse("Driver deleted successfully");
    }
}
