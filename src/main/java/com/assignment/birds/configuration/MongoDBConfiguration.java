package com.assignment.birds.configuration;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Created by Karan Malhotra on 26/4/17.
 */
@Configuration
@EnableMongoRepositories(basePackages = "com.assignment.birds.repository")
public class MongoDBConfiguration extends AbstractMongoConfiguration {

    @Value("${birds.database.name:birds}")
    String databaseName;

    @Value("${birds.database.host:127.0.0.1}")
    String databaseHost;

    @Value("${birds.database.port}")
    int databasePort;

    @Override
    protected String getDatabaseName() {
        return databaseName;
    }

    @Override
    public Mongo mongo() throws Exception {
        return new MongoClient(databaseHost, databasePort);
    }
}
