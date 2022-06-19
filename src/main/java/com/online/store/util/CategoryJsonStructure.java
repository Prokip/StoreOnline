package com.online.store.util;

import com.online.store.dto.response.CategoryResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryJsonStructure {

    public static final Long KEY = -1L;
    public static final String ROOT = "root";

    public Map<Long, CategoryConversionUtil> getJson(List<CategoryResponse> categories) {
        Map<Long, CategoryConversionUtil> map = new HashMap<>();
        map.put(KEY, new CategoryConversionUtil(ROOT));
        categories.forEach(categoryResponse -> map.put(categoryResponse.getId(),
                new CategoryConversionUtil(categoryResponse.getName())));
        buildJsonCategoryStructure(categories, map);
        map.remove(KEY);
        return map;
    }

    private void buildJsonCategoryStructure(List<CategoryResponse> categories, Map<Long, CategoryConversionUtil> map) {
        for (CategoryResponse categoryResponse : categories) {
            CategoryConversionUtil categoryConversion = map.get(categoryResponse.getParentCategoryId());
            if (categoryConversion != null) {
                categoryConversion.addChild(map.get(categoryResponse.getId()));
            }
        }
    }

}
