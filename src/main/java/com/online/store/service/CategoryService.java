package com.online.store.service;

import com.online.store.dto.request.CategoryRequest;
import com.online.store.dto.response.CategoryResponse;
import com.online.store.entity.Category;

import java.util.List;

public interface CategoryService {

    CategoryResponse createCategory(CategoryRequest categoryRequest);

    CategoryResponse modifyCategory(Long id, CategoryRequest categoryRequest);

    void deleteCategory(Long id);

    CategoryResponse findById(Long id);

    Category findByIdFromDB(Long id);

    List<CategoryResponse> findAll(Integer pageNumber, Integer pageSize, String sortBy,
                                   String parentCategory, String name);
}
