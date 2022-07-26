package com.online.store.service;

import com.online.store.dto.request.ProductDescriptionRequest;
import com.online.store.dto.response.ProductDescriptionResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductDescriptionService {

    ProductDescriptionResponse createProductDescription(ProductDescriptionRequest productDescription);

    ProductDescriptionResponse modifyProductDescription(String id, ProductDescriptionRequest productDescription);

    void deleteProductDescription(String id);

    List<ProductDescriptionResponse> findAllProductDescription();

}
