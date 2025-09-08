package com.busbuddy.adminservice.routestop.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RouteStopRequest {

    @NotBlank(message = "Route name is required")
    private String routeName;

    @NotBlank(message = "Stop name is required")
    private String stopName;

    private Integer sequenceNumber;
}
