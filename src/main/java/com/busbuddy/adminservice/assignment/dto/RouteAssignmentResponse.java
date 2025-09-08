package com.busbuddy.adminservice.assignment.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RouteAssignmentResponse {
    private Long assignmentId;

    private String routeName;
    private String busRegNumber;
    private String driverName;
    private String driverLicenseNumber;
    private String locationName;

    private LocalDate assignmentDate; 
    private String isActive;
}
