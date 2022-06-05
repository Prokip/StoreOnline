package com.online.store.service.impl;

import com.online.store.dto.request.OrderRequest;
import com.online.store.dto.response.OrderResponse;
import com.online.store.entity.Order;
import com.online.store.entity.enums.Status;
import com.online.store.repository.OrderRepository;
import com.online.store.service.OrderService;
import com.online.store.util.Constant;
import com.online.store.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.online.store.entity.enums.Status.PENDING;
import static com.online.store.exception.NotFoundException.notFoundException;
import static com.online.store.util.ValidationUtil.isNullOrEmpty;

@Service
public class OrderServiceImpl implements OrderService {

    public static final String SIMPLE_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String NUMBER_OF_ORDER = "%09d";
    public static final int DELIVERY_DEFAULT_VALUE = 0;

    private final OrderRepository orderRepository;

    private final UserServiceImpl userService;

    private final CardServiceImpl cardService;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, UserServiceImpl userService, CardServiceImpl cardService) {
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.cardService = cardService;
    }


    @Transactional
    @Override
    public OrderResponse createOrder(Long userId, List<Long> cardListId) {
        Order order = getOrder(cardListId);
        orderRepository.save(order);
        userService.setOrderToUser(userId, order);
        return fromOrder(order, new OrderResponse());
    }

    @Override
    public OrderResponse modifyOrder(OrderRequest orderRequest, Long id) {
        Order order = getOrderFromDB(id);
        toOrder(order, orderRequest);
        orderRepository.save(order);
        return fromOrder(order, new OrderResponse());
    }

    @Override
    public void deleteOrder(Long id) {
        Order order = getOrderFromDB(id);
        order.getCards().forEach(order::removeCard);
        order.getUser().removeOrder(order);
        orderRepository.delete(order);
    }

    @Override
    public OrderResponse getOrderById(Long id) {
        return fromOrder(getOrderFromDB(id), new OrderResponse());
    }

    private void setDefaultInOrder(Order order) {
        order.setCreateDate(new SimpleDateFormat(SIMPLE_DATE_FORMAT).format(new Date()));
        order.setStatus(PENDING);
        order.setDelivery(DELIVERY_DEFAULT_VALUE);
        orderRepository.save(order);
        order.setNumberOrder(String.format(NUMBER_OF_ORDER, order.getId()));
    }

    private Order getOrderFromDB(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> notFoundException(Constant.ORDER + id));
    }

    private void toOrder(Order order, OrderRequest orderRequest) {
        if (!isNullOrEmpty(orderRequest.getStatus())) {
            order.setStatus(Status.valueOf(orderRequest.getStatus().toUpperCase()));
        }
        if (!ValidationUtil.isNull(orderRequest.getDelivery())) {
            order.setDelivery(orderRequest.getDelivery());
        }
    }

    private OrderResponse fromOrder(Order order, OrderResponse orderResponse) {
        if (order == null) {
            return null;
        }
        orderResponse.setStatus(order.getStatus().toString());
        orderResponse.setDelivery(order.getDelivery());
        orderResponse.setId(order.getId());
        return orderResponse;
    }

    private Order getOrder(List<Long> cardListId) {
        Order order = new Order();
        setDefaultInOrder(order);
        cardService.findAllCards(cardListId).forEach(order::addCard);
        return order;
    }


}
