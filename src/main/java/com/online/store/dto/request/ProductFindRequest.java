package com.online.store.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductFindRequest {

    @ApiModelProperty(notes = "The name of the product")
    private String name;
    @ApiModelProperty(notes = "The price of the product with sale")
    private Integer price;
    @ApiModelProperty(notes = "CategoryId of product")
    private String category;
    @ApiModelProperty(notes = "Feature of product")
    private String feature;
    @ApiModelProperty(notes = "Main category of product")
    private String parentCategory;
    private Integer pageNumber = 0;
    private Integer pageSize = 20;
    private String sortBy = "name";

}
