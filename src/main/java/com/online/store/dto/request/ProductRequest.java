package com.online.store.dto.request;

import com.online.store.entity.Product;
import com.online.store.util.ProductConversionUtil;
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

    @Size(max = 150)
    @NotBlank(message = "name must be not empty")
    @NotNull(message = "name is missing")
    private String name;

    @Size(max = 150)
    @NotBlank(message = "codeUnit must be not empty")
    @NotNull(message = "codeUnit is missing")
    private String codeUnit;

    @NotNull(message = "isActive is missing")
    private Boolean isActive;

    @NotNull(message = "maxPrice is missing")
    @Positive
    private Integer maxPrice;

    @NotNull(message = "price is missing")
    @Positive
    private Integer price;

    @Size(max = 250)
    private String description = "";

    @NotNull(message = "categoryId is missing")
    @Positive
    private Long categoryId;

    private List<Long> featureKeysId = new ArrayList<>();

    private List<Long> images = new ArrayList<>();

    private List<Long> files = new ArrayList<>();

    @Transient
    private Product product;


    public void putProduct(Product product) {
        this.product = product;
    }

    public void compareProduct() {
        if (product == null) {
            return;
        }
        new ProductConversionUtil().toProduct(product, this);
    }


}
