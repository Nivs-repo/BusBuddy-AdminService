package com.busbuddy.adminservice.routestop.repository;

import com.busbuddy.adminservice.routestop.model.RouteStop;
import com.busbuddy.adminservice.route.model.Route;
import com.busbuddy.adminservice.stop.model.Stop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RouteStopRepository extends JpaRepository<RouteStop, Long> {

    List<RouteStop> findByRouteOrderBySequenceNumberAsc(Route route);

    boolean existsByRouteAndStop(Route route, Stop stop);
}
