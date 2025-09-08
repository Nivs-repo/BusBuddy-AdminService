package com.busbuddy.adminservice.assignment.service;

import com.busbuddy.adminservice.assignment.dto.RouteAssignmentRequest;
import com.busbuddy.adminservice.assignment.dto.RouteAssignmentResponse;

import java.util.List;

public interface RouteAssignmentService {
    RouteAssignmentResponse assignBusDriver(RouteAssignmentRequest request);
    List<RouteAssignmentResponse> getAssignmentsByLocation(String locationName);
    RouteAssignmentResponse unassign(Long assignmentId);
}
