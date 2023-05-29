package com.tutorial.userservice.vo;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class RequestLogin {
    @NotNull(message = "Email cannot be null")
    @Size(min = 2, message = "Email not be less than two chararcters")
    @Email
    private String email;

    @NotNull(message = "Password cannot be null")
    @Size(min = 2, message = "Password must be equals or greater than 8 chararcters")
    private String password;
}
