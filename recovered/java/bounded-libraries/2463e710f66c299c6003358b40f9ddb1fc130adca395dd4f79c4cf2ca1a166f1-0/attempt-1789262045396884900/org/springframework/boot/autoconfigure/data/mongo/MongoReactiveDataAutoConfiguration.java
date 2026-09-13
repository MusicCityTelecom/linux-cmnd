/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mongodb.ClientSessionOptions
 *  com.mongodb.reactivestreams.client.ClientSession
 *  com.mongodb.reactivestreams.client.MongoClient
 *  com.mongodb.reactivestreams.client.MongoDatabase
 *  org.bson.codecs.Codec
 *  org.bson.codecs.configuration.CodecRegistry
 *  org.springframework.boot.context.properties.EnableConfigurationProperties
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Import
 *  org.springframework.core.io.buffer.DataBufferFactory
 *  org.springframework.core.io.buffer.DefaultDataBufferFactory
 *  org.springframework.dao.DataAccessException
 *  org.springframework.dao.support.PersistenceExceptionTranslator
 *  org.springframework.data.convert.CustomConversions
 *  org.springframework.data.mapping.context.MappingContext
 *  org.springframework.data.mongodb.ReactiveMongoDatabaseFactory
 *  org.springframework.data.mongodb.core.ReactiveMongoOperations
 *  org.springframework.data.mongodb.core.ReactiveMongoTemplate
 *  org.springframework.data.mongodb.core.SimpleReactiveMongoDatabaseFactory
 *  org.springframework.data.mongodb.core.convert.DbRefResolver
 *  org.springframework.data.mongodb.core.convert.MappingMongoConverter
 *  org.springframework.data.mongodb.core.convert.MongoConverter
 *  org.springframework.data.mongodb.core.convert.MongoCustomConversions
 *  org.springframework.data.mongodb.core.convert.NoOpDbRefResolver
 *  org.springframework.data.mongodb.core.mapping.MongoMappingContext
 *  org.springframework.data.mongodb.gridfs.ReactiveGridFsOperations
 *  org.springframework.data.mongodb.gridfs.ReactiveGridFsTemplate
 *  org.springframework.util.StringUtils
 *  reactor.core.publisher.Mono
 */
package org.springframework.boot.autoconfigure.data.mongo;

import com.mongodb.ClientSessionOptions;
import com.mongodb.reactivestreams.client.ClientSession;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoDatabase;
import java.util.Optional;
import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecRegistry;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.autoconfigure.mongo.MongoReactiveAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.support.PersistenceExceptionTranslator;
import org.springframework.data.convert.CustomConversions;
import org.springframework.data.mapping.context.MappingContext;
import org.springframework.data.mongodb.ReactiveMongoDatabaseFactory;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.SimpleReactiveMongoDatabaseFactory;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.convert.NoOpDbRefResolver;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.gridfs.ReactiveGridFsOperations;
import org.springframework.data.mongodb.gridfs.ReactiveGridFsTemplate;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;

@AutoConfiguration(after={MongoReactiveAutoConfiguration.class})
@ConditionalOnClass(value={MongoClient.class, ReactiveMongoTemplate.class})
@ConditionalOnBean(value={MongoClient.class})
@EnableConfigurationProperties(value={MongoProperties.class})
@Import(value={MongoDataConfiguration.class})
public class MongoReactiveDataAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean(value={ReactiveMongoDatabaseFactory.class})
    public SimpleReactiveMongoDatabaseFactory reactiveMongoDatabaseFactory(MongoProperties properties, MongoClient mongo) {
        String database = properties.getMongoClientDatabase();
        return new SimpleReactiveMongoDatabaseFactory(mongo, database);
    }

    @Bean
    @ConditionalOnMissingBean(value={ReactiveMongoOperations.class})
    public ReactiveMongoTemplate reactiveMongoTemplate(ReactiveMongoDatabaseFactory reactiveMongoDatabaseFactory, MongoConverter converter) {
        return new ReactiveMongoTemplate(reactiveMongoDatabaseFactory, converter);
    }

    @Bean
    @ConditionalOnMissingBean(value={MongoConverter.class})
    public MappingMongoConverter mappingMongoConverter(MongoMappingContext context, MongoCustomConversions conversions) {
        MappingMongoConverter mappingConverter = new MappingMongoConverter((DbRefResolver)NoOpDbRefResolver.INSTANCE, (MappingContext)context);
        mappingConverter.setCustomConversions((CustomConversions)conversions);
        return mappingConverter;
    }

    @Bean
    @ConditionalOnMissingBean(value={DataBufferFactory.class})
    public DefaultDataBufferFactory dataBufferFactory() {
        return new DefaultDataBufferFactory();
    }

    @Bean
    @ConditionalOnMissingBean(value={ReactiveGridFsOperations.class})
    public ReactiveGridFsTemplate reactiveGridFsTemplate(ReactiveMongoDatabaseFactory reactiveMongoDatabaseFactory, MappingMongoConverter mappingMongoConverter, DataBufferFactory dataBufferFactory, MongoProperties properties) {
        return new ReactiveGridFsTemplate(dataBufferFactory, (ReactiveMongoDatabaseFactory)new GridFsReactiveMongoDatabaseFactory(reactiveMongoDatabaseFactory, properties), (MongoConverter)mappingMongoConverter, properties.getGridfs().getBucket());
    }

    static class GridFsReactiveMongoDatabaseFactory
    implements ReactiveMongoDatabaseFactory {
        private final ReactiveMongoDatabaseFactory delegate;
        private final MongoProperties properties;

        GridFsReactiveMongoDatabaseFactory(ReactiveMongoDatabaseFactory delegate, MongoProperties properties) {
            this.delegate = delegate;
            this.properties = properties;
        }

        public boolean hasCodecFor(Class<?> type) {
            return this.delegate.hasCodecFor(type);
        }

        public Mono<MongoDatabase> getMongoDatabase() throws DataAccessException {
            String gridFsDatabase = this.properties.getGridfs().getDatabase();
            if (StringUtils.hasText((String)gridFsDatabase)) {
                return this.delegate.getMongoDatabase(gridFsDatabase);
            }
            return this.delegate.getMongoDatabase();
        }

        public Mono<MongoDatabase> getMongoDatabase(String dbName) throws DataAccessException {
            return this.delegate.getMongoDatabase(dbName);
        }

        public <T> Optional<Codec<T>> getCodecFor(Class<T> type) {
            return this.delegate.getCodecFor(type);
        }

        public PersistenceExceptionTranslator getExceptionTranslator() {
            return this.delegate.getExceptionTranslator();
        }

        public CodecRegistry getCodecRegistry() {
            return this.delegate.getCodecRegistry();
        }

        public Mono<ClientSession> getSession(ClientSessionOptions options) {
            return this.delegate.getSession(options);
        }

        public ReactiveMongoDatabaseFactory withSession(ClientSession session) {
            return this.delegate.withSession(session);
        }

        public boolean isTransactionActive() {
            return this.delegate.isTransactionActive();
        }
    }
}

