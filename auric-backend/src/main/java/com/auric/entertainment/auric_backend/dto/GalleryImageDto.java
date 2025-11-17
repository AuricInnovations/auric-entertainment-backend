package com.auric.entertainment.auric_backend.dto;

import com.auric.entertainment.auric_backend.entity.GalleryImage;

public record GalleryImageDto(
        Long id,
        String title,
        String description,
        String imageUrl,
        Integer sortOrder,
        boolean isActive
) {
    public static GalleryImageDto from(GalleryImage g) {
        return new GalleryImageDto(
                g.getId(), g.getTitle(), g.getDescription(),
                g.getImageUrl(), g.getSortOrder(), g.isActive()
        );
    }
}