package com.busbuddy.adminservice.route.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RouteRequest {

    @NotBlank(message = "Route name is required")
    private String routeName;

    private String origin;
    private String destination;

}
