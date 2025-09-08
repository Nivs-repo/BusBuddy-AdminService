package com.busbuddy.adminservice.stop.service.impl;

import com.busbuddy.adminservice.common.constants.ErrorCodes;
import com.busbuddy.adminservice.common.exception.BusinessException;
import com.busbuddy.adminservice.common.exception.TechnicalException;
import com.busbuddy.adminservice.stop.dto.StopRequest;
import com.busbuddy.adminservice.stop.dto.StopResponse;
import com.busbuddy.adminservice.stop.model.Stop;
import com.busbuddy.adminservice.stop.repository.StopRepository;
import com.busbuddy.adminservice.stop.service.StopService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class StopServiceImpl implements StopService {

    private final StopRepository stopRepository;

    private StopResponse toResponse(Stop stop) {
        return StopResponse.builder()
                .stopId(stop.getStopId())
                .stopName(stop.getStopName())
                .gpsLocation(stop.getGpsLocation())
                .landmark(stop.getLandmark())
                .imageUrl(stop.getImageUrl())
                .build();
    }

    @Override
    public StopResponse addStop(StopRequest request) {
        try {
            String stopName = request.getStopName().trim();

            if (stopRepository.existsByStopNameIgnoreCase(stopName)) {
                throw new BusinessException(ErrorCodes.DUPLICATE_STOP, "Stop with this name already exists");
            }

            Stop stop = Stop.builder()
                    .stopName(stopName)
                    .gpsLocation(request.getGpsLocation())
                    .landmark(request.getLandmark())
                    .imageUrl(request.getImageUrl())
                    .build();

            Stop saved = stopRepository.save(stop);
            return toResponse(saved);

        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error while adding stop", e);
            throw new TechnicalException(ErrorCodes.STOP_CREATION_FAILED, "Unexpected error occurred while creating stop");
        }
    }

    @Override
    public StopResponse updateStop(Long stopId, StopRequest request) {
        try {
            Stop existing = stopRepository.findById(stopId)
                    .orElseThrow(() -> new BusinessException(ErrorCodes.STOP_NOT_FOUND, "Stop not found"));

            String newName = request.getStopName().trim();
            if (!existing.getStopName().equalsIgnoreCase(newName)
                    && stopRepository.existsByStopNameIgnoreCase(newName)) {
                throw new BusinessException(ErrorCodes.DUPLICATE_STOP, "Another stop with this name already exists");
            }

            existing.setStopName(newName);
            existing.setGpsLocation(request.getGpsLocation());
            existing.setLandmark(request.getLandmark());
            existing.setImageUrl(request.getImageUrl());

            Stop updated = stopRepository.save(existing);
            return toResponse(updated);

        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error while updating stop", e);
            throw new TechnicalException(ErrorCodes.STOP_UPDATE_FAILED, "Unexpected error occurred while updating stop");
        }
    }

    @Override
    public void deleteStop(Long stopId) {
        try {
            Stop existing = stopRepository.findById(stopId)
                    .orElseThrow(() -> new BusinessException(ErrorCodes.STOP_NOT_FOUND, "Stop not found"));
            stopRepository.delete(existing);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error while deleting stop", e);
            throw new TechnicalException(ErrorCodes.STOP_DELETE_FAILED, "Unexpected error occurred while deleting stop");
        }
    }

    @Override
    public StopResponse getStopById(Long stopId) {
        try {
            Stop stop = stopRepository.findById(stopId)
                    .orElseThrow(() -> new BusinessException(ErrorCodes.STOP_NOT_FOUND, "Stop not found"));
            return toResponse(stop);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error while fetching stop by id", e);
            throw new TechnicalException(ErrorCodes.STOP_FETCH_FAILED, "Unexpected error occurred while fetching stop");
        }
    }

    @Override
    public List<StopResponse> getAllStops() {
        try {
            return stopRepository.findAll()
                    .stream()
                    .map(this::toResponse)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error while fetching all stops", e);
            throw new TechnicalException(ErrorCodes.STOP_FETCH_FAILED, "Unexpected error occurred while fetching stops");
        }
    }
}
