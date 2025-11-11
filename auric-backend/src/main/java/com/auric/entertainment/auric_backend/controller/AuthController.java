package com.auric.entertainment.auric_backend.controller;


import com.auric.entertainment.auric_backend.dto.authdtos.LoginRequest;
import com.auric.entertainment.auric_backend.dto.authdtos.RefreshRequest;
import com.auric.entertainment.auric_backend.dto.authdtos.RegisterRequest;
import com.auric.entertainment.auric_backend.dto.authdtos.TokenResponse;
import com.auric.entertainment.auric_backend.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService svc;

    public AuthController(AuthService svc) {
        this.svc = svc;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest req) {
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
    public java.util.Map<String,Object> me(org.springframework.security.core.Authentication auth) {
        var roles = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).toList();
        return java.util.Map.of(
                "name", auth.getName(),
                "authorities", roles
        );
    }
}