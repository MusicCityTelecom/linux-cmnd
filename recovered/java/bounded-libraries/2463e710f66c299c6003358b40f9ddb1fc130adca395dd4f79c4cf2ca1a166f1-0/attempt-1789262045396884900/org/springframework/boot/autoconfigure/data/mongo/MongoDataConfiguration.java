/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.BeanUtils
 *  org.springframework.boot.context.properties.PropertyMapper
 *  org.springframework.context.ApplicationContext
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.data.mapping.model.FieldNamingStrategy
 *  org.springframework.data.mongodb.core.convert.MongoCustomConversions
 *  org.springframework.data.mongodb.core.mapping.Document
 *  org.springframework.data.mongodb.core.mapping.MongoMappingContext
 */
package org.springframework.boot.autoconfigure.data.mongo;

import java.util.Collections;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.domain.EntityScanner;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mapping.model.FieldNamingStrategy;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

@Configuration(proxyBeanMethods=false)
class MongoDataConfiguration {
    MongoDataConfiguration() {
    }

    @Bean
    @ConditionalOnMissingBean
    MongoMappingContext mongoMappingContext(ApplicationContext applicationContext, MongoProperties properties, MongoCustomConversions conversions) throws ClassNotFoundException {
        PropertyMapper mapper = PropertyMapper.get().alwaysApplyingWhenNonNull();
        MongoMappingContext context = new MongoMappingContext();
        mapper.from((Object)properties.isAutoIndexCreation()).to(arg_0 -> ((MongoMappingContext)context).setAutoIndexCreation(arg_0));
        context.setInitialEntitySet(new EntityScanner(applicationContext).scan(Document.class));
        Class<?> strategyClass = properties.getFieldNamingStrategy();
        if (strategyClass != null) {
            context.setFieldNamingStrategy((FieldNamingStrategy)BeanUtils.instantiateClass(strategyClass));
        }
        context.setSimpleTypeHolder(conversions.getSimpleTypeHolder());
        return context;
    }

    @Bean
    @ConditionalOnMissingBean
    MongoCustomConversions mongoCustomConversions() {
        return new MongoCustomConversions(Collections.emptyList());
    }
}

