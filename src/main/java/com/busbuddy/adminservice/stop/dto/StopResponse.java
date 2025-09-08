package com.busbuddy.adminservice.stop.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StopResponse {
    private Long stopId;
    private String stopName;
    private String gpsLocation;
    private String landmark;
    private String imageUrl;
}
