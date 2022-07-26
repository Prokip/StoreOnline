package com.online.store.service.impl;

import com.online.store.dto.request.ProductDescriptionRequest;
import com.online.store.dto.response.ProductDescriptionResponse;
import com.online.store.entity.ProductDescription;
import com.online.store.repository.ProductDescriptionRepository;
import com.online.store.service.ProductDescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductDescriptionServiceImpl implements ProductDescriptionService {

    @Autowired
    ProductDescriptionRepository productDescriptionRepository;


    @Override
    public ProductDescriptionResponse createProductDescription(ProductDescriptionRequest productDescriptionRequest) {
        ProductDescription productDescription = getProductDescription(productDescriptionRequest);
        productDescriptionRepository.save(productDescription);
        return getProductDescriptionResponse(productDescription);
    }

    @Override
    public ProductDescriptionResponse modifyProductDescription(String id, ProductDescriptionRequest productDescriptionRequest) {
        ProductDescription productDescription = productDescriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("product description not found"));
        productDescription.setName(productDescriptionRequest.getName());
        if (productDescriptionRequest.getDescription() != null) {
            productDescription.setDescription(productDescriptionRequest.getDescription());
        }
        productDescriptionRepository.save(productDescription);
        return getProductDescriptionResponse(productDescription);
    }

    @Override
    public void deleteProductDescription(String id) {
        ProductDescription productDescription = productDescriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("product description not found"));
        productDescriptionRepository.delete(productDescription);
    }

    @Override
    public List<ProductDescriptionResponse> findAllProductDescription() {
        return productDescriptionRepository.findAll().stream()
                .map(this::getProductDescriptionResponse)
                .collect(Collectors.toList());
    }

    private ProductDescription getProductDescription(ProductDescriptionRequest productDescriptionRequest) {
        ProductDescription productDescription = new ProductDescription();
        productDescription.setName(productDescriptionRequest.getName());
        productDescription.setDescription(productDescriptionRequest.getDescription());
        return productDescription;
    }

    private ProductDescriptionResponse getProductDescriptionResponse(ProductDescription productDescription) {
        ProductDescriptionResponse productDescriptionResponse = new ProductDescriptionResponse();
        productDescriptionResponse.setId(productDescription.getId());
        productDescriptionResponse.setName(productDescription.getName());
        productDescriptionResponse.setDescription(productDescription.getDescription());
        return productDescriptionResponse;
    }
}
