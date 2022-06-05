package com.online.store.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

@Getter
@Setter
public class OrderRequest {

    @Size(max = 100)
    @NotNull(message = "status is missing")
    private String status;

    @PositiveOrZero(message = "Value cant be negative")
    private Integer delivery;

}
