package com.online.store.controller;


import com.online.store.dto.request.ProductDescriptionRequest;
import com.online.store.dto.response.ProductDescriptionResponse;
import com.online.store.service.ProductDescriptionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Api(value = "REST APIs related to Product Description Entity")
@RestController
@RequestMapping("/productDescription")
public class ProductDescriptionController {

    @Autowired
    private ProductDescriptionService productDescriptionService;

    @ApiOperation(value = "View a list of available product descriptions")
    @PostMapping("/all")
    public List<ProductDescriptionResponse> findAllProductDescriptions() {
        log.info("Request to find all product descriptions {}");
        return productDescriptionService.findAllProductDescription();
    }

    @ApiOperation(value = "Creates a new product description in the system")
    @PostMapping
    public ProductDescriptionResponse createProductDescription(@RequestBody ProductDescriptionRequest productDescriptionRequest) {
        log.info("Request to create product description {}", productDescriptionRequest);
        return productDescriptionService.createProductDescription(productDescriptionRequest);
    }

    @ApiOperation(value = "Update an existing product description in the system")
    @PutMapping("/{id}")
    public ProductDescriptionResponse modifyProductDescription(@RequestBody ProductDescriptionRequest productDescriptionRequest, @PathVariable String id) {
        log.info("Request to modify product description {}", productDescriptionRequest);
        return productDescriptionService.modifyProductDescription(id, productDescriptionRequest);
    }

    @ApiOperation(value = "Delete a product description from the system by id")
    @DeleteMapping("/{id}")
    public void deleteProductDescriptionById(@PathVariable String id) {
        log.info("Request to delete product description {}", id);
        productDescriptionService.deleteProductDescription(id);
    }

}
