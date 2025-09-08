package com.busbuddy.adminservice.bus.service.impl;

import com.busbuddy.adminservice.bus.dto.BusRequest;
import com.busbuddy.adminservice.bus.dto.BusResponse;
import com.busbuddy.adminservice.bus.model.Bus;
import com.busbuddy.adminservice.bus.repository.BusRepository;
import com.busbuddy.adminservice.bus.service.BusService;
import com.busbuddy.adminservice.common.exception.BusinessException;
import com.busbuddy.adminservice.common.exception.TechnicalException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BusServiceImpl implements BusService {

    private final BusRepository busRepository;

    @Override
    public BusResponse addBus(BusRequest request) {
        try {
            if (busRepository.existsByRegNumber(request.getRegNumber())) {
                throw new BusinessException("DUPLICATE_BUS", "Bus with this registration number already exists");
            }

            Bus bus = new Bus();
            BeanUtils.copyProperties(request, bus);

            Bus savedBus = busRepository.save(bus);
            return convertToResponse(savedBus);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new TechnicalException("TECHNICAL_ERROR", "Failed to add bus due to system error");
        }
    }

    @Override
    public BusResponse updateBus(Long busId, BusRequest request) {
        try {
            Bus bus = busRepository.findById(busId)
                    .orElseThrow(() -> new BusinessException("BUS_NOT_FOUND", "Bus not found with ID: " + busId));

            if (!bus.getRegNumber().equals(request.getRegNumber())
                    && busRepository.existsByRegNumber(request.getRegNumber())) {
                throw new BusinessException("DUPLICATE_BUS", "Bus with this registration number already exists");
            }

            bus.setRegNumber(request.getRegNumber());
            bus.setCapacity(request.getCapacity());
            bus.setStatus(request.getStatus());

            Bus updatedBus = busRepository.save(bus);
            return convertToResponse(updatedBus);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new TechnicalException("TECHNICAL_ERROR", "Failed to update bus due to system error");
        }
    }

    @Override
    public List<BusResponse> getAllBuses() {
        try {
            List<Bus> buses = busRepository.findAll();
            List<BusResponse> responseList = new ArrayList<>();
            for (Bus bus : buses) {
                responseList.add(convertToResponse(bus));
            }
            return responseList;
        } catch (Exception e) {
            throw new TechnicalException("TECHNICAL_ERROR", "Failed to fetch bus list");
        }
    }

    @Override
    public BusResponse getBusById(Long busId) {
        Bus bus = busRepository.findById(busId)
                .orElseThrow(() -> new BusinessException("BUS_NOT_FOUND", "Bus not found with ID: " + busId));
        return convertToResponse(bus);
    }

    @Override
    public void deleteBus(Long busId) {
        Bus bus = busRepository.findById(busId)
                .orElseThrow(() -> new BusinessException("BUS_NOT_FOUND", "Bus not found with ID: " + busId));

        bus.setStatus("INACTIVE");
        busRepository.save(bus);
    }

    private BusResponse convertToResponse(Bus bus) {
        BusResponse response = new BusResponse();
        BeanUtils.copyProperties(bus, response);
        return response;
    }
}
