package com.online.store.service;

import com.online.store.entity.ProductFile;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    void saveProductFile(MultipartFile file);

    Resource findFileById(Long id);

    ProductFile getProductFileFromDB(Long id);

    void deleteProductFile(Long id);

}
