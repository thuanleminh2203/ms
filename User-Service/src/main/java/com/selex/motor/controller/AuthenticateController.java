package com.selex.motor.controller;

import com.selex.motor.payload.request.OauthRequest;
import com.selex.motor.payload.response.OauthResponse;
import com.selex.motor.service.OauthService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/oauth")
@AllArgsConstructor
public class AuthenticateController {
    private final OauthService oauthService;
    @PostMapping("/login")
    public OauthResponse login(@RequestBody OauthRequest request){
       return oauthService.login(request);
    }
}
