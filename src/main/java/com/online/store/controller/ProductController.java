package com.online.store.controller;

import com.online.store.dto.request.ProductFindRequest;
import com.online.store.dto.request.ProductRequest;
import com.online.store.dto.response.ProductResponse;
import com.online.store.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;


    @GetMapping
    public List<ProductResponse> findProductsByParam(@Valid @RequestBody ProductFindRequest productFindRequest) {
        log.info("Request to find all products {}", productFindRequest);
        return productService.findProductByParam(productFindRequest);
    }

    @GetMapping("/{id}")
    public ProductResponse findProductById(@PathVariable Long id) {
        log.info("Request to find product by id {}", id);
        return productService.findProductById(id);
    }

    @PutMapping("/{id}/favourites/{userId}")
    public void putProductToFavouriteList(@PathVariable Long id, @PathVariable Long userId) {
        log.info("Request to add product to favourite{}", id);
        productService.putProductToFavourites(id, userId);
    }

    @DeleteMapping("/{id}/favourites/{userId}")
    public void deleteProductFromFavouriteList(@PathVariable Long id, @PathVariable Long userId) {
        log.info("Request to delete product from favourite{}", id);
        productService.deleteProductFromFavourites(id, userId);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ProductResponse createProduct(@Valid @RequestBody ProductRequest productRequest) {
        log.info("Request to create product {}", productRequest);
        return productService.createProduct(productRequest);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public ProductResponse modifyProduct(@Valid @RequestBody ProductRequest productRequest, @PathVariable Long id) {
        log.info("Request to modify product {}", productRequest);
        return productService.modifyProduct(productRequest, id);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteProductById(@PathVariable Long id) {
        log.info("Request to delete product {}", id);
        productService.deleteProduct(id);
    }

}
