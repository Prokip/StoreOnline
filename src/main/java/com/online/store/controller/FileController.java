package com.online.store.controller;

import com.online.store.service.FileService;
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
@RequestMapping("/files")
public class FileController {

    @Autowired
    private FileService fileService;


    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Resource> findFileById(@PathVariable Long id) {
        log.info("Request to find file{}", id);
        Resource file = fileService.findFileById(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public void createProductFile(@RequestParam("path") MultipartFile path) {
        log.info("Request to create file {}", path);
        fileService.saveProductFile(path);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteProductFile(@PathVariable Long id) {
        log.info("Request to delete file {}", id);
        fileService.deleteProductFile(id);
    }


}
