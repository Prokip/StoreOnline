package com.online.store.service;

import com.online.store.dto.request.ProductRequest;
import com.online.store.dto.response.ProductResponse;
import com.online.store.entity.Category;
import com.online.store.entity.Product;

import java.util.List;

public interface ProductService {

    ProductResponse createProduct(ProductRequest productRequest);

    ProductResponse modifyProduct(ProductRequest productRequest, Long id);

    ProductResponse findProductById(Long id);

    Product findProductByIdFromDB(Long id);


    void deleteProduct(Long id);

    void putProductToFavourites(Long id, Long productId);

    void deleteProductFromFavourites(Long id, Long productId);

    List<ProductResponse> findProductByParam(Integer pageNumber, Integer pageSize, String sortBy, String category,
                                             String feature, String name, Integer price, String childCategory);

}
