package com.online.store.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserLoginResponse {

    private Long id;

    private String token;

    private String username;

    private List<String> roles;

}
