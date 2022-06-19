package com.online.store.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductFindRequest {

    private String name;
    private Integer price;
    private String category;
    private String feature;
    private String parentCategory;
    private Integer pageNumber = 0;
    private Integer pageSize = 20;
    private String sortBy = "name";

}
