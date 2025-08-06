package com.tcs.busbuddy.location.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.tcs.busbuddy.location.model.Location;

public interface LocationRepository extends JpaRepository<Location, Long> {
    // Add custom queries if necessary
}
