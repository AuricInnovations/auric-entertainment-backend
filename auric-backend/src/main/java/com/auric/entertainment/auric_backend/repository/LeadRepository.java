package com.auric.entertainment.auric_backend.repository;

import com.auric.entertainment.auric_backend.entity.Lead;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeadRepository extends JpaRepository<Lead, Long> {
}