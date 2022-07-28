package com.selex.motor.AuthenticeService.repository;

import com.selex.motor.AuthenticeService.entity.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<UserEntity,String> {

    Optional<UserEntity> findByUsername(String username);
}
