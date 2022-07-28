package com.selex.motor.AuthenticeService.service;

import com.selex.motor.AuthenticeService.entity.UserEntity;
import com.selex.motor.AuthenticeService.repository.UserRepository;
import com.selex.motor.comon.config.AuthInfo;
import com.selex.motor.comon.exception.BadRequestException;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) {

        UserEntity user = getUserByUsername(username);

        AuthInfo authInfo = new AuthInfo();
        authInfo.setUsername(user.getUsername());
        authInfo.setAuthorities(user.getAuthorities().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
        authInfo.setPassword(user.getPassword());
        return authInfo;
    }

    @SneakyThrows
    private UserEntity getUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new BadRequestException("${account.valid}"));
    }

}
