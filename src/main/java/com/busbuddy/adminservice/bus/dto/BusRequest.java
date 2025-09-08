package com.busbuddy.adminservice.bus.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class BusRequest {

    @NotBlank(message = "Registration number is required")
    @Size(max = 20, message = "Registration number must not exceed 20 characters")
    private String regNumber;

    @NotNull(message = "Capacity is required")
    private Integer capacity;

    private String status = "ACTIVE";
}
