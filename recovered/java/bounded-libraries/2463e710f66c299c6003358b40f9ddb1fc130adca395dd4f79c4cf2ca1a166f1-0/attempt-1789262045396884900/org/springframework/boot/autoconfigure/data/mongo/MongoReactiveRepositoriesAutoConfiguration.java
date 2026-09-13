/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mongodb.reactivestreams.client.MongoClient
 *  org.springframework.context.annotation.Import
 *  org.springframework.data.mongodb.repository.ReactiveMongoRepository
 *  org.springframework.data.mongodb.repository.config.ReactiveMongoRepositoryConfigurationExtension
 *  org.springframework.data.mongodb.repository.support.ReactiveMongoRepositoryFactoryBean
 */
package org.springframework.boot.autoconfigure.data.mongo;

import com.mongodb.reactivestreams.client.MongoClient;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.ConditionalOnRepositoryType;
import org.springframework.boot.autoconfigure.data.RepositoryType;
import org.springframework.boot.autoconfigure.data.mongo.MongoReactiveDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoReactiveRepositoriesRegistrar;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.config.ReactiveMongoRepositoryConfigurationExtension;
import org.springframework.data.mongodb.repository.support.ReactiveMongoRepositoryFactoryBean;

@AutoConfiguration(after={MongoReactiveDataAutoConfiguration.class})
@ConditionalOnClass(value={MongoClient.class, ReactiveMongoRepository.class})
@ConditionalOnMissingBean(value={ReactiveMongoRepositoryFactoryBean.class, ReactiveMongoRepositoryConfigurationExtension.class})
@ConditionalOnRepositoryType(store="mongodb", type=RepositoryType.REACTIVE)
@Import(value={MongoReactiveRepositoriesRegistrar.class})
public class MongoReactiveRepositoriesAutoConfiguration {
}

