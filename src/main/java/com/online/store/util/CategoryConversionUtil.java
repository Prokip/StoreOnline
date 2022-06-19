package com.online.store.util;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CategoryConversionUtil {

    private String nodeName;
    private List<CategoryConversionUtil> children = new ArrayList<>();

    public CategoryConversionUtil(String nodeName) {
        this.nodeName = nodeName;
    }

    public void addChild(CategoryConversionUtil categoryConversionUtil) {
        this.children.add(categoryConversionUtil);
    }

}