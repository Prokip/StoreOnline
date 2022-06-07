package com.online.store.controller;

import com.online.store.service.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/images")
public class ImageController {

    @Autowired
    private ImageService imageService;


    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Resource> findImagesById(@PathVariable Long id) {
        log.info("Request to find image{}", id);
        Resource image = imageService.findImagesById(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; imageName=\"" + image.getFilename() + "\"")
                .body(image);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public void createProductImage(@RequestParam("path") MultipartFile path) {
        log.info("Request to create image {}", path);
        imageService.saveProductImages(path);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteProductImage(@PathVariable Long id) {
        log.info("Request to delete image {}", id);
        imageService.deleteProductImage(id);
    }


}
