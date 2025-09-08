package com.busbuddy.adminservice.assignment.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RouteAssignmentRequest {

    @NotBlank(message = "Route name is required")
    private String routeName;

    @NotBlank(message = "Bus registration number is required")
    private String busRegNumber;

    @NotBlank(message = "Driver license number is required")
    private String driverLicenseNumber;

    @NotBlank(message = "Location name is required")
    private String locationName;
}
