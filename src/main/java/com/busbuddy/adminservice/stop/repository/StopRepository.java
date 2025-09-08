package com.busbuddy.adminservice.stop.repository;

import com.busbuddy.adminservice.stop.model.Stop;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StopRepository extends JpaRepository<Stop, Long> {
    boolean existsByStopNameIgnoreCase(String stopName);
    Optional<Stop> findByStopNameIgnoreCase(String stopName);
}
