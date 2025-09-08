package com.busbuddy.adminservice.driver.service;

import com.busbuddy.adminservice.driver.dto.DriverRequest;
import com.busbuddy.adminservice.driver.dto.DriverResponse;

import java.util.List;

public interface DriverService {

    DriverResponse createDriver(DriverRequest request);

    List<DriverResponse> getAllDrivers();

    DriverResponse getDriverById(Long driverId);

    DriverResponse updateDriver(Long driverId, DriverRequest request);

    void deleteDriver(Long driverId);
}
