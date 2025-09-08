package com.busbuddy.adminservice.driver.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DriverResponse {
    private Long driverId;
    private String driverName;
    private String contactNumber;
    private String licenseNumber;
    private String address;
    private String govId;
    private String isActive; // 'Y' or 'N'
}
