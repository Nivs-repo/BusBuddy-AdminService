package com.busbuddy.adminservice.route.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RouteResponse {
    private Long routeId;
    private String routeName;
    private String origin;
    private String destination;
}
