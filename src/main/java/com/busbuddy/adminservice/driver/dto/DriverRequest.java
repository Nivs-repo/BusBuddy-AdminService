package com.busbuddy.adminservice.driver.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DriverRequest {

    @NotBlank(message = "Name is required")
    @Size(max = 100)
    private String driverName;

    @NotBlank(message = "Contact number is required")
    @Size(max = 20)
    private String contactNumber;

    @NotBlank(message = "License number is required")
    @Size(max = 50)
    private String licenseNumber;

    @Size(max = 200)
    private String address;

    @Size(max = 50)
    private String govId;

    private String isActive; // 'Y' or 'N'
}
