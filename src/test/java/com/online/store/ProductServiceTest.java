package com.online.store;

import com.online.store.dto.request.ProductFindRequest;
import com.online.store.dto.request.ProductRequest;
import com.online.store.dto.response.ProductResponse;
import com.online.store.entity.Category;
import com.online.store.entity.Product;
import com.online.store.exception.NotFoundException;
import com.online.store.repository.ProductRepository;
import com.online.store.repository.specification.ProductSpecification;
import com.online.store.service.CategoryService;
import com.online.store.service.ProductService;
import com.online.store.service.UserService;
import com.online.store.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @InjectMocks
    ProductService productService = new ProductServiceImpl();

    @Mock
    private ProductRepository productRepository;
    @Mock
    private CategoryService categoryService;
    @Mock
    private UserService userService;

    private ProductRequest productRequest;
    private Product product;
    private Category category;
    private Long id;


    @BeforeEach
    public void setup() {
        id = 1L;
        category = new Category();
        category.setName("Level");
        category.setId(id);
        productRequest = new ProductRequest("Level", "12L123", true, 72000,
                68000, id);
        product = new Product("Level", "12L123", true, 72000,
                68000, category);
    }


    @DisplayName("JUnit test for save product successfully")
    @Test
    public void shouldSaveProductSuccessFully() {

        when(categoryService.findByIdFromDB(id)).thenReturn(category);

        ProductResponse savedProduct = productService.createProduct(productRequest);

        System.out.println(savedProduct.toString());
        assertThat(savedProduct).isNotNull();
        assertThat(savedProduct.getName()).isEqualTo("Level");
        assertThat(savedProduct.getPrice()).isEqualTo(68000);
    }

    @DisplayName("JUnit test for findProductById successfully")
    @Test
    public void shouldGiveProductSuccessFully() {

        product.setId(id);

        when(productRepository.findById(id)).thenReturn(Optional.of(product));

        ProductResponse savedProduct = productService.findProductById(product.getId());

        assertThat(savedProduct).isNotNull();
        assertThat(savedProduct.getId()).isEqualTo(id);
    }

    @DisplayName("JUnit test for findProductById which throws exception")
    @Test
    public void shouldThrowsExceptionWhenProductByIdNotFound() {

        when(productRepository.findById(id)).thenReturn(Optional.empty());
        Exception exception = assertThrows(NotFoundException.class, () -> productService.findProductByIdFromDB(id));

        String expectedMessage = "product" + id + " not found";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Disabled("Disabled until find way to test ProductSpecification")
    @DisplayName("JUnit test for findProductsByParam successfully")
    @Test
    public void shouldFindProductsByParamSuccessFully() {

        ProductFindRequest productFindRequest = new ProductFindRequest("Level", 0, 10, "name");

        Product product2 = new Product();
        product2.setId(15L);
        product2.setName("Level");
        product2.setPrice(45000);

        List<Product> products = new ArrayList<>();
        products.add(product);
        products.add(product2);

        PageRequest pageRequest = PageRequest.of(productFindRequest.getPageNumber(),
                productFindRequest.getPageSize(), Sort.by(Sort.Direction.ASC, productFindRequest.getSortBy()));

//        ProductSpecification productSpecification = mock(ProductSpecification.class);
        ProductSpecification productSpecification = new ProductSpecification(0,
                null, null, "Level", null);

        Page<Product> pagedProducts = new PageImpl(products);

        when(productRepository.findAll(productSpecification, pageRequest)).thenReturn(pagedProducts);

        List<ProductResponse> responseList = productService.findProductsByParam(productFindRequest);

        assertThat(responseList).isNotNull();
        assertThat(responseList.size()).isEqualTo(products.size());
    }

    @Disabled("Disabled until find way to test ProductSpecification")
    @DisplayName("JUnit test for findProductsByParam with empty list")
    @Test
    public void shouldGiveEmptyListWhenThereIsNoProductsByParam() {

        ProductFindRequest productFindRequest = new ProductFindRequest("Drill", 0, 10, "name");

        List<Product> products = new ArrayList<>();

        PageRequest pageRequest = PageRequest.of(productFindRequest.getPageNumber(),
                productFindRequest.getPageSize(), Sort.by(Sort.Direction.ASC, productFindRequest.getSortBy()));

//        ProductSpecification productSpecification = mock(ProductSpecification.class);
        ProductSpecification productSpecification = new ProductSpecification(0,
                null, null, "Level", null);

        Page<Product> pagedProducts = new PageImpl(products);

        when(productRepository.findAll(productSpecification, pageRequest)).thenReturn(pagedProducts);

        List<ProductResponse> responseList = productService.findProductsByParam(productFindRequest);

        assertThat(responseList).isEmpty();
    }

    @DisplayName("JUnit test for update product successfully")
    @Test
    public void shouldUpdateProductSuccessFully() {

        ProductRequest productRequest2 = new ProductRequest();
        productRequest2.setName("Saw");
        productRequest2.setPrice(86000);
        productRequest2.setCategoryId(category.getId());

        product.setId(id);
        product.setCategory(category);

        when(productRepository.findById(id)).thenReturn(Optional.of(product));
        when(categoryService.findByIdFromDB(id)).thenReturn(category);

        ProductResponse productResponse = productService.modifyProduct(productRequest2, id);

        assertThat(productResponse.getName()).isEqualTo("Saw");
        assertThat(productResponse.getPrice()).isEqualTo(86000);
    }

    @DisplayName("JUnit test for update product which throws exception")
    @Test
    public void shouldThrowsExceptionWhenUpdateAndProductIdNotFound() {

        ProductRequest productRequest2 = new ProductRequest();
        productRequest2.setName("Saw");
        productRequest2.setPrice(86000);

        product.setId(id);

        when(productRepository.findById(id)).thenReturn(Optional.empty());
        Exception exception = assertThrows(NotFoundException.class, () -> productService.modifyProduct(productRequest2, id));

        String expectedMessage = "product" + id + " not found";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));

        verify(productRepository, never()).save(any(Product.class));
    }

    @DisplayName("JUnit test for delete product successfully")
    @Test
    public void shouldDeleteProductSuccessFully() {
        when(productRepository.findById(id)).thenReturn(Optional.of(product));

        productService.deleteProduct(id);

        verify(productRepository, times(1)).delete(product);
    }

    @DisplayName("JUnit test for delete product which throws exception")
    @Test
    public void shouldThrowsExceptionWhenDeleteProduct() {

        when(productRepository.findById(id)).thenReturn(Optional.empty());
        Exception exception = assertThrows(NotFoundException.class, () -> productService.deleteProduct(id));

        String expectedMessage = "product" + id + " not found";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));

        verify(productRepository, never()).delete(any(Product.class));
    }

}
