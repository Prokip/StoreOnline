package com.online.store.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderResponse {

    private Long id;

    private String status;

    private Integer delivery;

}
