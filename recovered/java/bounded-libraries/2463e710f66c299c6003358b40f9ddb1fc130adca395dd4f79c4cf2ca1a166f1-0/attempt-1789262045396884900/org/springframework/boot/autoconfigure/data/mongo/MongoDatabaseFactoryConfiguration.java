/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mongodb.client.MongoClient
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.data.mongodb.MongoDatabaseFactory
 *  org.springframework.data.mongodb.core.MongoDatabaseFactorySupport
 *  org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory
 */
package org.springframework.boot.autoconfigure.data.mongo;

import com.mongodb.client.MongoClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoDatabaseFactorySupport;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

@Configuration(proxyBeanMethods=false)
@ConditionalOnMissingBean(value={MongoDatabaseFactory.class})
@ConditionalOnSingleCandidate(value=MongoClient.class)
class MongoDatabaseFactoryConfiguration {
    MongoDatabaseFactoryConfiguration() {
    }

    @Bean
    MongoDatabaseFactorySupport<?> mongoDatabaseFactory(MongoClient mongoClient, MongoProperties properties) {
        return new SimpleMongoClientDatabaseFactory(mongoClient, properties.getMongoClientDatabase());
    }
}

