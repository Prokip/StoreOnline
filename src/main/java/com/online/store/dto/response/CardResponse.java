package com.online.store.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardResponse {

    private Integer quantity;

    private Integer total;

    private Boolean isActive;

    private Long productId;

}
