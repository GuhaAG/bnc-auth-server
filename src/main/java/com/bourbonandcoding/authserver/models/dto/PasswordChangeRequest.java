package com.bourbonandcoding.authserver.models.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter
@Setter
public class PasswordChangeRequest {
    private String username;
    private String token;

    @Size(min = 8, max = 20, message = "Password should be between 8 and 20 characters")
    private String password;
}
