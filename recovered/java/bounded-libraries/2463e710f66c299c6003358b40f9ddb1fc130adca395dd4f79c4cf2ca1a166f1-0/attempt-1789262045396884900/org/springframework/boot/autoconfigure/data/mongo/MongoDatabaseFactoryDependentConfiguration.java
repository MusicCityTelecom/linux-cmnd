/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mongodb.ClientSessionOptions
 *  com.mongodb.client.ClientSession
 *  com.mongodb.client.MongoDatabase
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.dao.DataAccessException
 *  org.springframework.dao.support.PersistenceExceptionTranslator
 *  org.springframework.data.convert.CustomConversions
 *  org.springframework.data.mapping.context.MappingContext
 *  org.springframework.data.mongodb.MongoDatabaseFactory
 *  org.springframework.data.mongodb.core.MongoOperations
 *  org.springframework.data.mongodb.core.MongoTemplate
 *  org.springframework.data.mongodb.core.convert.DbRefResolver
 *  org.springframework.data.mongodb.core.convert.DefaultDbRefResolver
 *  org.springframework.data.mongodb.core.convert.MappingMongoConverter
 *  org.springframework.data.mongodb.core.convert.MongoConverter
 *  org.springframework.data.mongodb.core.convert.MongoCustomConversions
 *  org.springframework.data.mongodb.core.mapping.MongoMappingContext
 *  org.springframework.data.mongodb.gridfs.GridFsOperations
 *  org.springframework.data.mongodb.gridfs.GridFsTemplate
 *  org.springframework.util.Assert
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.autoconfigure.data.mongo;

import com.mongodb.ClientSessionOptions;
import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoDatabase;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.support.PersistenceExceptionTranslator;
import org.springframework.data.convert.CustomConversions;
import org.springframework.data.mapping.context.MappingContext;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

@Configuration(proxyBeanMethods=false)
@ConditionalOnBean(value={MongoDatabaseFactory.class})
class MongoDatabaseFactoryDependentConfiguration {
    private final MongoProperties properties;

    MongoDatabaseFactoryDependentConfiguration(MongoProperties properties) {
        this.properties = properties;
    }

    @Bean
    @ConditionalOnMissingBean(value={MongoOperations.class})
    MongoTemplate mongoTemplate(MongoDatabaseFactory factory, MongoConverter converter) {
        return new MongoTemplate(factory, converter);
    }

    @Bean
    @ConditionalOnMissingBean(value={MongoConverter.class})
    MappingMongoConverter mappingMongoConverter(MongoDatabaseFactory factory, MongoMappingContext context, MongoCustomConversions conversions) {
        DefaultDbRefResolver dbRefResolver = new DefaultDbRefResolver(factory);
        MappingMongoConverter mappingConverter = new MappingMongoConverter((DbRefResolver)dbRefResolver, (MappingContext)context);
        mappingConverter.setCustomConversions((CustomConversions)conversions);
        return mappingConverter;
    }

    @Bean
    @ConditionalOnMissingBean(value={GridFsOperations.class})
    GridFsTemplate gridFsTemplate(MongoDatabaseFactory factory, MongoTemplate mongoTemplate) {
        return new GridFsTemplate((MongoDatabaseFactory)new GridFsMongoDatabaseFactory(factory, this.properties), mongoTemplate.getConverter(), this.properties.getGridfs().getBucket());
    }

    static class GridFsMongoDatabaseFactory
    implements MongoDatabaseFactory {
        private final MongoDatabaseFactory mongoDatabaseFactory;
        private final MongoProperties properties;

        GridFsMongoDatabaseFactory(MongoDatabaseFactory mongoDatabaseFactory, MongoProperties properties) {
            Assert.notNull((Object)mongoDatabaseFactory, (String)"MongoDatabaseFactory must not be null");
            Assert.notNull((Object)properties, (String)"Properties must not be null");
            this.mongoDatabaseFactory = mongoDatabaseFactory;
            this.properties = properties;
        }

        public MongoDatabase getMongoDatabase() throws DataAccessException {
            String gridFsDatabase = this.properties.getGridfs().getDatabase();
            if (StringUtils.hasText((String)gridFsDatabase)) {
                return this.mongoDatabaseFactory.getMongoDatabase(gridFsDatabase);
            }
            return this.mongoDatabaseFactory.getMongoDatabase();
        }

        public MongoDatabase getMongoDatabase(String dbName) throws DataAccessException {
            return this.mongoDatabaseFactory.getMongoDatabase(dbName);
        }

        public PersistenceExceptionTranslator getExceptionTranslator() {
            return this.mongoDatabaseFactory.getExceptionTranslator();
        }

        public ClientSession getSession(ClientSessionOptions options) {
            return this.mongoDatabaseFactory.getSession(options);
        }

        public MongoDatabaseFactory withSession(ClientSession session) {
            return this.mongoDatabaseFactory.withSession(session);
        }
    }
}

