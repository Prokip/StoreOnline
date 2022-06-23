package com.online.store.controller;

import com.online.store.service.ImageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Api("REST APIs related to Image Entity")
@RestController
@RequestMapping("/images")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @ApiOperation(value = "Get image by id from the system")
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

    @ApiOperation(value = "Creates a new image in the system")
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public void createImage(@RequestParam("path") MultipartFile path) {
        log.info("Request to create image {}", path);
        imageService.saveProductImages(path);
    }

    @ApiOperation(value = "Delete image from the system by id")
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteImage(@PathVariable Long id) {
        log.info("Request to delete image {}", id);
        imageService.deleteProductImage(id);
    }


}
