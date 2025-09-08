package com.busbuddy.adminservice.bus.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "bus")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bus_id")
    private Long busId;

    @NotBlank(message = "Registration number is required")
    @Size(max = 20, message = "Registration number must not exceed 20 characters")
    @Column(name = "reg_number", nullable = false, unique = true, length = 20)
    private String regNumber;

    @Column(name = "capacity")
    private Integer capacity;

    @Column(name = "status", length = 20)
    private String status = "ACTIVE";
}
