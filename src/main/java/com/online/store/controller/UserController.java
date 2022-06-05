package com.online.store.controller;

import com.online.store.dto.request.CardRequest;
import com.online.store.dto.request.OrderRequest;
import com.online.store.dto.request.UserLoginRequest;
import com.online.store.dto.request.UserRequest;
import com.online.store.dto.response.CardResponse;
import com.online.store.dto.response.OrderResponse;
import com.online.store.dto.response.UserLoginResponse;
import com.online.store.dto.response.UserResponse;
import com.online.store.service.CardService;
import com.online.store.service.OrderService;
import com.online.store.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final OrderService orderService;
    private final CardService cardService;

    @Autowired
    public UserController(UserService userService, OrderService orderService, CardService cardService) {
        this.userService = userService;
        this.orderService = orderService;
        this.cardService = cardService;
    }


    @PostMapping("/register")
    public ResponseEntity<UserResponse> registryUser(@Valid @RequestBody UserRequest userRequest) {
        log.info("Request to register {}", userRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.createUser(userRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> loginUser(@Valid @RequestBody UserLoginRequest userLoginRequest) {
        log.info("Request to login {}", userLoginRequest);
        return ResponseEntity
                .ok()
                .body(userService.loginUser(userLoginRequest));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public List<UserResponse> getAllUsers(@RequestParam(required = false, defaultValue = "0") Integer pageNumber,
                                          @RequestParam(required = false, defaultValue = "20") Integer pageSize,
                                          @RequestParam(required = false, defaultValue = "firstName") String sortBy) {
        log.info("Request to get all users {}", sortBy);
        return userService.findAllUsers(pageNumber, pageSize, sortBy);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        log.info("Request to get user by id {}", id);
        return ResponseEntity
                .ok()
                .body(userService.getUserById(id));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> modifyUser(@RequestBody UserRequest userRequest,
                                                   @PathVariable Long id) {
        log.info("Request to modify user {}", userRequest);
        return ResponseEntity
                .ok()
                .body(userService.modifyUser(userRequest, id));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable Long id) {
        log.info("Request to delete user {}", id);
        userService.deleteUser(id);
    }

    @PostMapping("/{userId}/orders")
    public ResponseEntity<OrderResponse> createOrder(@PathVariable Long userId, List<Long> cardListId) {
        log.info("Request to create order {}", userId);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(orderService.createOrder(userId, cardListId));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/orders/{id}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long id) {
        log.info("Request to get order {}", id);
        return ResponseEntity
                .ok()
                .body(orderService.getOrderById(id));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/orders/{id}")
    public ResponseEntity<OrderResponse> modifyOrder(@Valid @RequestBody OrderRequest orderRequest,
                                                     @PathVariable Long id) {
        log.info("Request to modify order {}", orderRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(orderService.modifyOrder(orderRequest, id));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/orders/{id}")
    public void deleteOrder(@PathVariable Long id) {
        log.info("Request to delete order {}", id);
        orderService.deleteOrder(id);
    }

    @PostMapping("/cards")
    public CardResponse createCard(@Valid @RequestBody CardRequest cardRequest) {
        log.info("Request to add card {}", cardRequest);
        return cardService.createCard(cardRequest);
    }

    @PutMapping("/cards/{id}")
    public CardResponse modifyCard(@PathVariable Long id, @RequestParam Integer quantity) {
        log.info("Request to modify card {}", id);
        return cardService.modifyCard(id, quantity);
    }

    @DeleteMapping("/cards/{id}")
    public void deleteCard(@PathVariable Long id) {
        log.info("Request to delete card {}", id);
        cardService.deleteCard(id);
    }

}
