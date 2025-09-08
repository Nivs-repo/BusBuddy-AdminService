package com.busbuddy.adminservice.bus.service;

import com.busbuddy.adminservice.bus.dto.BusRequest;
import com.busbuddy.adminservice.bus.dto.BusResponse;
import java.util.List;

public interface BusService {

    BusResponse addBus(BusRequest request);

    BusResponse updateBus(Long busId, BusRequest request);

    List<BusResponse> getAllBuses();

    BusResponse getBusById(Long busId);

    void deleteBus(Long busId);
}
