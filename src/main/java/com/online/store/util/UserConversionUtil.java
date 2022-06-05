package com.online.store.util;

import com.online.store.dto.request.UserRequest;
import com.online.store.dto.response.UserResponse;
import com.online.store.entity.User;

import java.util.stream.Collectors;

import static com.online.store.util.ValidationUtil.isNullOrEmpty;

public class UserConversionUtil {

    public static User toUser(User user, UserRequest userRequest) {
        if (user == null) {
            return null;
        }
        if (!isNullOrEmpty(userRequest.getFirstName())) {
            user.setFirstName(userRequest.getFirstName());
        }
        if (!isNullOrEmpty(userRequest.getLastName())) {
            user.setLastName(userRequest.getLastName());
        }
        if (!isNullOrEmpty(userRequest.getEmail())) {
            user.setEmail(userRequest.getEmail());
        }
        if (!isNullOrEmpty(userRequest.getPhone())) {
            user.setPhone(userRequest.getPhone());
        }
        if (!isNullOrEmpty(userRequest.getStreet())) {
            user.setStreet(userRequest.getStreet());
        }
        if (!isNullOrEmpty(userRequest.getCity())) {
            user.setCity(userRequest.getCity());
        }
        if (!isNullOrEmpty(userRequest.getCountryCode())) {
            user.setCountryCode(userRequest.getCountryCode());
        }
        return user;
    }

    public static UserResponse fromUserToUserResponse(User user) {
        if (user == null) {
            return new UserResponse();
        }
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setPhone(user.getPhone());
        userResponse.setStreet(user.getStreet());
        userResponse.setCity(user.getCity());
        userResponse.setCountryCode(user.getCountryCode());
        userResponse.setRoles(user.getRoles().stream()
                .map(roles -> roles.getRole().name())
                .collect(Collectors.toList()));
        return userResponse;
    }

}
