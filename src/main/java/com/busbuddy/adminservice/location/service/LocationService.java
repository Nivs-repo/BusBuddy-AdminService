package com.busbuddy.adminservice.location.service;

import com.busbuddy.adminservice.location.model.Location;

import java.util.List;
import java.util.Optional;

public interface LocationService {

    Location createLocation(Location location);

    List<Location> getAllLocations();

    Location getLocationById(Long id);

    Location updateLocation(Long id, Location locationDetails);

    void deleteLocation(Long id);
}
