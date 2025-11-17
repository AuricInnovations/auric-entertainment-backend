package com.auric.entertainment.auric_backend.entity;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "gallery_images")
public class GalleryImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    @Column(name = "image_url", nullable = false)
    private String imageUrl;
    @Column(name = "sort_order")
    private Integer sortOrder;
    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;
    @Column(name = "created_at", updatable = false, insertable = false)
    private Timestamp createdAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}