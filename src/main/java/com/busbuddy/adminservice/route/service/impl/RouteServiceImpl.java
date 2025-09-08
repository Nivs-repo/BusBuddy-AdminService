package com.busbuddy.adminservice.route.service.impl;

import com.busbuddy.adminservice.common.constants.ErrorCodes;
import com.busbuddy.adminservice.common.exception.BusinessException;
import com.busbuddy.adminservice.common.exception.TechnicalException;
import com.busbuddy.adminservice.route.dto.RouteRequest;
import com.busbuddy.adminservice.route.dto.RouteResponse;
import com.busbuddy.adminservice.route.model.Route;
import com.busbuddy.adminservice.route.repository.RouteRepository;
import com.busbuddy.adminservice.route.service.RouteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class RouteServiceImpl implements RouteService {

    private final RouteRepository routeRepository;

    private RouteResponse toResponse(Route route) {
        return RouteResponse.builder()
                .routeId(route.getRouteId())
                .routeName(route.getRouteName())
                .origin(route.getOrigin())
                .destination(route.getDestination())
                .build();
    }

    @Override
    public RouteResponse addRoute(RouteRequest request) {
        try {
            String routeName = request.getRouteName().trim();

            if (routeRepository.existsByRouteNameIgnoreCase(routeName)) {
                throw new BusinessException(ErrorCodes.DUPLICATE_ROUTE, "Route with this name already exists");
            }

            Route route = Route.builder()
                    .routeName(routeName)
                    .origin(request.getOrigin())
                    .destination(request.getDestination())
                    .build();

            Route saved = routeRepository.save(route);
            return toResponse(saved);

        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error while adding route", e);
            throw new TechnicalException(ErrorCodes.ROUTE_CREATION_FAILED, "Unexpected error occurred while creating route");
        }
    }

    @Override
    public RouteResponse updateRoute(Long routeId, RouteRequest request) {
        try {
            Route existing = routeRepository.findById(routeId)
                    .orElseThrow(() -> new BusinessException(ErrorCodes.ROUTE_NOT_FOUND, "Route not found"));

            String newName = request.getRouteName().trim();
            if (!existing.getRouteName().equalsIgnoreCase(newName)
                    && routeRepository.existsByRouteNameIgnoreCase(newName)) {
                throw new BusinessException(ErrorCodes.DUPLICATE_ROUTE, "Another route with this name already exists");
            }

            existing.setRouteName(newName);
            existing.setOrigin(request.getOrigin());
            existing.setDestination(request.getDestination());

            Route updated = routeRepository.save(existing);
            return toResponse(updated);

        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error while updating route", e);
            throw new TechnicalException(ErrorCodes.ROUTE_UPDATE_FAILED, "Unexpected error occurred while updating route");
        }
    }

    @Override
    public void deleteRoute(Long routeId) {
        try {
            Route existing = routeRepository.findById(routeId)
                    .orElseThrow(() -> new BusinessException(ErrorCodes.ROUTE_NOT_FOUND, "Route not found"));

            routeRepository.delete(existing);

        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error while deleting route", e);
            throw new TechnicalException(ErrorCodes.ROUTE_DELETE_FAILED, "Unexpected error occurred while deleting route");
        }
    }

    @Override
    public RouteResponse getRouteById(Long routeId) {
        try {
            Route route = routeRepository.findById(routeId)
                    .orElseThrow(() -> new BusinessException(ErrorCodes.ROUTE_NOT_FOUND, "Route not found"));

            return toResponse(route);

        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error while fetching route by id", e);
            throw new TechnicalException(ErrorCodes.ROUTE_FETCH_FAILED, "Unexpected error occurred while fetching route");
        }
    }

    @Override
    public List<RouteResponse> getAllRoutes() {
        try {
            return routeRepository.findAll()
                    .stream()
                    .map(this::toResponse)
                    .collect(Collectors.toList());

        } catch (Exception e) {
            log.error("Error while fetching all routes", e);
            throw new TechnicalException(ErrorCodes.ROUTE_FETCH_FAILED, "Unexpected error occurred while fetching routes");
        }
    }
}
