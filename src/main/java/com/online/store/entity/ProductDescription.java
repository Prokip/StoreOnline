package com.online.store.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@Setter
public class ProductDescription {

    @Id
    private String id;

    @Indexed(direction = IndexDirection.ASCENDING)
    private String name;

    private String description;


}
