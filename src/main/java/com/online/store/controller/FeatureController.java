package com.online.store.controller;

import com.online.store.dto.request.FeatureRequest;
import com.online.store.dto.response.FeatureResponse;
import com.online.store.service.FeatureService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/features")
public class FeatureController {

    @Autowired
    private FeatureService featureService;


    @GetMapping("/{id}")
    public ResponseEntity<FeatureResponse> findFeature(@PathVariable Long id) {
        log.info("Request to find feature {}", id);
        return ResponseEntity
                .ok()
                .body(featureService.getFeatureById(id));
    }

    @PostMapping
    public ResponseEntity<FeatureResponse> createFeature(
            @Valid @RequestBody FeatureRequest featureRequest) {
        log.info("Request to create feature {}", featureRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(featureService.createFeature(featureRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FeatureResponse> modifyFeature(
            @PathVariable Long id,
            @Valid @RequestBody FeatureRequest featureRequest) {
        log.info("Request to modify feature {}", featureRequest);
        return ResponseEntity
                .ok()
                .body(featureService.modifyFeature(id, featureRequest));
    }

    @DeleteMapping("/{id}")
    public void deleteFeature(@PathVariable Long id) {
        log.info("Request to delete feature {}", id);
        featureService.deleteFeature(id);
    }

    @PostMapping("/{id}")
    public ResponseEntity<FeatureResponse> createFeatureKey(
            @PathVariable Long id,
            @Valid @RequestBody FeatureRequest featureRequest) {
        log.info("Request to create feature key {}", featureRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(featureService.createFeatureKey(id, featureRequest));
    }

    @PutMapping("/{featureId}/featureKey/{id}")
    public ResponseEntity<FeatureResponse> modifyFeatureKey(
            @PathVariable Long featureId,
            @Valid @RequestBody FeatureRequest featureRequest,
            @PathVariable Long id) {
        log.info("Request to modify feature key {}", featureRequest);
        return ResponseEntity
                .ok()
                .body(featureService.modifyFeatureKey(id, featureRequest, featureId));
    }

    @GetMapping("FeatureKeys/{id}")
    public ResponseEntity<FeatureResponse> findFeatureKey(@PathVariable Long id) {
        log.info("Request to find feature key {}", id);
        return ResponseEntity
                .ok()
                .body(featureService.getFeatureKeyById(id));
    }

    @DeleteMapping("/FeatureKeys/{id}")
    public void deleteFeatureKey(@PathVariable Long id) {
        log.info("Request to delete feature key {}", id);
        featureService.deleteFeatureKey(id);
    }

}
