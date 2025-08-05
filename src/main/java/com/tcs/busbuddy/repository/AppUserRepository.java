package com.tcs.busbuddy.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tcs.busbuddy.model.AppUser;


public interface AppUserRepository extends JpaRepository<AppUser, Long>{
    Optional<AppUser> findByUsername(String userName);
}

