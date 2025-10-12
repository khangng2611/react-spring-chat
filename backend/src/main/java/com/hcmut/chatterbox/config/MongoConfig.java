package com.hcmut.chatterbox.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@Configuration
@EnableMongoAuditing
public class MongoConfig {
    // The @EnableMongoAuditing annotation enables automatic date auditing
}
