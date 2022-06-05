package com.online.store.dto.request;

import com.online.store.entity.IdHolder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class FeatureRequest extends IdHolder {

    @NotBlank(message = "name must not be empty")
    @NotNull(message = "Name is missing")
    @Size(max = 250)
    private String name;

}
