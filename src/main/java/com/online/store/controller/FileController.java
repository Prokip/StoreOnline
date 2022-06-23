package com.online.store.controller;

import com.online.store.service.FileService;
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
@Api("REST APIs related to File Entity")
@RestController
@RequestMapping("/files")
public class FileController {

    @Autowired
    private FileService fileService;

    @ApiOperation(value = "Get file by id from the system")
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

    @ApiOperation(value = "Creates a new file in the system")
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public void createFile(@RequestParam("path") MultipartFile path) {
        log.info("Request to create file {}", path);
        fileService.saveProductFile(path);
    }

    @ApiOperation(value = "Delete file from the system by id")
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteFile(@PathVariable Long id) {
        log.info("Request to delete file {}", id);
        fileService.deleteProductFile(id);
    }


}
