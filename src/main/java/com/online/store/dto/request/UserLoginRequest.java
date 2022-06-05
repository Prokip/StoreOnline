package com.online.store.dto.request;

import com.online.store.util.Constant;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UserLoginRequest {

    @NotNull(message = "Password is missing")
    @Size(min = 8, max = 16, message = "Not correct size of password")
    @Pattern(regexp = Constant.PASSWORD_REGEXP, message = Constant.PASSWORD_MESSAGE)
    private String password;

    @Size(max = 150)
    @NotNull(message = "Email is missing")
    private String email;

}
