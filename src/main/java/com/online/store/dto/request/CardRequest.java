package com.online.store.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
public class CardRequest {

    @Positive
    private Integer quantity;

    @NotNull
    private Long productId;

}
