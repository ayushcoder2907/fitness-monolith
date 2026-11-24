
package com.project.fitness_monolith.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;

@Component
public class JwtUtils {

    private final String jwtSecret =
            "YS1zdHJpbmctc2VjcmV0LTI1Ni1iaXRzLWxvbmchISE=";

    private final long jwtExpirationMs = 172800000L;

    // Generate JWT signing key
    private SecretKey key() {
        return Keys.hmacShaKeyFor(
                Decoders.BASE64.decode(jwtSecret)
        );
    }

    // Extract JWT from Authorization header
    public String getJwtFromHeader(HttpServletRequest request) {

        String bearerToken = request.getHeader("Authorization");

        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        return null;
    }

    // Generate JWT token
    public String generateToken(String userId, String role) {

        return Jwts.builder()
                .subject(userId)
                .claim("roles", List.of("ROLE_" + role))
                .issuedAt(new Date())
                .expiration(
                        new Date(System.currentTimeMillis() + jwtExpirationMs)
                )
                .signWith(key())
                .compact();
    }

    // Validate JWT token
    public boolean validateJwtToken(String jwtToken) {

        try {
            Jwts.parser()
                    .verifyWith(key())
                    .build()
                    .parseSignedClaims(jwtToken);

            return true;

        } catch (Exception e) {
            return false;
        }
    }

    // Extract user ID from JWT subject
    public String getUsernameFromToken(String jwt) {

        return Jwts.parser()
                .verifyWith(key())
                .build()
                .parseSignedClaims(jwt)
                .getPayload()
                .getSubject();
    }

    // Extract all JWT claims
    public Claims getAllClaims(String jwt) {

        return Jwts.parser()
                .verifyWith(key())
                .build()
                .parseSignedClaims(jwt)
                .getPayload();
    }

    public String getUserIdFromToken(String jwt) {
        return Jwts.parser().verifyWith((SecretKey) key())
                .build().parseSignedClaims(jwt)
                .getPayload().getSubject();
    }
}
