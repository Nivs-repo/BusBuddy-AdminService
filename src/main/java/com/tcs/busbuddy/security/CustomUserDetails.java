package com.tcs.busbuddy.security;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.tcs.busbuddy.model.AppUser;

public class CustomUserDetails implements UserDetails {

    private final AppUser appUser;

    public CustomUserDetails(AppUser appUser) {
        this.appUser = appUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + appUser.getRole()));
    }

    @Override
    public String getPassword() {
        return appUser.getPassword();
    }

    @Override
    public String getUsername() {
        return appUser.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // or appUser.isAccountNonExpired() if field exists
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // or appUser.isAccountNonLocked()
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // or appUser.isCredentialsNonExpired()
    }

    @Override
    public boolean isEnabled() {
        return true; // or appUser.isEnabled()
    }

    public AppUser getAppUser() {
        return appUser;
    }
}
