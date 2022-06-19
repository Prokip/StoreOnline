package com.online.store.controller;

import com.online.store.dto.request.CategoryFindRequest;
import com.online.store.dto.request.CategoryRequest;
import com.online.store.dto.response.CategoryResponse;
import com.online.store.service.CategoryService;
import com.online.store.util.CategoryConversionUtil;
import com.online.store.util.CategoryJsonStructure;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

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
    public List<CategoryResponse> findAll(@Valid @RequestBody CategoryFindRequest categoryFindRequest) {
        log.info("Request to find all categories {}", categoryFindRequest);
        return categoryService.findAll(categoryFindRequest);
    }

    @GetMapping("/tree")
    public Map<Long, CategoryConversionUtil> findTree(@Valid @RequestBody CategoryFindRequest categoryFindRequest) {
        log.info("Request to find all categories {}", categoryFindRequest);
        return new CategoryJsonStructure().getJson(categoryService.findAll(categoryFindRequest));
    }

}
