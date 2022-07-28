package com.selex.motor.AuthenticeService.controller;

import com.selex.motor.AuthenticeService.payload.request.AuthRequest;
import com.selex.motor.AuthenticeService.payload.response.AuthResponse;
import com.selex.motor.AuthenticeService.service.AuthService;
import com.selex.motor.comon.config.AuthInfo;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class AuthController {

    private final AuthService oauthService;

    @PostMapping(value = "/login")
    public AuthResponse login(@RequestBody @Valid AuthRequest request) {
        return oauthService.login(request);
    }


    @GetMapping("/info")
    public AuthResponse getInfo(@AuthenticationPrincipal AuthInfo authInfo) {
        return oauthService.getInfo(authInfo);
    }


}
