package com.online.store.controller;

import com.online.store.dto.request.CategoryFindRequest;
import com.online.store.dto.request.CategoryRequest;
import com.online.store.dto.response.CategoryResponse;
import com.online.store.service.CategoryService;
import com.online.store.util.CategoryConversionUtil;
import com.online.store.util.CategoryJsonStructure;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Slf4j
@Api("REST APIs related to Category Entity")
@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 401, message = "Not authorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not found")})
    @ApiOperation(value = "View a category by id from the system")
    @ApiImplicitParam(
            name = "id",
            value = "Id category in db",
            required = true,
            dataType = "Long",
            paramType = "path")
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> findCategory(@PathVariable Long id) {
        log.info("Request to find category {}", id);
        return ResponseEntity
                .ok()
                .body(categoryService.findById(id));
    }

    @ApiOperation(value = "Creates a new category in the system")
    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(@Valid @RequestBody CategoryRequest categoryRequest) {
        log.info("Request to create category {}", categoryRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(categoryService.createCategory(categoryRequest));
    }

    @ApiOperation(value = "Update existing category in the system")
    @ApiImplicitParam(
            name = "id",
            value = "Id category in db",
            required = true,
            dataType = "Long",
            paramType = "path")
    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> modifyCategory(@PathVariable Long id,
                                                           @Valid @RequestBody CategoryRequest categoryRequest) {
        log.info("Request to modify category {}", categoryRequest);
        return ResponseEntity
                .ok()
                .body(categoryService.modifyCategory(id, categoryRequest));
    }

    @ApiOperation(value = "Delete category from the system by id")
    @ApiImplicitParam(
            name = "id",
            value = "Id category in db",
            required = true,
            dataType = "Long",
            paramType = "path")
    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Long id) {
        log.info("Request to delete category {}", id);
        categoryService.deleteCategory(id);
    }

    @ApiOperation(value = "View a list of Category")
    @PostMapping("/all")
    public List<CategoryResponse> findAll(@Valid @RequestBody CategoryFindRequest categoryFindRequest) {
        log.info("Request to find all categories {}", categoryFindRequest);
        return categoryService.findAll(categoryFindRequest);
    }

    @ApiOperation(value = "View a list of Category with children")
    @PostMapping("/tree")
    public Map<Long, CategoryConversionUtil> findTree(@Valid @RequestBody CategoryFindRequest categoryFindRequest) {
        log.info("Request to find all categories {}", categoryFindRequest);
        return new CategoryJsonStructure().getJson(categoryService.findAll(categoryFindRequest));
    }

}
