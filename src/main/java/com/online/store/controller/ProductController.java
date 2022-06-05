package com.online.store.controller;

import com.online.store.dto.request.ProductRequest;
import com.online.store.dto.response.ProductResponse;
import com.online.store.service.FileService;
import com.online.store.service.ImageService;
import com.online.store.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final FileService fileService;
    private final ImageService imageService;

    @Autowired
    public ProductController(ProductService productService, FileService fileService, ImageService imageService) {
        this.productService = productService;
        this.fileService = fileService;
        this.imageService = imageService;
    }

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

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/files/{id}")
    @ResponseBody
    public ResponseEntity<Resource> findFileById(@PathVariable Long id) {
        log.info("Request to find file{}", id);
        Resource file = fileService.findFileById(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/images/{id}")
    @ResponseBody
    public ResponseEntity<Resource> findImagesById(@PathVariable Long id) {
        log.info("Request to find image{}", id);
        Resource image = imageService.findImagesById(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; imageName=\"" + image.getFilename() + "\"")
                .body(image);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/files")
    public void createProductFile(@RequestParam("path") MultipartFile path) {
        log.info("Request to create file {}", path);
        fileService.saveProductFile(path);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/images")
    public void createProductImage(@RequestParam("path") MultipartFile path) {
        log.info("Request to create image {}", path);
        imageService.saveProductImages(path);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/files/{id}")
    public void deleteProductFile(@PathVariable Long id) {
        log.info("Request to delete file {}", id);
        fileService.deleteProductFile(id);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/images/{id}")
    public void deleteProductImage(@PathVariable Long id) {
        log.info("Request to delete image {}", id);
        imageService.deleteProductImage(id);
    }


}
