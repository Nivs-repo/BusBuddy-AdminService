package com.busbuddy.adminservice.routestop.service.impl;

import com.busbuddy.adminservice.common.constants.ErrorCodes;
import com.busbuddy.adminservice.common.exception.BusinessException;
import com.busbuddy.adminservice.common.exception.TechnicalException;
import com.busbuddy.adminservice.route.model.Route;
import com.busbuddy.adminservice.route.repository.RouteRepository;
import com.busbuddy.adminservice.routestop.dto.RouteStopRequest;
import com.busbuddy.adminservice.routestop.model.RouteStop;
import com.busbuddy.adminservice.routestop.repository.RouteStopRepository;
import com.busbuddy.adminservice.routestop.service.RouteStopService;
import com.busbuddy.adminservice.stop.model.Stop;
import com.busbuddy.adminservice.stop.repository.StopRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class RouteStopServiceImpl implements RouteStopService {

    private final RouteStopRepository routeStopRepository;
    private final RouteRepository routeRepository;
    private final StopRepository stopRepository;

    @Override
    public List<String> addRouteStop(RouteStopRequest request) {
        try {
            // Fetch route by name
            Route route = routeRepository.findByRouteNameIgnoreCase(request.getRouteName().trim())
                    .orElseThrow(() -> new BusinessException(ErrorCodes.ROUTE_NOT_FOUND, "Route not found"));

            // Fetch stop by name
            Stop stop = stopRepository.findByStopNameIgnoreCase(request.getStopName().trim())
                    .orElseThrow(() -> new BusinessException(ErrorCodes.STOP_NOT_FOUND, "Stop not found"));

            // Check duplicate
            if (routeStopRepository.existsByRouteAndStop(route, stop)) {
                throw new BusinessException(ErrorCodes.ROUTE_STOP_DUPLICATE, "Stop already exists for this route");
            }

            // Fetch current stops ordered
            List<RouteStop> stops = routeStopRepository.findByRouteOrderBySequenceNumberAsc(route);

            int position;
            if (request.getSequenceNumber() == null || request.getSequenceNumber() > stops.size()) {
                // Append at end
                position = stops.size() + 1;
            } else if (request.getSequenceNumber() < 1) {
                throw new BusinessException(ErrorCodes.VALIDATION_ERROR, "Sequence number must be >= 1");
            } else {
                // Insert at given position
                position = request.getSequenceNumber();
            }

            // Create new stop
            RouteStop newStop = RouteStop.builder()
                    .route(route)
                    .stop(stop)
                    .sequenceNumber(position)
                    .build();

            // Insert into list (1-based index)
            stops.add(position - 1, newStop);

            // Re-sequence everything
            for (int i = 0; i < stops.size(); i++) {
                stops.get(i).setSequenceNumber(i + 1);
            }

            // Save all back
            routeStopRepository.saveAll(stops);

            // ✅ Return only stop names in order
            return stops.stream()
                    .map(rs -> rs.getStop().getStopName())
                    .collect(Collectors.toList());

        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error in addRouteStop", e);
            throw new TechnicalException(ErrorCodes.TECHNICAL_ERROR, "Failed to add stop to route");
        }
    }

    @Override
    public List<String> getStopsForRoute(String routeName) {
        Route route = routeRepository.findByRouteNameIgnoreCase(routeName.trim())
                .orElseThrow(() -> new BusinessException(ErrorCodes.ROUTE_NOT_FOUND, "Route not found"));

        return routeStopRepository.findByRouteOrderBySequenceNumberAsc(route)
                .stream()
                .map(rs -> rs.getStop().getStopName())
                .collect(Collectors.toList());
    }

    @Override
    public void deleteRouteStop(Long id) {
        if (!routeStopRepository.existsById(id)) {
            throw new BusinessException(ErrorCodes.ROUTE_STOP_NOT_FOUND, "RouteStop not found");
        }
        routeStopRepository.deleteById(id);
    }
}
