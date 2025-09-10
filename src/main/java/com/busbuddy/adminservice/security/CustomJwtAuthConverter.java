package com.busbuddy.adminservice.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;

@Component
public class CustomJwtAuthConverter extends JwtAuthenticationConverter {

    private final UserRepository userRepository;

    public CustomJwtAuthConverter(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.setJwtGrantedAuthoritiesConverter(this::mapRolesFromDb);
    }

    private Collection<GrantedAuthority> mapRolesFromDb(Jwt jwt) {
        // 🔹 Fetch email claim from JWT
        String email = jwt.getClaim("email");

        if (email == null || email.isBlank()) {
            throw new RuntimeException("JWT does not contain 'email' claim");
        }

        // 🔹 Validate against DB
        User user = userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new RuntimeException("User not found in DB for email: " + email));

        // 🔹 Return role as authority
        return Collections.singletonList(
                new SimpleGrantedAuthority("ROLE_" + user.getRole().toUpperCase())
        );
    }
}