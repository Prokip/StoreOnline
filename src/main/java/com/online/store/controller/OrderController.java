package com.online.store.controller;

import com.online.store.dto.request.CardRequest;
import com.online.store.dto.request.OrderRequest;
import com.online.store.dto.response.CardResponse;
import com.online.store.dto.response.OrderResponse;
import com.online.store.service.CardService;
import com.online.store.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Api("REST APIs related to Order and Card Entities")
@RestController
@RequestMapping("/users")
public class OrderController {


    @Autowired
    private OrderService orderService;
    @Autowired
    private CardService cardService;


    @ApiOperation(value = "Creates a new order in the system")
    @PostMapping("/{userId}/orders")
    public ResponseEntity<OrderResponse> createOrder(@PathVariable Long userId, List<Long> cardListId) {
        log.info("Request to create order {}", userId);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(orderService.createOrder(userId, cardListId));
    }

    @ApiOperation(value = "View order by id from the system")
    @ApiImplicitParam(
            name = "id",
            value = "Id order in db",
            required = true,
            dataType = "Long",
            paramType = "path")
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/orders/{id}")
    public ResponseEntity<OrderResponse> findOrderById(@PathVariable Long id) {
        log.info("Request to get order {}", id);
        return ResponseEntity
                .ok()
                .body(orderService.getOrderById(id));
    }

    @ApiOperation(value = "Update existing order in the system")
    @ApiImplicitParam(
            name = "id",
            value = "Id order in db",
            required = true,
            dataType = "Long",
            paramType = "path")
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/orders/{id}")
    public ResponseEntity<OrderResponse> modifyOrder(@Valid @RequestBody OrderRequest orderRequest,
                                                     @PathVariable Long id) {
        log.info("Request to modify order {}", orderRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(orderService.modifyOrder(orderRequest, id));
    }

    @ApiOperation(value = "Delete order from the system by id")
    @ApiImplicitParam(
            name = "id",
            value = "Id order in db",
            required = true,
            dataType = "Long",
            paramType = "path")
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/orders/{id}")
    public void deleteOrder(@PathVariable Long id) {
        log.info("Request to delete order {}", id);
        orderService.deleteOrder(id);
    }

    @ApiOperation(value = "Creates a new card in the system")
    @PostMapping("/cards")
    public CardResponse createCard(@Valid @RequestBody CardRequest cardRequest) {
        log.info("Request to add card {}", cardRequest);
        return cardService.createCard(cardRequest);
    }

    @ApiOperation(value = "Update existing card in the system")
    @PutMapping("/cards/{id}")
    public CardResponse modifyCard(@PathVariable Long id, @RequestParam Integer quantity) {
        log.info("Request to modify card {}", id);
        return cardService.modifyCard(id, quantity);
    }

    @ApiOperation(value = "Delete card from the system by id")
    @DeleteMapping("/cards/{id}")
    public void deleteCard(@PathVariable Long id) {
        log.info("Request to delete card {}", id);
        cardService.deleteCard(id);
    }

}
