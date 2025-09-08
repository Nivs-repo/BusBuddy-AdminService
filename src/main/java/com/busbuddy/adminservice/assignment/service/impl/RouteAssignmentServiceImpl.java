package com.busbuddy.adminservice.assignment.service.impl;

import com.busbuddy.adminservice.assignment.dto.RouteAssignmentRequest;
import com.busbuddy.adminservice.assignment.dto.RouteAssignmentResponse;
import com.busbuddy.adminservice.assignment.model.RouteAssignment;
import com.busbuddy.adminservice.assignment.repository.RouteAssignmentRepository;
import com.busbuddy.adminservice.bus.model.Bus;
import com.busbuddy.adminservice.bus.repository.BusRepository;
import com.busbuddy.adminservice.common.constants.ErrorCodes;
import com.busbuddy.adminservice.common.exception.BusinessException;
import com.busbuddy.adminservice.common.exception.TechnicalException;
import com.busbuddy.adminservice.driver.model.Driver;
import com.busbuddy.adminservice.driver.repository.DriverRepository;
import com.busbuddy.adminservice.location.model.Location;
import com.busbuddy.adminservice.location.repository.LocationRepository;
import com.busbuddy.adminservice.route.model.Route;
import com.busbuddy.adminservice.route.repository.RouteRepository;
import com.busbuddy.adminservice.assignment.service.RouteAssignmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class RouteAssignmentServiceImpl implements RouteAssignmentService {

    private final RouteAssignmentRepository assignmentRepository;
    private final RouteRepository routeRepository;
    private final BusRepository busRepository;
    private final DriverRepository driverRepository;
    private final LocationRepository locationRepository;

    private RouteAssignmentResponse toResponse(RouteAssignment a) {
        return RouteAssignmentResponse.builder()
                .assignmentId(a.getAssignmentId())
                .routeName(a.getRoute().getRouteName())
                .busRegNumber(a.getBus().getRegNumber())
                .driverName(a.getDriver().getDriverName())
                .driverLicenseNumber(a.getDriver().getLicenseNumber())
                .locationName(a.getLocation().getLocationName())
                .assignmentDate(a.getAssignmentDate()) // ✅ added
                .isActive(a.getIsActive())
                .build();
    }

    @Override
    public RouteAssignmentResponse assignBusDriver(RouteAssignmentRequest request) {
        try {
            Route route = routeRepository.findByRouteNameIgnoreCase(request.getRouteName())
                    .orElseThrow(() -> new BusinessException(ErrorCodes.ROUTE_NOT_FOUND, "Route not found"));

            Bus bus = busRepository.findByRegNumberIgnoreCase(request.getBusRegNumber())
                    .orElseThrow(() -> new BusinessException(ErrorCodes.BUS_NOT_FOUND, "Bus not found"));

            Driver driver = driverRepository.findByLicenseNumber(request.getDriverLicenseNumber())
                    .orElseThrow(() -> new BusinessException(ErrorCodes.DRIVER_NOT_FOUND, "Driver not found"));

            Location location = locationRepository.findByLocationNameIgnoreCase(request.getLocationName())
                    .orElseThrow(() -> new BusinessException(ErrorCodes.LOCATION_NOT_FOUND, "Location not found"));

            if (assignmentRepository.existsByRoute_RouteIdAndBus_BusIdAndDriver_DriverIdAndLocation_LocationId(
                    route.getRouteId(), bus.getBusId(), driver.getDriverId(), location.getLocationId())) {
                throw new BusinessException(ErrorCodes.DUPLICATE_ASSIGNMENT,
                        "This bus-driver-route-location combination already exists");
            }

            RouteAssignment assignment = RouteAssignment.builder()
                    .route(route)
                    .bus(bus)
                    .driver(driver)
                    .location(location)
                    .assignmentDate(LocalDate.now()) 
                    .isActive("Y")
                    .build();

            return toResponse(assignmentRepository.save(assignment));
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error assigning bus & driver to route", e);
            throw new TechnicalException(ErrorCodes.TECHNICAL_ERROR, "Unexpected error occurred");
        }
    }

    @Override
    public List<RouteAssignmentResponse> getAssignmentsByLocation(String locationName) {
        try {
            return assignmentRepository.findAll().stream() 
                    .filter(a -> a.getLocation().getLocationName().equalsIgnoreCase(locationName))
                    .map(this::toResponse)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error fetching assignments by location", e);
            throw new TechnicalException(ErrorCodes.TECHNICAL_ERROR, "Unexpected error occurred");
        }
    }

    @Override
    public RouteAssignmentResponse unassign(Long assignmentId) {
        try {
            var assignment = assignmentRepository.findById(assignmentId)
                    .orElseThrow(() -> new BusinessException(ErrorCodes.ASSIGNMENT_NOT_FOUND, "Assignment not found"));

            assignment.setIsActive("N"); 
            RouteAssignment updated = assignmentRepository.save(assignment);

            return toResponse(updated);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error unassigning route assignment", e);
            throw new TechnicalException(ErrorCodes.TECHNICAL_ERROR, "Unexpected error occurred");
        }
    }
}
