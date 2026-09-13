/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mongodb.client.MongoClient
 *  org.springframework.boot.context.properties.EnableConfigurationProperties
 *  org.springframework.context.annotation.Import
 *  org.springframework.data.mongodb.core.MongoTemplate
 */
package org.springframework.boot.autoconfigure.data.mongo;

import com.mongodb.client.MongoClient;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoDatabaseFactoryConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoDatabaseFactoryDependentConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;

@AutoConfiguration(after={MongoAutoConfiguration.class})
@ConditionalOnClass(value={MongoClient.class, MongoTemplate.class})
@EnableConfigurationProperties(value={MongoProperties.class})
@Import(value={MongoDataConfiguration.class, MongoDatabaseFactoryConfiguration.class, MongoDatabaseFactoryDependentConfiguration.class})
public class MongoDataAutoConfiguration {
}

