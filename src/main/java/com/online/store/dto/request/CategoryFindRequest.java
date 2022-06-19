package com.online.store.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryFindRequest {

    private String name;
    private String parentCategory;
    private Integer pageNumber = 0;
    private Integer pageSize = 20;
    private String sortBy = "name";

}
