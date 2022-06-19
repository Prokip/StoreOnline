package com.online.store.util;

import com.online.store.dto.request.ProductRequest;
import com.online.store.dto.response.ProductResponse;
import com.online.store.entity.IdHolder;
import com.online.store.entity.Product;

import java.util.ArrayList;
import java.util.stream.Collectors;

import static com.online.store.util.ValidationUtil.isNull;
import static com.online.store.util.ValidationUtil.isNullOrEmpty;

public class ProductConversionUtil {

    public Product toProduct(Product product, ProductRequest productRequest) {
        if (product == null) {
            return null;
        }
        if (!isNullOrEmpty(productRequest.getName())) {
            product.setName(productRequest.getName());
        }
        if (!isNullOrEmpty(productRequest.getCodeUnit())) {
            product.setCodeUnit(productRequest.getCodeUnit());
        }
        if (!isNull(productRequest.getIsActive())) {
            product.setIsActive(productRequest.getIsActive());
        }
        if (!isNull(productRequest.getPrice())) {
            product.setPrice(productRequest.getPrice());
        }
        if (!isNull(productRequest.getMaxPrice())) {
            product.setMaxPrice(productRequest.getMaxPrice());
        }
        if (!isNullOrEmpty(productRequest.getDescription())) {
            product.setDescription(productRequest.getDescription());
        }
        return product;
    }

    public ProductResponse fromProduct(Product product) {
        if (product == null) {
            return new ProductResponse();
        }
        ProductResponse productResponse = new ProductResponse();
        productResponse.setId(product.getId());
        productResponse.setName(product.getName());
        productResponse.setCodeUnit(product.getCodeUnit());
        productResponse.setPrice(product.getPrice());
        productResponse.setMaxPrice(product.getMaxPrice());
        productResponse.setDescription(product.getDescription());
        productResponse.setIsActive(product.getIsActive());
        if (product.getCategory() != null) {
            productResponse.setCategoryId(product.getCategory().getId());
        } else {
            productResponse.setCategoryId(Constant.LONG_NULL);
        }
        if (!product.getFeatureKeys().isEmpty()) {
            productResponse.setFeatureKeysId(product.getFeatureKeys().stream()
                    .map(IdHolder::getId)
                    .collect(Collectors.toList()));
        } else {
            productResponse.setFeatureKeysId(new ArrayList<>());
        }
        if (!product.getProductImages().isEmpty()) {
            productResponse.setImages(product.getProductImages().stream()
                    .map(IdHolder::getId)
                    .collect(Collectors.toList()));
        } else {
            productResponse.setImages(new ArrayList<>());
        }
        if (!product.getProductFiles().isEmpty()) {
            productResponse.setFiles(product.getProductFiles().stream()
                    .map(IdHolder::getId)
                    .collect(Collectors.toList()));
        } else {
            productResponse.setFiles(new ArrayList<>());
        }
        return productResponse;
    }

}
