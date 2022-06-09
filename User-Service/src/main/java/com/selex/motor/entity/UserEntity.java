package com.selex.motor.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document("user")
public class UserEntity {

    @Id
    private String id;
    private String username;
    private String fullName;
    private String password;
    private String email;
    private List<String> roles;
}
