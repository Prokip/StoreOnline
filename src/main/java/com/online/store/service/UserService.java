package com.online.store.service;

import com.online.store.dto.request.UserRequest;
import com.online.store.dto.request.UserLoginRequest;
import com.online.store.dto.response.UserLoginResponse;
import com.online.store.dto.response.UserResponse;
import com.online.store.entity.Order;
import com.online.store.entity.User;

import java.util.List;

public interface UserService {

    UserResponse createUser(UserRequest userRequest);

    UserLoginResponse loginUser(UserLoginRequest userLoginRequest);

    List<UserResponse> findAllUsers(int pageNumber, int pageSize, String sortBy);

    User findUserByEmail(String email);

    UserResponse getUserById(Long id);

    UserResponse modifyUser(UserRequest userRequest, Long id);

    void deleteUser(Long id);

    void setOrderToUser(Long id, Order order);

}
