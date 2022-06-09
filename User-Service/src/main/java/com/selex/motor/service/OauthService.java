package com.selex.motor.service;

import com.selex.motor.payload.request.OauthRequest;
import com.selex.motor.payload.response.OauthResponse;

public interface OauthService {
    OauthResponse login(OauthRequest request);
}
