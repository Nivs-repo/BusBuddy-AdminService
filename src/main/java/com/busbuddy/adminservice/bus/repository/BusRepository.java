package com.busbuddy.adminservice.bus.repository;

import com.busbuddy.adminservice.bus.model.Bus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BusRepository extends JpaRepository<Bus, Long> {

    boolean existsByRegNumber(String regNumber);

    Optional<Bus> findByRegNumber(String regNumber);

    Optional<Bus> findByRegNumberIgnoreCase(String regNumber);
}
