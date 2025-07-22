package com.tcs.busbuddy.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tcs.busbuddy.model.AppUser;
import com.tcs.busbuddy.repository.AppUserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{

    @Autowired
    private AppUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = userRepository.findByUserName(username).orElse(null);
        if (user == null){
            throw new UsernameNotFoundException("username - "+username+" not found");
        }
        return new CustomUserDetails(user);
    }
    
}
