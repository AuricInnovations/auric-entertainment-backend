package com.auric.entertainment.auric_backend.controller;


import com.auric.entertainment.auric_backend.dto.authdtos.LoginRequest;
import com.auric.entertainment.auric_backend.dto.authdtos.RefreshRequest;
import com.auric.entertainment.auric_backend.dto.authdtos.RegisterRequest;
import com.auric.entertainment.auric_backend.dto.authdtos.TokenResponse;
import com.auric.entertainment.auric_backend.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService svc;

    public AuthController(AuthService svc) {
        this.svc = svc;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest req) {
        svc.register(req);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest req) {
        return ResponseEntity.ok(svc.login(req));
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenResponse> refresh(@RequestBody RefreshRequest req) {
        return ResponseEntity.ok(svc.refresh(req));
    }

    @GetMapping("/me")
    public ResponseEntity<?> me(Authentication auth, @AuthenticationPrincipal UserDetails user) {
        if (auth == null || !auth.isAuthenticated() || user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Unauthenticated"));
        }
        var roles = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        return ResponseEntity.ok(Map.of("username", user.getUsername(), "roles", roles));
    }
}