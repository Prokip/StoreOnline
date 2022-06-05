package com.online.store.service;

import com.online.store.entity.ProductImage;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    void saveProductImages(MultipartFile file);

    Resource findImagesById(Long id);

    ProductImage getProductImageFromDB(Long id);

    void deleteProductImage(Long id);

}
