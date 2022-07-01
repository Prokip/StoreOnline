package com.online.store.service.impl;

import com.online.store.entity.ProductImage;
import com.online.store.repository.ImageRepository;
import com.online.store.service.ImageService;
import com.online.store.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import static com.online.store.exception.FileStoreException.*;
import static com.online.store.exception.NotFoundException.notFoundException;
import static com.online.store.util.Constant.IMAGE;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

@Service
public class ImageServiceImpl implements ImageService {

    private static String pathImage = "resources\\images";

    private static final Path ROOT_IMAGES = Paths.get(pathImage);

    @Autowired
    private ImageRepository imageRepository;


    @Override
    public void saveProductImages(MultipartFile file) {
        ProductImage productImage = new ProductImage();
        creatingImage(file, productImage);
        imageRepository.save(productImage);
    }

    @Override
    public Resource findImagesById(Long id) {
        ProductImage productImage = getProductImageFromDB(id);
        return getResourceImage(productImage);
    }

    @Override
    public void deleteProductImage(Long id) {
        ProductImage productImage = getProductImageFromDB(id);
        productImage.getProducts().clear();
        imageRepository.delete(productImage);
    }

    @Override
    public ProductImage getProductImageFromDB(Long id) {
        return imageRepository
                .findById(id)
                .orElseThrow(() -> notFoundException(IMAGE + id));
    }

    private void creatingImage(MultipartFile file, ProductImage image) {
        try {
            Path path = ROOT_IMAGES.resolve(Objects.requireNonNull(file.getOriginalFilename()));
            Files.copy(file.getInputStream(), path, REPLACE_EXISTING);
            image.setPath(path.toAbsolutePath().toString());
        } catch (Exception e) {
            throw storeFileException(e.getMessage());
        }
    }

    private Resource getResourceImage(ProductImage productImage) {
        try {
            Path file = Paths.get(productImage.getPath());
            return getResource(file);
        } catch (MalformedURLException e) {
            throw errorFileException(Constant.FILE + e.getMessage());
        }
    }

    private Resource getResource(Path file) throws MalformedURLException {
        Resource resource;
        resource = new UrlResource(file.toUri());
        if (!resource.exists() || !resource.isReadable()) {
            throw readFileException(Constant.FILE);
        }
        return resource;
    }

}
