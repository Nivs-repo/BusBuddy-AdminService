package com.busbuddy.adminservice.routestop.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RouteStopResponse {
    private Long id;

    private String routeName;

    private String stopName;

    private Integer sequenceNumber;
}
