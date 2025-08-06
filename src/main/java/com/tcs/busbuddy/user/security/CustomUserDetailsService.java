package com.tcs.busbuddy.user.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tcs.busbuddy.user.model.AppUser;
import com.tcs.busbuddy.user.repository.AppUserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{

    @Autowired
    private AppUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = userRepository.findByUsername(username).orElse(null);
        if (user == null){
            throw new UsernameNotFoundException("username - "+username+" not found");
        }
        return new CustomUserDetails(user);
    }
    
}
