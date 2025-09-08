package com.busbuddy.adminservice.assignment.controller;

import com.busbuddy.adminservice.assignment.dto.RouteAssignmentRequest;
import com.busbuddy.adminservice.assignment.dto.RouteAssignmentResponse;
import com.busbuddy.adminservice.assignment.service.RouteAssignmentService;
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
@RequestMapping("/api/assignments")
@RequiredArgsConstructor
public class RouteAssignmentController {

    private final RouteAssignmentService assignmentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<RouteAssignmentResponse>> assign(
            @Valid @RequestBody RouteAssignmentRequest request) {
        RouteAssignmentResponse saved = assignmentService.assignBusDriver(request);
        return ResponseBuilder.buildSuccessResponse(saved);
    }

    @GetMapping("/location/{locationName}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<RouteAssignmentResponse>>> getByLocation(@PathVariable String locationName) {
        List<RouteAssignmentResponse> assignments = assignmentService.getAssignmentsByLocation(locationName);
        return ResponseBuilder.buildSuccessResponse(assignments);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<RouteAssignmentResponse>> unassign(@PathVariable Long id) {
        RouteAssignmentResponse unassigned = assignmentService.unassign(id);
        return ResponseBuilder.buildSuccessResponse(unassigned);
    }
}
