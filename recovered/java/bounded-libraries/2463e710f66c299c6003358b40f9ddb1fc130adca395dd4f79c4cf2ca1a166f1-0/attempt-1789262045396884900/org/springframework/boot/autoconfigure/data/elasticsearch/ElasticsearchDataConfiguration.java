/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.elasticsearch.client.RestHighLevelClient
 *  org.springframework.context.ApplicationContext
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.data.convert.CustomConversions
 *  org.springframework.data.elasticsearch.annotations.Document
 *  org.springframework.data.elasticsearch.client.reactive.ReactiveElasticsearchClient
 *  org.springframework.data.elasticsearch.core.ElasticsearchOperations
 *  org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate
 *  org.springframework.data.elasticsearch.core.ReactiveElasticsearchOperations
 *  org.springframework.data.elasticsearch.core.ReactiveElasticsearchTemplate
 *  org.springframework.data.elasticsearch.core.convert.ElasticsearchConverter
 *  org.springframework.data.elasticsearch.core.convert.ElasticsearchCustomConversions
 *  org.springframework.data.elasticsearch.core.convert.MappingElasticsearchConverter
 *  org.springframework.data.elasticsearch.core.mapping.SimpleElasticsearchMappingContext
 *  org.springframework.data.mapping.context.MappingContext
 *  org.springframework.web.reactive.function.client.WebClient
 */
package org.springframework.boot.autoconfigure.data.elasticsearch;

import java.util.Collections;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.domain.EntityScanner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.convert.CustomConversions;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.client.reactive.ReactiveElasticsearchClient;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.ReactiveElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ReactiveElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchConverter;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchCustomConversions;
import org.springframework.data.elasticsearch.core.convert.MappingElasticsearchConverter;
import org.springframework.data.elasticsearch.core.mapping.SimpleElasticsearchMappingContext;
import org.springframework.data.mapping.context.MappingContext;
import org.springframework.web.reactive.function.client.WebClient;

abstract class ElasticsearchDataConfiguration {
    ElasticsearchDataConfiguration() {
    }

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnClass(value={WebClient.class, ReactiveElasticsearchOperations.class})
    static class ReactiveRestClientConfiguration {
        ReactiveRestClientConfiguration() {
        }

        @Bean
        @ConditionalOnMissingBean(value={ReactiveElasticsearchOperations.class}, name={"reactiveElasticsearchTemplate"})
        @ConditionalOnBean(value={ReactiveElasticsearchClient.class})
        ReactiveElasticsearchTemplate reactiveElasticsearchTemplate(ReactiveElasticsearchClient client, ElasticsearchConverter converter) {
            return new ReactiveElasticsearchTemplate(client, converter);
        }
    }

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnClass(value={RestHighLevelClient.class})
    static class RestClientConfiguration {
        RestClientConfiguration() {
        }

        @Bean
        @ConditionalOnMissingBean(value={ElasticsearchOperations.class}, name={"elasticsearchTemplate"})
        @ConditionalOnBean(value={RestHighLevelClient.class})
        ElasticsearchRestTemplate elasticsearchTemplate(RestHighLevelClient client, ElasticsearchConverter converter) {
            return new ElasticsearchRestTemplate(client, converter);
        }
    }

    @Configuration(proxyBeanMethods=false)
    static class BaseConfiguration {
        BaseConfiguration() {
        }

        @Bean
        @ConditionalOnMissingBean
        ElasticsearchCustomConversions elasticsearchCustomConversions() {
            return new ElasticsearchCustomConversions(Collections.emptyList());
        }

        @Bean
        @ConditionalOnMissingBean
        SimpleElasticsearchMappingContext mappingContext(ApplicationContext applicationContext, ElasticsearchCustomConversions elasticsearchCustomConversions) throws ClassNotFoundException {
            SimpleElasticsearchMappingContext mappingContext = new SimpleElasticsearchMappingContext();
            mappingContext.setInitialEntitySet(new EntityScanner(applicationContext).scan(Document.class));
            mappingContext.setSimpleTypeHolder(elasticsearchCustomConversions.getSimpleTypeHolder());
            return mappingContext;
        }

        @Bean
        @ConditionalOnMissingBean
        ElasticsearchConverter elasticsearchConverter(SimpleElasticsearchMappingContext mappingContext, ElasticsearchCustomConversions elasticsearchCustomConversions) {
            MappingElasticsearchConverter converter = new MappingElasticsearchConverter((MappingContext)mappingContext);
            converter.setConversions((CustomConversions)elasticsearchCustomConversions);
            return converter;
        }
    }
}

