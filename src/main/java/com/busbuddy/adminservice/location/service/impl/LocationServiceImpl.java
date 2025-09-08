package com.busbuddy.adminservice.location.service.impl;

import com.busbuddy.adminservice.common.constants.ErrorCodes;
import com.busbuddy.adminservice.common.exception.BusinessException;
import com.busbuddy.adminservice.common.exception.TechnicalException;
import com.busbuddy.adminservice.location.model.Location;
import com.busbuddy.adminservice.location.repository.LocationRepository;
import com.busbuddy.adminservice.location.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;

    @Override
    public Location createLocation(Location location) {
        try {
            String normalizedName = location.getLocationName().trim();

            if (locationRepository.existsByLocationNameIgnoreCase(normalizedName)) {
                throw new BusinessException(ErrorCodes.LOCATION_ALREADY_EXISTS,
                        "Location with name '" + normalizedName + "' already exists");
            }

            location.setLocationName(normalizedName);
            return locationRepository.save(location);
        } catch (Exception ex) {
            throw new TechnicalException(ErrorCodes.TECHNICAL_ERROR, "Failed to create location due to system error");
        }
    }

    @Override
    public List<Location> getAllLocations() {
        try {
            return locationRepository.findAll();
        } catch (Exception ex) {
            throw new TechnicalException(ErrorCodes.TECHNICAL_ERROR, "Failed to fetch location list");
        }
    }

    @Override
    public Location getLocationById(Long id) {
        return locationRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCodes.LOCATION_NOT_FOUND,
                        "Location with ID " + id + " not found"));
    }

    @Override
    public Location updateLocation(Long id, Location locationDetails) {
        try {
            Location location = locationRepository.findById(id)
                    .orElseThrow(() -> new BusinessException(ErrorCodes.LOCATION_NOT_FOUND,
                            "Location with ID " + id + " not found"));

            String normalizedName = locationDetails.getLocationName().trim();

            if (!location.getLocationName().equalsIgnoreCase(normalizedName)
                    && locationRepository.existsByLocationNameIgnoreCase(normalizedName)) {
                throw new BusinessException(ErrorCodes.LOCATION_ALREADY_EXISTS,
                        "Location with name '" + normalizedName + "' already exists");
            }

            location.setLocationName(normalizedName);
            return locationRepository.save(location);
        } catch (Exception ex) {
            throw new TechnicalException(ErrorCodes.TECHNICAL_ERROR, "Failed to update location");
        }
    }

    @Override
    public void deleteLocation(Long id) {
        try {
            Location location = locationRepository.findById(id)
                    .orElseThrow(() -> new BusinessException(ErrorCodes.LOCATION_NOT_FOUND,
                            "Location with ID " + id + " not found"));

            locationRepository.deleteById(id);
        } catch (Exception ex) {
            throw new TechnicalException(ErrorCodes.TECHNICAL_ERROR, "Failed to delete location");
        }
    }
}
