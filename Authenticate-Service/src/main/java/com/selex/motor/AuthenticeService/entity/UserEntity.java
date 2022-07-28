package com.selex.motor.AuthenticeService.entity;

import com.selex.motor.comon.entity.BaseEntity;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document("user")
public class UserEntity extends BaseEntity {
    @Id
    private String id;

    @Indexed
    private String username;

    private String password;

    private List<String> authorities;


}
