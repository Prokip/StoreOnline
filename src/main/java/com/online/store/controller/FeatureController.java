package com.online.store.controller;

import com.online.store.dto.request.FeatureRequest;
import com.online.store.dto.response.FeatureResponse;
import com.online.store.service.FeatureService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@Api("REST APIs related to Feature Entity")
@RestController
@RequestMapping("/features")
public class FeatureController {

    @Autowired
    private FeatureService featureService;


    @ApiOperation(value = "View a feature by id from the system")
    @ApiImplicitParam(
            name = "id",
            value = "Id feature in db",
            required = true,
            dataType = "Long",
            paramType = "path")
    @GetMapping("/{id}")
    public ResponseEntity<FeatureResponse> findFeature(@PathVariable Long id) {
        log.info("Request to find feature {}", id);
        return ResponseEntity
                .ok()
                .body(featureService.getFeatureById(id));
    }

    @ApiOperation(value = "Creates a new feature in the system")
    @PostMapping
    public ResponseEntity<FeatureResponse> createFeature(
            @Valid @RequestBody FeatureRequest featureRequest) {
        log.info("Request to create feature {}", featureRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(featureService.createFeature(featureRequest));
    }

    @ApiOperation(value = "Update existing feature in the system")
    @ApiImplicitParam(
            name = "id",
            value = "Id feature in db",
            required = true,
            dataType = "Long",
            paramType = "path")
    @PutMapping("/{id}")
    public ResponseEntity<FeatureResponse> modifyFeature(
            @PathVariable Long id,
            @Valid @RequestBody FeatureRequest featureRequest) {
        log.info("Request to modify feature {}", featureRequest);
        return ResponseEntity
                .ok()
                .body(featureService.modifyFeature(id, featureRequest));
    }

    @ApiOperation(value = "Delete existing feature from the system by id")
    @ApiImplicitParam(
            name = "id",
            value = "Id feature in db",
            required = true,
            dataType = "Long",
            paramType = "path")
    @DeleteMapping("/{id}")
    public void deleteFeature(@PathVariable Long id) {
        log.info("Request to delete feature {}", id);
        featureService.deleteFeature(id);
    }

    @ApiOperation(value = "View a feature key by id from the system")
    @ApiImplicitParam(
            name = "id",
            value = "Id feature key in db",
            required = true,
            dataType = "Long",
            paramType = "path")
    @PostMapping("/{id}")
    public ResponseEntity<FeatureResponse> createFeatureKey(
            @PathVariable Long id,
            @Valid @RequestBody FeatureRequest featureRequest) {
        log.info("Request to create feature key {}", featureRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(featureService.createFeatureKey(id, featureRequest));
    }

    @ApiOperation(value = "Update existing feature key in the system")
    @ApiImplicitParam(
            name = "id",
            value = "Id feature key in db",
            required = true,
            dataType = "Long",
            paramType = "path")
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

    @ApiOperation(value = "View a list of feature`s keys")
    @GetMapping("FeatureKeys/{id}")
    public ResponseEntity<FeatureResponse> findFeatureKey(@PathVariable Long id) {
        log.info("Request to find feature key {}", id);
        return ResponseEntity
                .ok()
                .body(featureService.getFeatureKeyById(id));
    }

    @ApiOperation(value = "Delete feature key from the system by id")
    @ApiImplicitParam(
            name = "id",
            value = "Id feature Key in db",
            required = true,
            dataType = "Long",
            paramType = "path")
    @DeleteMapping("/FeatureKeys/{id}")
    public void deleteFeatureKey(@PathVariable Long id) {
        log.info("Request to delete feature key {}", id);
        featureService.deleteFeatureKey(id);
    }

}
