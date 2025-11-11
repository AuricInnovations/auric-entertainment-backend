package com.auric.entertainment.auric_backend.jwt;

import com.auric.entertainment.auric_backend.domain.UserEntity;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {
    private final Key key;
    private final long accessMinutes;
    private final long refreshDays;

    public JwtService(
            @Value("${security.jwt.secret}") String secret,
            @Value("${security.jwt.access-token-minutes}") long accessMinutes,
            @Value("${security.jwt.refresh-token-days}") long refreshDays) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.accessMinutes = accessMinutes;
        this.refreshDays = refreshDays;
    }

    public String generateAccessToken(UserEntity user) {
        Instant now = Instant.now();
        Instant exp = now.plusSeconds(accessMinutes * 60);
        return Jwts.builder()
                .setSubject(user.getEmail())
                .addClaims(Map.of("roles", user.getRoles()))
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(exp))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(UserEntity user) {
        Instant now = Instant.now();
        Instant exp = now.plusSeconds(refreshDays * 24 * 3600);
        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("type", "refresh")
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(exp))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractSubject(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token).getBody().getSubject();
    }
}
