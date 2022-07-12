package com.online.store.controller;

import com.online.store.dto.request.ProductFindRequest;
import com.online.store.dto.request.ProductRequest;
import com.online.store.dto.response.ProductResponse;
import com.online.store.service.ProductService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Api(value = "REST APIs related to Product Entity")
@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @ApiOperation(value = "View a list of available products")
    @PostMapping("/all")
    public List<ProductResponse> findProductsByParam(@Valid @RequestBody ProductFindRequest productFindRequest) {
        log.info("Request to find all products {}", productFindRequest);
        return productService.findProductsByParam(productFindRequest);
    }

    @ApiOperation(value = "Search a product with an ID")
    @ApiImplicitParam(
            name = "id",
            value = "Id product in db",
            required = true,
            dataType = "Long",
            paramType = "path")
    @GetMapping("/{id}")
    public ProductResponse findProductById(@PathVariable Long id) {
        log.info("Request to find product by id {}", id);
        return productService.findProductById(id);
    }

    @ApiOperation(value = "Add product to favourites")
    @ApiImplicitParams(value = {
            @ApiImplicitParam (
                    name = "id",
                    value = "Id product in db",
                    required = true,
                    dataType = "Long",
                    paramType = "path"),
            @ApiImplicitParam (
                    name = "userId",
                    value = "userId in db",
                    required = true,
                    dataType = "Long",
                    paramType = "path")})
    @PutMapping("/{id}/favourites/{userId}")
    public void putProductToFavouriteList(@PathVariable Long id, @PathVariable Long userId) {
        log.info("Request to add product to favourite{}", id);
        productService.putProductToFavourites(id, userId);
    }

    @ApiOperation(value = "Delete a product from favourites")
    @ApiImplicitParams(value = {
            @ApiImplicitParam (
                    name = "id",
                    value = "Id product in db",
                    required = true,
                    dataType = "Long",
                    paramType = "path"),
            @ApiImplicitParam (
                    name = "userId",
                    value = "userId in db",
                    required = true,
                    dataType = "Long",
                    paramType = "path")})
    @DeleteMapping("/{id}/favourites/{userId}")
    public void deleteProductFromFavouriteList(@PathVariable Long id, @PathVariable Long userId) {
        log.info("Request to delete product from favourite{}", id);
        productService.deleteProductFromFavourites(id, userId);
    }

    @ApiOperation(value = "Creates a new product in the system")
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ProductResponse createProduct(@Valid @RequestBody ProductRequest productRequest) {
        log.info("Request to create product {}", productRequest);
        return productService.createProduct(productRequest);
    }

    @ApiOperation(value = "Update an existing product in the system")
    @ApiImplicitParam(
            name = "id",
            value = "Id product in db",
            required = true,
            dataType = "Long",
            paramType = "path")
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public ProductResponse modifyProduct(@Valid @RequestBody ProductRequest productRequest, @PathVariable Long id) {
        log.info("Request to modify product {}", productRequest);
        return productService.modifyProduct(productRequest, id);
    }

    @ApiOperation(value = "Delete a product from the system by id")
    @ApiImplicitParam(
            name = "id",
            value = "Id product in db",
            required = true,
            dataType = "Long",
            paramType = "path")
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteProductById(@PathVariable Long id) {
        log.info("Request to delete product {}", id);
        productService.deleteProduct(id);
    }

}
