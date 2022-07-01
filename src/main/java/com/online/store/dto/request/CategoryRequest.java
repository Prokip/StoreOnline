package com.online.store.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

@Getter
@Setter
public class CategoryRequest {

    @Size(max = 255)
    @NotBlank(message = "name must not be empty")
    @NotNull(message = "name of category is missing")
    private String name;

    @PositiveOrZero(message = "parentCategoryId cant be negative")
    private Long parentCategoryId;

}
