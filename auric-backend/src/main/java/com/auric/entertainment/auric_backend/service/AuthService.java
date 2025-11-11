package com.auric.entertainment.auric_backend.service;

import com.auric.entertainment.auric_backend.domain.Role;
import com.auric.entertainment.auric_backend.dto.authdtos.LoginRequest;
import com.auric.entertainment.auric_backend.dto.authdtos.RegisterRequest;
import com.auric.entertainment.auric_backend.domain.UserEntity;
import com.auric.entertainment.auric_backend.dto.authdtos.RefreshRequest;
import com.auric.entertainment.auric_backend.dto.authdtos.TokenResponse;
import com.auric.entertainment.auric_backend.jwt.JwtService;
import com.auric.entertainment.auric_backend.repository.UserRepository;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AuthService {
    private final UserRepository repo;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authManager;
    private final JwtService jwt;

    public AuthService(UserRepository repo, PasswordEncoder encoder, AuthenticationManager authManager, JwtService jwt) {
        this.repo = repo; this.encoder = encoder; this.authManager = authManager; this.jwt = jwt;
    }

    public void register(RegisterRequest req) {
        if (repo.existsByEmail(req.email())) throw new IllegalArgumentException("Email already exists");
        var user = new UserEntity();
        user.setEmail(req.email().toLowerCase());
        user.setFullName(req.fullName());
        user.setPassword(encoder.encode(req.password()));
        user.setRoles(Set.of(Role.USER));
        repo.save(user);
    }

    public TokenResponse login(LoginRequest req) {
        authManager.authenticate(new UsernamePasswordAuthenticationToken(req.email(), req.password()));
        var user = repo.findByEmail(req.email()).orElseThrow();
        var access = jwt.generateAccessToken(user);
        var refresh = jwt.generateRefreshToken(user);
        return new TokenResponse(access, refresh);
    }

    public TokenResponse refresh(RefreshRequest req) {
        var email = jwt.extractSubject(req.refreshToken()); // will throw if invalid/expired
        var user = repo.findByEmail(email).orElseThrow();
        // Optional: validate "type" claim = refresh
        var access = jwt.generateAccessToken(user);
        return new TokenResponse(access, req.refreshToken());
    }
}