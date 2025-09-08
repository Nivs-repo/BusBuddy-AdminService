package com.busbuddy.adminservice.bus.controller;

import com.busbuddy.adminservice.bus.dto.BusRequest;
import com.busbuddy.adminservice.bus.dto.BusResponse;
import com.busbuddy.adminservice.bus.service.BusService;
import com.busbuddy.adminservice.common.dto.ApiResponse;
import com.busbuddy.adminservice.common.util.ResponseBuilder;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/buses")
@RequiredArgsConstructor
public class BusController {

    private final BusService busService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<BusResponse>> addBus(@Valid @RequestBody BusRequest request) {
        BusResponse response = busService.addBus(request);
        return ResponseBuilder.buildSuccessResponse(response);
    }

    @PutMapping("/{busId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<BusResponse>> updateBus(
            @PathVariable("busId") Long busId,
            @Valid @RequestBody BusRequest request) {
        BusResponse response = busService.updateBus(busId, request);
        return ResponseBuilder.buildSuccessResponse(response);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<BusResponse>>> getAllBuses() {
        List<BusResponse> responseList = busService.getAllBuses();
        return ResponseBuilder.buildSuccessResponse(responseList);
    }

    @GetMapping("/{busId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<BusResponse>> getBusById(@PathVariable("busId") Long busId) {
        BusResponse response = busService.getBusById(busId);
        return ResponseBuilder.buildSuccessResponse(response);
    }

    @DeleteMapping("/{busId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<String>> deleteBus(@PathVariable("busId") Long busId) {
        busService.deleteBus(busId);
        return ResponseBuilder.buildSuccessResponse("Bus marked as INACTIVE successfully");
    }
}
