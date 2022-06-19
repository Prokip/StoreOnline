package com.online.store.service.impl;

import com.online.store.dto.request.ProductFindRequest;
import com.online.store.dto.request.ProductRequest;
import com.online.store.dto.response.ProductResponse;
import com.online.store.entity.FeatureKey;
import com.online.store.entity.Product;
import com.online.store.entity.ProductFile;
import com.online.store.entity.ProductImage;
import com.online.store.repository.ProductRepository;
import com.online.store.repository.specification.ProductSpecification;
import com.online.store.service.*;
import com.online.store.util.Constant;
import com.online.store.util.ProductConversionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.online.store.exception.NotFoundException.notFoundException;
import static com.online.store.util.ValidationUtil.isNull;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private FeatureService featureService;
    @Autowired
    private ImageService imageService;
    @Autowired
    private FileService fileService;


    @Transactional
    @Override
    public ProductResponse createProduct(ProductRequest productRequest) {
        productRequest.putProduct(new Product());
        productRequest.compareProduct();
        Product product = productRequest.getProduct();
        setFieldsToProduct(productRequest, product);
        productRepository.save(product);
        return new ProductConversionUtil().fromProduct(product);
    }

    @Transactional
    @Override
    public ProductResponse modifyProduct(ProductRequest productRequest, Long id) {
        Product product = findProductByIdFromDB(id);
        productRequest.putProduct(product);
        productRequest.compareProduct();
        setFieldsToProduct(productRequest, product);
        productRepository.save(product);
        return new ProductConversionUtil().fromProduct(product);
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = findProductByIdFromDB(id);
        product.getCards().forEach(product::removeCard);
        productRepository.delete(product);
    }

    @Override
    public ProductResponse findProductById(Long id) {
        return new ProductConversionUtil().fromProduct(findProductByIdFromDB(id));
    }

    @Override
    public Product findProductByIdFromDB(Long id) {
        return productRepository
                .findById(id)
                .orElseThrow(() -> notFoundException(Constant.PRODUCT + id));
    }

    @Override
    public void putProductToFavourites(Long productId, Long id) {
        Product product = findProductByIdFromDB(productId);
        userService.getUserByIdFromDB(id).addFavouriteProduct(product);
    }

    @Override
    public void deleteProductFromFavourites(Long productId, Long id) {
        Product product = findProductByIdFromDB(productId);
        userService.getUserByIdFromDB(id).removeFavouriteProduct(product);
    }

    @Override
    public List<ProductResponse> findProductByParam(ProductFindRequest productFindRequest) {
        PageRequest pageRequest = PageRequest.of(productFindRequest.getPageNumber(),
                productFindRequest.getPageSize(), Sort.by(Sort.Direction.ASC, productFindRequest.getSortBy()));
        ProductSpecification productSpecification = new ProductSpecification(productFindRequest.getPrice(),
                productFindRequest.getCategory(), productFindRequest.getFeature(),
                productFindRequest.getName(), productFindRequest.getParentCategory());
        List<Product> productList = productRepository.findAll(productSpecification, pageRequest).toList();
        return getProductResponses(productList);
    }

    private List<ProductResponse> getProductResponses(List<Product> productList) {
        return productList.stream()
                .map(product -> new ProductConversionUtil().fromProduct(product))
                .collect(Collectors.toList());
    }

    private void setCategoryToProduct(ProductRequest productRequest, Product product) {
        if (!isNull(productRequest.getCategoryId())) {
            categoryService.findByIdFromDB(productRequest.getCategoryId()).addProduct(product);
        }
    }

    private void setImageToProduct(ProductRequest productRequest, Product product) {
        if (!productRequest.getImages().isEmpty()) {
            List<ProductImage> productImages = productRequest.getImages().stream()
                    .map(imageService::getProductImageFromDB)
                    .collect(Collectors.toList());
            if (!product.getProductImages().isEmpty()) {
                productImages.forEach(product::removeImage);
            }
            productImages.forEach(product::addImage);
        }
    }

    private void setFileToProduct(ProductRequest productRequest, Product product) {
        if (!productRequest.getFiles().isEmpty()) {
            List<ProductFile> productFiles = productRequest.getFiles().stream()
                    .map(fileService::getProductFileFromDB)
                    .collect(Collectors.toList());
            if (!product.getProductImages().isEmpty()) {
                productFiles.forEach(product::removeFile);
            }
            productFiles.forEach(product::addFile);
        }
    }

    public void setFeatureToProduct(ProductRequest productRequest, Product product) {
        if (!productRequest.getFeatureKeysId().isEmpty()) {
            List<FeatureKey> featureKeys = productRequest.getFeatureKeysId().stream()
                    .map(featureService::getFeatureKeyByIdFromDB)
                    .collect(Collectors.toList());
            if (!product.getFeatureKeys().isEmpty()) {
                featureKeys.forEach(product::removeFeatureKey);
            }
            featureKeys.forEach(product::addFeatureKey);
        }
    }

    private void setFieldsToProduct(ProductRequest productRequest, Product product) {
        setCategoryToProduct(productRequest, product);
        setFeatureToProduct(productRequest, product);
        setImageToProduct(productRequest, product);
        setFileToProduct(productRequest, product);
        product.setCreateDate(LocalDateTime.now());
    }

}
