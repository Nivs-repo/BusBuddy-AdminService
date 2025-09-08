package com.busbuddy.adminservice.driver.repository;

import com.busbuddy.adminservice.driver.model.Driver;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {
    boolean existsByContactNumber(String contactNumber);
    boolean existsByLicenseNumber(String licenseNumber);
    Optional<Driver> findByLicenseNumber(String licenseNumber);
}
