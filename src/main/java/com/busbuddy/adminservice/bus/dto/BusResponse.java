package com.busbuddy.adminservice.bus.dto;

import lombok.Data;

@Data
public class BusResponse {

    private Long busId;
    private String regNumber;
    private Integer capacity;
    private String status;
}
