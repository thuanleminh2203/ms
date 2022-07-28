package com.selex.motor.AuthenticeService.payload.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class AuthRequest {
    @NotNull(message = "code is required")
    @Size(max = 255, min = 5 ,message = "username must be greater than 5 and less than 255 characters")
    private String username;

    @NotNull(message = "password is required")
    @Size(max = 255, min = 5 ,message = "password must be greater than 6 and less than 255 characters")
    private String password;
}
