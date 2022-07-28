package com.selex.motor.AuthenticeService.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.selex.motor.comon.constant.AppConstants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class AuthResponse {
    private String username;
    private String fullName;
    private String token;
    private final String tokenType = AppConstants.BEARER_TOKEN_TYPE;
    private List<String> authorities;
}

