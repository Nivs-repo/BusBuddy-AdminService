package com.tcs.busbuddy.model;

import jakarta.persistence.*;

import lombok.Data;

@Entity
@Table(name = "app_user")
@Data
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;
    @Column(unique = true)
    private String userName;
    private String password;
    private String role;


}
