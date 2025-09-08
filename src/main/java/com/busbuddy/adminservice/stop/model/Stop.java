package com.busbuddy.adminservice.stop.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "stop")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Stop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stop_id")
    private Long stopId;

    @Column(name = "stop_name", length = 100, nullable = false)
    private String stopName;

    @Column(name = "gps_location", length = 100)
    private String gpsLocation;

    @Column(name = "landmark", length = 100)
    private String landmark;

    @Column(name = "image_url", length = 200)
    private String imageUrl;

}
