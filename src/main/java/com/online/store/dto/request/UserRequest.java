package com.online.store.dto.request;

import com.online.store.entity.User;
import com.online.store.util.Constant;
import com.online.store.util.UserConversionUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
public class UserRequest {

    @Size(max = 200)
    @NotBlank(message = "firstName must be not empty")
    @NotNull(message = "First name is missing")
    private String firstName;

    @Size(max = 200)
    @NotBlank(message = "lastName must be not empty")
    @NotNull(message = "Last name is missing")
    private String lastName;

    @Size(max = 200)
    @NotNull(message = "Email is missing")
    @Email(message = "Email is not well formatted")
    private String email;

    @Size(max = 200)
    @Pattern(regexp = Constant.PHONE_REGEXP, message = "phone is not well formatted")
    private String phone;

    @NotNull(message = "Password is missing")
    @Size(min = 8, max = 16)
    @Pattern(regexp = Constant.PASSWORD_REGEXP, message = Constant.PASSWORD_MESSAGE)
    private String password;

    @Size(max = 200, message = "length must be up to 100 symbols")
    private String street = " ";

    @Size(max = 100, message = "length must be up to 50 symbols")
    private String city = " ";

    @Size(max = 3, message = "length must be 3 symbols")
    @Pattern(regexp = Constant.COUNTRY_REGEXP, message = "country code must be short form like USA")
    private String countryCode = "UA";

    @ApiModelProperty(hidden = true)
    public User convertToUser(User user) {
        return new UserConversionUtil().toUser(user, this);
    }

}
