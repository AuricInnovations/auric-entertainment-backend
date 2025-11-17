package com.auric.entertainment.auric_backend.controller;

import com.auric.entertainment.auric_backend.dto.GalleryImageDto;
import com.auric.entertainment.auric_backend.entity.GalleryImage;
import com.auric.entertainment.auric_backend.repository.GalleryImageRepository;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class GalleryController {
    private final GalleryImageRepository repo;

    public GalleryController(GalleryImageRepository repo) {
        this.repo = repo;
    }

    @GetMapping("/gallery")
    public List<GalleryImageDto> publicGallery() {
        return repo.findByIsActiveTrueOrderBySortOrderAscCreatedAtDesc()
                .stream().map(GalleryImageDto::from).toList();
    }

    @GetMapping("/admin/gallery")
    public List<GalleryImageDto> adminList() {
        return repo.findAll(Sort.by("sortOrder").ascending())
                .stream().map(GalleryImageDto::from).toList();
    }

    @PostMapping("/admin/gallery")
    public GalleryImageDto create(@RequestBody GalleryImageDto dto) {
        GalleryImage g = new GalleryImage();
        g.setTitle(dto.title());
        g.setDescription(dto.description());
        g.setImageUrl(dto.imageUrl());
        g.setSortOrder(dto.sortOrder());
        g.setActive(dto.isActive());
        return GalleryImageDto.from(repo.save(g));
    }

    @PutMapping("/admin/gallery/{id}")
    public GalleryImageDto update(@PathVariable Long id, @RequestBody GalleryImageDto dto) {
        GalleryImage g = repo.findById(id).orElseThrow();
        g.setTitle(dto.title());
        g.setDescription(dto.description());
        g.setImageUrl(dto.imageUrl());
        g.setSortOrder(dto.sortOrder());
        g.setActive(dto.isActive());
        return GalleryImageDto.from(repo.save(g));
    }

    @DeleteMapping("/admin/gallery/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        repo.deleteById(id);
    }
}