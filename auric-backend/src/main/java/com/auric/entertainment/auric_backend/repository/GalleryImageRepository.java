package com.auric.entertainment.auric_backend.repository;

import com.auric.entertainment.auric_backend.entity.GalleryImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GalleryImageRepository extends JpaRepository<GalleryImage, Long> {
    List<GalleryImage> findByIsActiveTrueOrderBySortOrderAscCreatedAtDesc();
}

