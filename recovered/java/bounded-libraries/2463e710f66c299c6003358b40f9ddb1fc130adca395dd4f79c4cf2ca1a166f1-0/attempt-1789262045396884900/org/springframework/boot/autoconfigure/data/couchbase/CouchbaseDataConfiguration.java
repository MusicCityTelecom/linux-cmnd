/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.BeanUtils
 *  org.springframework.context.ApplicationContext
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.data.convert.CustomConversions
 *  org.springframework.data.couchbase.core.convert.CouchbaseCustomConversions
 *  org.springframework.data.couchbase.core.convert.MappingCouchbaseConverter
 *  org.springframework.data.couchbase.core.convert.translation.JacksonTranslationService
 *  org.springframework.data.couchbase.core.convert.translation.TranslationService
 *  org.springframework.data.couchbase.core.mapping.CouchbaseMappingContext
 *  org.springframework.data.couchbase.core.mapping.Document
 *  org.springframework.data.mapping.context.MappingContext
 *  org.springframework.data.mapping.model.FieldNamingStrategy
 */
package org.springframework.boot.autoconfigure.data.couchbase;

import java.util.Collections;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.couchbase.CouchbaseDataProperties;
import org.springframework.boot.autoconfigure.domain.EntityScanner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.convert.CustomConversions;
import org.springframework.data.couchbase.core.convert.CouchbaseCustomConversions;
import org.springframework.data.couchbase.core.convert.MappingCouchbaseConverter;
import org.springframework.data.couchbase.core.convert.translation.JacksonTranslationService;
import org.springframework.data.couchbase.core.convert.translation.TranslationService;
import org.springframework.data.couchbase.core.mapping.CouchbaseMappingContext;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.mapping.context.MappingContext;
import org.springframework.data.mapping.model.FieldNamingStrategy;

@Configuration(proxyBeanMethods=false)
class CouchbaseDataConfiguration {
    CouchbaseDataConfiguration() {
    }

    @Bean
    @ConditionalOnMissingBean
    MappingCouchbaseConverter couchbaseMappingConverter(CouchbaseDataProperties properties, CouchbaseMappingContext couchbaseMappingContext, CouchbaseCustomConversions couchbaseCustomConversions) {
        MappingCouchbaseConverter converter = new MappingCouchbaseConverter((MappingContext)couchbaseMappingContext, properties.getTypeKey());
        converter.setCustomConversions((CustomConversions)couchbaseCustomConversions);
        return converter;
    }

    @Bean
    @ConditionalOnMissingBean
    TranslationService couchbaseTranslationService() {
        return new JacksonTranslationService();
    }

    @Bean(name={"couchbaseMappingContext"})
    @ConditionalOnMissingBean(name={"couchbaseMappingContext"})
    CouchbaseMappingContext couchbaseMappingContext(CouchbaseDataProperties properties, ApplicationContext applicationContext, CouchbaseCustomConversions couchbaseCustomConversions) throws Exception {
        CouchbaseMappingContext mappingContext = new CouchbaseMappingContext();
        mappingContext.setInitialEntitySet(new EntityScanner(applicationContext).scan(Document.class));
        mappingContext.setSimpleTypeHolder(couchbaseCustomConversions.getSimpleTypeHolder());
        Class<?> fieldNamingStrategy = properties.getFieldNamingStrategy();
        if (fieldNamingStrategy != null) {
            mappingContext.setFieldNamingStrategy((FieldNamingStrategy)BeanUtils.instantiateClass(fieldNamingStrategy));
        }
        mappingContext.setAutoIndexCreation(properties.isAutoIndex());
        return mappingContext;
    }

    @Bean(name={"couchbaseCustomConversions"})
    @ConditionalOnMissingBean(name={"couchbaseCustomConversions"})
    CouchbaseCustomConversions couchbaseCustomConversions() {
        return new CouchbaseCustomConversions(Collections.emptyList());
    }
}

