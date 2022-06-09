package com.selex.motor.service;

import com.selex.motor.payload.request.OauthRequest;
import com.selex.motor.payload.response.OauthResponse;
import com.selex.motor.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OauthServiceImpl implements OauthService{
    private final UserRepository userRepository;

    @Override
    public OauthResponse login(OauthRequest request) {
//        userRepository.
        return null;
    }
}
