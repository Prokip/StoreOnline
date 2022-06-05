package com.online.store.controller;

import com.online.store.dto.request.CategoryRequest;
import com.online.store.dto.response.CategoryResponse;
import com.online.store.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> findCategory(@PathVariable Long id) {
        log.info("Request to find category {}", id);
        return ResponseEntity
                .ok()
                .body(categoryService.findById(id));
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(@Valid @RequestBody CategoryRequest categoryRequest) {
        log.info("Request to create category {}", categoryRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(categoryService.createCategory(categoryRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> modifyCategory(@PathVariable Long id,
                                                           @Valid @RequestBody CategoryRequest categoryRequest) {
        log.info("Request to modify category {}", categoryRequest);
        return ResponseEntity
                .ok()
                .body(categoryService.modifyCategory(id, categoryRequest));
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Long id) {
        log.info("Request to delete category {}", id);
        categoryService.deleteCategory(id);
    }

    @GetMapping
    public List<CategoryResponse> findAll(
            @RequestParam(required = false, defaultValue = "0") Integer pageNumber,
            @RequestParam(required = false, defaultValue = "20") Integer pageSize,
            @RequestParam(required = false, defaultValue = "name") String sortBy,
            @RequestParam(required = false) String parentCategory,
            @RequestParam(required = false) String name) {
        log.info("Request to find all categories {}", sortBy);
        return categoryService.findAll(pageNumber, pageSize, sortBy, parentCategory, name);
    }

}
