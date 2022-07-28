package com.selex.motor.gateway.DTO;

import com.selex.motor.comon.constant.AppConstants;
import lombok.Data;

import java.util.List;

@Data
public class AuthDTO {
    private String username;
    private String fullName;
    private String token;
    private final String tokenType = AppConstants.BEARER_TOKEN_TYPE;
    private List<String> authorities;
}
