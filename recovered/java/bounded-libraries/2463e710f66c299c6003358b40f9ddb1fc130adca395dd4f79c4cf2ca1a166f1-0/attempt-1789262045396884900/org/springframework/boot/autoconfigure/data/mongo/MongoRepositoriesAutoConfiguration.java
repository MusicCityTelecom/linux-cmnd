/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mongodb.client.MongoClient
 *  org.springframework.context.annotation.Import
 *  org.springframework.data.mongodb.repository.MongoRepository
 *  org.springframework.data.mongodb.repository.config.MongoRepositoryConfigurationExtension
 *  org.springframework.data.mongodb.repository.support.MongoRepositoryFactoryBean
 */
package org.springframework.boot.autoconfigure.data.mongo;

import com.mongodb.client.MongoClient;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.ConditionalOnRepositoryType;
import org.springframework.boot.autoconfigure.data.RepositoryType;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoRepositoriesRegistrar;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.MongoRepositoryConfigurationExtension;
import org.springframework.data.mongodb.repository.support.MongoRepositoryFactoryBean;

@AutoConfiguration(after={MongoDataAutoConfiguration.class})
@ConditionalOnClass(value={MongoClient.class, MongoRepository.class})
@ConditionalOnMissingBean(value={MongoRepositoryFactoryBean.class, MongoRepositoryConfigurationExtension.class})
@ConditionalOnRepositoryType(store="mongodb", type=RepositoryType.IMPERATIVE)
@Import(value={MongoRepositoriesRegistrar.class})
public class MongoRepositoriesAutoConfiguration {
}

