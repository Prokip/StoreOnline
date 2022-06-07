package com.online.store.service.impl;

import com.online.store.entity.ProductFile;
import com.online.store.repository.FileRepository;
import com.online.store.service.FileService;
import com.online.store.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

import static com.online.store.exception.FileStoreException.*;
import static com.online.store.exception.NotFoundException.notFoundException;

@Service
public class FileServiceImpl implements FileService {

    private static String pathFile = "resources\\files";

    private static final Path ROOT_FILES = Paths.get(pathFile);

    @Autowired
    private FileRepository fileRepository;


    @Override
    public void saveProductFile(MultipartFile file) {
        ProductFile productFile = new ProductFile();
        creatingFile(file, productFile);
        fileRepository.save(productFile);
    }

    @Override
    public Resource findFileById(Long id) {
        ProductFile productFile = getProductFileFromDB(id);
        return getResourceFile(productFile);
    }

    @Override
    public void deleteProductFile(Long id) {
        ProductFile productFile = getProductFileFromDB(id);
        productFile.getProducts().clear();
        fileRepository.delete(productFile);
    }

    @Override
    public ProductFile getProductFileFromDB(Long id) {
        return fileRepository
                .findById(id)
                .orElseThrow(() -> notFoundException(Constant.FILE + id));
    }

    private void creatingFile(MultipartFile file, ProductFile productFile) {
        try {
            Path path = ROOT_FILES.resolve(Objects.requireNonNull(file.getOriginalFilename()));
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            productFile.setPath(path.toAbsolutePath().toString());
        } catch (Exception e) {
            throw storeFileException(e.getMessage());
        }
    }

    private Resource getResourceFile(ProductFile productFile) {
        try {
            Path file = Paths.get(productFile.getPath());
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
