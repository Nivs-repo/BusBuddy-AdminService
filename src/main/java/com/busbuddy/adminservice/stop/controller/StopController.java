package com.busbuddy.adminservice.stop.controller;

import com.busbuddy.adminservice.common.dto.ApiResponse;
import com.busbuddy.adminservice.common.util.ResponseBuilder;
import com.busbuddy.adminservice.stop.dto.StopRequest;
import com.busbuddy.adminservice.stop.dto.StopResponse;
import com.busbuddy.adminservice.stop.service.StopService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stops")
@RequiredArgsConstructor
public class StopController {

    private final StopService stopService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<StopResponse>> createStop(@Valid @RequestBody StopRequest request) {
        StopResponse savedStop = stopService.addStop(request);
        return ResponseBuilder.buildSuccessResponse(savedStop);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<StopResponse>>> getAllStops() {
        List<StopResponse> stops = stopService.getAllStops();
        return ResponseBuilder.buildSuccessResponse(stops);
    }

    @GetMapping("/{stopId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<StopResponse>> getStopById(@PathVariable Long stopId) {
        StopResponse stop = stopService.getStopById(stopId);
        return ResponseBuilder.buildSuccessResponse(stop);
    }

    @PutMapping("/{stopId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<StopResponse>> updateStop(
            @PathVariable Long stopId,
            @Valid @RequestBody StopRequest request) {
        StopResponse updatedStop = stopService.updateStop(stopId, request);
        return ResponseBuilder.buildSuccessResponse(updatedStop);
    }

    @DeleteMapping("/{stopId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<String>> deleteStop(@PathVariable Long stopId) {
        stopService.deleteStop(stopId);
        return ResponseBuilder.buildSuccessResponse("Stop deleted successfully");
    }
}
