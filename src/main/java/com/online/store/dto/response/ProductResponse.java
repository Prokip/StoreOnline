package com.online.store.dto.response;


import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ProductResponse {

    private Long id;
    private String name;
    private String codeUnit;
    private Boolean isActive;
    private Integer maxPrice;
    private Integer price;
    private String description;
    private Long categoryId;
    private List<Long> featureKeysId = new ArrayList<>();
    private List<Long> images = new ArrayList<>();
    private List<Long> files = new ArrayList<>();

}
