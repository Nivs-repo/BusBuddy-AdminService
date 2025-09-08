package com.busbuddy.adminservice.stop.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StopRequest {

    @NotBlank(message = "stopName is required")
    @Size(max = 100)
    private String stopName;

    // Format: "lat,lng"
    @Size(max = 100)
    private String gpsLocation;

    @Size(max = 100)
    private String landmark;

    @Size(max = 200)
    private String imageUrl;
}
