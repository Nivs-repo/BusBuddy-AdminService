package com.busbuddy.adminservice.assignment.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

import com.busbuddy.adminservice.bus.model.Bus;
import com.busbuddy.adminservice.driver.model.Driver;
import com.busbuddy.adminservice.location.model.Location;
import com.busbuddy.adminservice.route.model.Route;

@Entity
@Table(
    name = "route_assignment",
    uniqueConstraints = @UniqueConstraint(
        columnNames = {"route_id", "bus_id", "driver_id", "location_id"}
    )
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RouteAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "assignment_id")
    private Long assignmentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "route_id", nullable = false)
    private Route route;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bus_id", nullable = false)
    private Bus bus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id", nullable = false)
    private Driver driver;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;

    @Column(name = "assignment_date", nullable = false)
    private LocalDate assignmentDate;

    @Column(name = "is_active", length = 1, nullable = false)
    private String isActive = "Y";
}
