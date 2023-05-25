package com.moviebookingapp.registrationAndLogin.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;

@Configuration
@EnableMongoRepositories(basePackages = "com.moviebookingapp.registrationAndLogin.UserProfileRepository")
public class MongoConfig extends AbstractMongoClientConfiguration{
	
	@Override
    protected String getDatabaseName() {
        return "mydatabase";
    }

    @Override
    protected void configureClientSettings(MongoClientSettings.Builder builder) {
        builder.applyConnectionString(new ConnectionString("mongodb://localhost:27017"));
    }

}
