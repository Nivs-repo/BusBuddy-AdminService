package com.busbuddy.adminservice.stop.service;

import com.busbuddy.adminservice.stop.dto.StopRequest;
import com.busbuddy.adminservice.stop.dto.StopResponse;

import java.util.List;

public interface StopService {
    StopResponse addStop(StopRequest request);
    StopResponse updateStop(Long stopId, StopRequest request);
    void deleteStop(Long stopId);
    StopResponse getStopById(Long stopId);
    List<StopResponse> getAllStops();
}
