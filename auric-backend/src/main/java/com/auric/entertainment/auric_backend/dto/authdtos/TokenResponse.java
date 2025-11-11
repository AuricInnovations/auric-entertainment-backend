package com.auric.entertainment.auric_backend.dto.authdtos;

public record TokenResponse(String accessToken, String refreshToken, String tokenType) {
    public TokenResponse(String a, String r) { this(a, r, "Bearer"); }
}
