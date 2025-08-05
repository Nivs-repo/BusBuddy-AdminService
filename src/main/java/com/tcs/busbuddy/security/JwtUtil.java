package com.tcs.busbuddy.security;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    private static final String secret = "BusBuddyAppSuperSecureKey123456!";
    private final Key key = Keys.hmacShaKeyFor(secret.getBytes());

    public String generateToken(String userName) {
        return Jwts.builder()

                .setSubject(userName)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + (60 * 60 * 1000)))
                .signWith(key)

                .compact();
    }

    public Boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()

                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String extractUsername(String token) {
        return Jwts.parserBuilder()

                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)

                .getBody()
                .getSubject();
    }

}
