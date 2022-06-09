package com.selex.motor;

import com.selex.motor.config.MongoProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication(
		scanBasePackages = {"com.selex.motor"},
		exclude = {EmbeddedMongoAutoConfiguration.class, DataSourceAutoConfiguration.class})
@EnableMongoAuditing
@EnableConfigurationProperties(value = {MongoProperties.class})
public class UserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

}
