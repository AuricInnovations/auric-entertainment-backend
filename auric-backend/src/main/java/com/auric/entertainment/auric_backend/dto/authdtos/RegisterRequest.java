package com.auric.entertainment.auric_backend.dto.authdtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterRequest(
        @NotBlank String fullname,
        @NotBlank @Email String email,
        @NotBlank String password
) {}