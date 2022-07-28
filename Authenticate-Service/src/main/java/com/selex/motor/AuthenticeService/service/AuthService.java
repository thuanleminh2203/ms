package com.selex.motor.AuthenticeService.service;

import com.selex.motor.AuthenticeService.payload.request.AuthRequest;
import com.selex.motor.AuthenticeService.payload.response.AuthResponse;
import com.selex.motor.comon.config.AuthInfo;

public interface AuthService {
    AuthResponse login(AuthRequest request);

    AuthResponse getInfo(AuthInfo info);
}
