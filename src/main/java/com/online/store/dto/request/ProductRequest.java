package com.online.store.dto.request;

import com.online.store.entity.Product;
import com.online.store.util.ProductConversionUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
public class ProductRequest {

    @ApiModelProperty(notes = "The name of the product")
    @Size(max = 150)
    @NotBlank(message = "name must be not empty")
    @NotNull(message = "name is missing")
    private String name;

    @ApiModelProperty(notes = "The code/article of the product")
    @Size(max = 100)
    @NotBlank(message = "codeUnit must be not empty")
    @NotNull(message = "codeUnit is missing")
    private String codeUnit;

    @NotNull(message = "isActive is missing")
    private Boolean isActive;

    @ApiModelProperty(notes = "The full price of the product")
    @NotNull(message = "maxPrice is missing")
    @Positive
    private Integer maxPrice;

    @ApiModelProperty(notes = "The price of the product with sale")
    @NotNull(message = "price is missing")
    @Positive
    private Integer price;

    @ApiModelProperty(notes = "The product description")
    @Size(max = 255)
    private String description = "";

    @ApiModelProperty(notes = "CategoryId of product")
    @NotNull(message = "categoryId is missing")
    @Positive
    private Long categoryId;

    private List<Long> featureKeysId = new ArrayList<>();

    private List<Long> images = new ArrayList<>();

    private List<Long> files = new ArrayList<>();

    @ApiModelProperty(hidden = true)
    @Transient
    private Product product;


    public ProductRequest() {
    }

    public ProductRequest(String name, String codeUnit, Boolean isActive, Integer maxPrice,
                          Integer price, Long categoryId) {
        this.name = name;
        this.codeUnit = codeUnit;
        this.isActive = isActive;
        this.maxPrice = maxPrice;
        this.price = price;
        this.categoryId = categoryId;
    }


    public void putProduct(Product product) {
        this.product = product;
    }

    public Product compareProduct() {
        return new ProductConversionUtil().toProduct(product, this);
    }


}
