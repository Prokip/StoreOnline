package com.online.store.service.impl;

import com.online.store.dto.request.CategoryRequest;
import com.online.store.dto.response.CategoryResponse;
import com.online.store.entity.Category;
import com.online.store.repository.CategoryRepository;
import com.online.store.repository.specification.CategorySpecification;
import com.online.store.service.CategoryService;
import com.online.store.util.Constant;
import com.online.store.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.online.store.exception.NotFoundException.notFoundException;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;


    @Override
    public CategoryResponse createCategory(CategoryRequest categoryRequest) {
        Category category = getCategory(new Category(), categoryRequest);
        categoryRepository.save(category);
        return getCategoryResponse(category);
    }

    @Override
    public CategoryResponse modifyCategory(Long id, CategoryRequest categoryRequest) {
        Category category = findByIdFromDB(id);
        getCategory(category, categoryRequest);
        categoryRepository.save(category);
        return getCategoryResponse(category);
    }

    @Override
    public void deleteCategory(Long id) {
        Category category = findByIdFromDB(id);
        category.getProductList().forEach(category::removeProduct);
        categoryRepository.delete(category);
    }

    @Override
    public CategoryResponse findById(Long id) {
        return getCategoryResponse(findByIdFromDB(id));
    }

    @Override
    public Category findByIdFromDB(Long id) {
        return ValidationUtil.isNull(id) ? null : categoryRepository.findById(id)
                .orElseThrow(() -> notFoundException(Constant.CATEGORY + id));
    }

    @Override
    public List<CategoryResponse> findAll(Integer pageNumber, Integer pageSize, String sortBy,
                                          String parentCategory, String name) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, sortBy));
        return categoryRepository.findAll(new CategorySpecification(parentCategory, name), pageRequest).toList().stream()
                .map(this::getCategoryResponse)
                .collect(Collectors.toList());
    }

    private boolean checkParentCategoryIfNull(CategoryRequest categoryRequest) {
        if (categoryRequest.getParentCategoryId() == null) {
            categoryRequest.setParentCategoryId(Constant.LONG_NULL);
            return false;
        }
        return true;
    }

    private Category getCategory(Category category, CategoryRequest categoryRequest) {
        category.setName(categoryRequest.getName());
        if (checkParentCategoryIfNull(categoryRequest)) {
            category.setParentCategory(findByIdFromDB(categoryRequest.getParentCategoryId()));
        }
        return category;
    }

    private CategoryResponse getCategoryResponse(Category category) {
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setId(category.getId());
        categoryResponse.setName(category.getName());
        setParentCategory(category, categoryResponse);
        return categoryResponse;
    }

    private void setParentCategory(Category category, CategoryResponse categoryResponse) {
        if (category.getParentCategory() != null) {
            categoryResponse.setParentCategoryId(category.getParentCategory().getId());
        } else {
            categoryResponse.setParentCategoryId(Constant.LONG_NULL);
        }
    }

}
