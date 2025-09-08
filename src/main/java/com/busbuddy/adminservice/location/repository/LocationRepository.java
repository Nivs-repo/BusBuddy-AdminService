package com.busbuddy.adminservice.location.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.busbuddy.adminservice.location.model.Location;

public interface LocationRepository extends JpaRepository<Location, Long> {

    boolean existsByLocationNameIgnoreCase(String name);
    Optional<Location> findByLocationNameIgnoreCase(String name);
}
