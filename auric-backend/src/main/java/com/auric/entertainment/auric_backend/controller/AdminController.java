package com.auric.entertainment.auric_backend.controller;

import com.auric.entertainment.auric_backend.domain.Role;
import com.auric.entertainment.auric_backend.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final UserRepository repo;

    public AdminController(UserRepository repo) { this.repo = repo; }

    @PostMapping("/{id}/roles")
    public ResponseEntity<?> setRoles(@PathVariable Long id, @RequestBody Set<Role> roles) {
        var u = repo.findById(id).orElseThrow();
        u.setRoles(roles);
        repo.save(u);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/ping")
    public String ping() {
        return "admin-ok";
    }


}
