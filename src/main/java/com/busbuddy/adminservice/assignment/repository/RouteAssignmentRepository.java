package com.busbuddy.adminservice.assignment.repository;

import com.busbuddy.adminservice.assignment.model.RouteAssignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RouteAssignmentRepository extends JpaRepository<RouteAssignment, Long> {

    boolean existsByRoute_RouteIdAndBus_BusIdAndDriver_DriverIdAndLocation_LocationId(Long routeId, Long busId, Long driverId, Long locationId);

    Optional<RouteAssignment> findByRoute_RouteIdAndLocation_LocationId(Long routeId, Long locationId);

    Optional<RouteAssignment> findByBus_BusIdAndIsActive(Long busId, String isActive);

    List<RouteAssignment> findByLocation_LocationIdAndIsActive(Long locationId, String isActive);
}
