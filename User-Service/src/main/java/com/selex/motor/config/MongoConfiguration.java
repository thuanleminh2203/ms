package com.selex.motor.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;

@Configuration
public class MongoConfiguration extends AbstractMongoClientConfiguration {
	private final MongoProperties mongoProperties;
	
	public MongoConfiguration(MongoProperties mongoProperties) {
		this.mongoProperties = mongoProperties;
	}
	
	@Override
	protected String getDatabaseName() {
		return mongoProperties.getDatabase();
	}

	@Override
	public MongoClient mongoClient() {
		return MongoClients.create(mongoProperties.getUri());
	}

	@Override
	protected boolean autoIndexCreation() {
		return mongoProperties.isAutoIndexCreation();
	}


//	@Bean
//	MongoTransactionManager transactionManager(MongoDatabaseFactory dbFactory) {
//		return new MongoTransactionManager(dbFactory);
//	}

}
