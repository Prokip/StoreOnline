package com.online.store.controller;

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
    public List<ProductResponse> getProductsByParam(
            @RequestParam(required = false, defaultValue = "0") Integer pageNumber,
            @RequestParam(required = false, defaultValue = "20") Integer pageSize,
            @RequestParam(required = false, defaultValue = "name") String sortBy,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String feature,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer price,
            @RequestParam(required = false) String parentCategory) {
        log.info("Request to find all products {}", sortBy);
        return productService.findProductByParam(pageNumber, pageSize, sortBy, category,
                feature, name, price, parentCategory);
    }

    @GetMapping("/{id}")
    public ProductResponse getProductById(@PathVariable Long id) {
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
