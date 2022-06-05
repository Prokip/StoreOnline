package com.online.store.service;

import com.online.store.dto.request.OrderRequest;
import com.online.store.dto.response.CardResponse;
import com.online.store.dto.response.OrderResponse;
import com.online.store.entity.Card;

import java.util.List;

public interface OrderService {

    OrderResponse createOrder(Long userId, List<Long> cardListId);

    OrderResponse modifyOrder(OrderRequest orderRequest, Long id);

    void deleteOrder(Long id);

    OrderResponse getOrderById(Long id);

}
