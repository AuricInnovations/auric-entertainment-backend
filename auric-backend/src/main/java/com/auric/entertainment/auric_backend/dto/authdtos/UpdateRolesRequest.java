package com.auric.entertainment.auric_backend.dto.authdtos;

import com.auric.entertainment.auric_backend.domain.Role;

import java.util.Set;

public record UpdateRolesRequest(Set<Role> roles) {
}
