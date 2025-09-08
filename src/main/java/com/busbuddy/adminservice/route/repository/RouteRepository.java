package com.busbuddy.adminservice.route.repository;

import com.busbuddy.adminservice.route.model.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {

    boolean existsByRouteNameIgnoreCase(String routeName);

    Optional<Route> findByRouteNameIgnoreCase(String routeName);
}
