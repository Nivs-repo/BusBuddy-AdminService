package com.busbuddy.adminservice.driver.service.impl;

import com.busbuddy.adminservice.common.constants.ErrorCodes;
import com.busbuddy.adminservice.common.exception.BusinessException;
import com.busbuddy.adminservice.common.exception.TechnicalException;
import com.busbuddy.adminservice.driver.dto.DriverRequest;
import com.busbuddy.adminservice.driver.dto.DriverResponse;
import com.busbuddy.adminservice.driver.model.Driver;
import com.busbuddy.adminservice.driver.repository.DriverRepository;
import com.busbuddy.adminservice.driver.service.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {

    private final DriverRepository driverRepository;

    @Override
    public DriverResponse createDriver(DriverRequest request) {
        try {
            if (driverRepository.existsByContactNumber(request.getContactNumber())) {
                throw new BusinessException(ErrorCodes.DRIVER_DUPLICATE_PHONE, "Contact number already exists");
            }
            if (driverRepository.existsByLicenseNumber(request.getLicenseNumber())) {
                throw new BusinessException(ErrorCodes.DRIVER_DUPLICATE_LICENSE, "License number already exists");
            }

            Driver driver = toEntity(request);
            Driver saved = driverRepository.save(driver);
            return toResponse(saved);

        } catch (Exception ex) {
            if (ex instanceof BusinessException) throw (BusinessException) ex;
            throw new TechnicalException(ErrorCodes.TECHNICAL_ERROR, "Failed to create driver");
        }
    }

    @Override
    public List<DriverResponse> getAllDrivers() {
        try {
            return driverRepository.findAll().stream()
                    .map(this::toResponse)
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            throw new TechnicalException(ErrorCodes.TECHNICAL_ERROR, "Failed to fetch drivers");
        }
    }

    @Override
    public DriverResponse getDriverById(Long driverId) {
        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new BusinessException(ErrorCodes.DRIVER_NOT_FOUND, "Driver not found"));
        return toResponse(driver);
    }

    @Override
    public DriverResponse updateDriver(Long driverId, DriverRequest request) {
        try {
            Driver existing = driverRepository.findById(driverId)
                    .orElseThrow(() -> new BusinessException(ErrorCodes.DRIVER_NOT_FOUND, "Driver not found"));

            if (StringUtils.hasText(request.getContactNumber())
                    && !request.getContactNumber().equals(existing.getContactNumber())
                    && driverRepository.existsByContactNumber(request.getContactNumber())) {
                throw new BusinessException(ErrorCodes.DRIVER_DUPLICATE_PHONE, "Contact number already exists");
            }
            if (StringUtils.hasText(request.getLicenseNumber())
                    && !request.getLicenseNumber().equals(existing.getLicenseNumber())
                    && driverRepository.existsByLicenseNumber(request.getLicenseNumber())) {
                throw new BusinessException(ErrorCodes.DRIVER_DUPLICATE_LICENSE, "License number already exists");
            }

            existing.setDriverName(request.getDriverName());
            existing.setContactNumber(request.getContactNumber());
            existing.setLicenseNumber(request.getLicenseNumber());
            existing.setAddress(request.getAddress());
            existing.setGovId(request.getGovId());
            existing.setIsActive(request.getIsActive());

            Driver updated = driverRepository.save(existing);
            return toResponse(updated);

        } catch (Exception ex) {
            if (ex instanceof BusinessException) throw (BusinessException) ex;
            throw new TechnicalException(ErrorCodes.TECHNICAL_ERROR, "Failed to update driver");
        }
    }

    @Override
    public void deleteDriver(Long driverId) {
        try {
            if (!driverRepository.existsById(driverId)) {
                throw new BusinessException(ErrorCodes.DRIVER_NOT_FOUND, "Driver not found");
            }
            driverRepository.deleteById(driverId);
        } catch (Exception ex) {
            if (ex instanceof BusinessException) throw (BusinessException) ex;
            throw new TechnicalException(ErrorCodes.TECHNICAL_ERROR, "Failed to delete driver");
        }
    }

    private Driver toEntity(DriverRequest request) {
        return Driver.builder()
                .driverName(request.getDriverName())
                .contactNumber(request.getContactNumber())
                .licenseNumber(request.getLicenseNumber())
                .address(request.getAddress())
                .govId(request.getGovId())
                .isActive(request.getIsActive() != null ? request.getIsActive() : "Y")
                .build();
    }

    private DriverResponse toResponse(Driver driver) {
        return DriverResponse.builder()
                .driverId(driver.getDriverId())
                .driverName(driver.getDriverName())
                .contactNumber(driver.getContactNumber())
                .licenseNumber(driver.getLicenseNumber())
                .address(driver.getAddress())
                .govId(driver.getGovId())
                .isActive(driver.getIsActive())
                .build();
    }
}
