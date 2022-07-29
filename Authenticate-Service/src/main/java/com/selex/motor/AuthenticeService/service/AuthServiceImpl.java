package com.selex.motor.AuthenticeService.service;

import com.selex.motor.AuthenticeService.payload.request.AuthRequest;
import com.selex.motor.AuthenticeService.payload.response.AuthResponse;
import com.selex.motor.comon.config.AuthInfo;
import com.selex.motor.comon.constant.AppConstants;
import com.selex.motor.comon.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final RedisTemplate<String, Object> redisTemplate;
    @Value("${jwt.time-token}")
    private long expireTime;


    @Override
    @SneakyThrows
    public AuthResponse login(AuthRequest request) {
        var user = (AuthInfo) userDetailsService.loadUserByUsername(request.getUsername().toLowerCase());
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword()))
            throw new BadRequestException("{account.valid}");

        String token = UUID.randomUUID().toString();

        redisTemplate.opsForValue().set(token, user, expireTime, TimeUnit.SECONDS);
        return AuthResponse
                .builder()
                .token(AppConstants.BEARER_TOKEN_TYPE + token)
                .fullName(user.getFullName())
                .username(user.getUsername())
                .authorities(user.getAuthorities().stream().map(String::valueOf).collect(Collectors.toList())).build();
    }

    @Override
    @SneakyThrows
    public AuthResponse getInfo(AuthInfo authInfo) {

        return AuthResponse
                .builder()
                .fullName(authInfo.getFullName())
                .username(authInfo.getUsername())
                .authorities(authInfo.getAuthorities().stream().map(String::valueOf).collect(Collectors.toList())).build();
    }


}
