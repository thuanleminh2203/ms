package com.selex.motor.service;

import com.selex.motor.comon.config.UserInfo;
import com.selex.motor.entity.UserEntity;
import com.selex.motor.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;


    @Override
    @SneakyThrows
    public UserDetails loadUserByUsername(String username) {

        UserEntity entity = userRepository.findByUsername(username).orElseThrow();
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername(entity.getUsername());
        List<GrantedAuthority> authorities = entity.getRoles().stream().map(k -> new SimpleGrantedAuthority(k)).collect(Collectors.toList());
        userInfo.setAuthorities(authorities);

        return userInfo;
    }


}
