/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.ObjectProvider
 *  org.springframework.boot.context.properties.EnableConfigurationProperties
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Import
 *  org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration
 *  org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
 */
package org.springframework.boot.autoconfigure.data.rest;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.data.rest.RepositoryRestProperties;
import org.springframework.boot.autoconfigure.data.rest.SpringBootRepositoryRestConfigurer;
import org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@AutoConfiguration(after={HttpMessageConvertersAutoConfiguration.class, JacksonAutoConfiguration.class})
@ConditionalOnWebApplication(type=ConditionalOnWebApplication.Type.SERVLET)
@ConditionalOnMissingBean(value={RepositoryRestMvcConfiguration.class})
@ConditionalOnClass(value={RepositoryRestMvcConfiguration.class})
@EnableConfigurationProperties(value={RepositoryRestProperties.class})
@Import(value={RepositoryRestMvcConfiguration.class})
public class RepositoryRestMvcAutoConfiguration {
    @Bean
    public SpringBootRepositoryRestConfigurer springBootRepositoryRestConfigurer(ObjectProvider<Jackson2ObjectMapperBuilder> objectMapperBuilder, RepositoryRestProperties properties) {
        return new SpringBootRepositoryRestConfigurer((Jackson2ObjectMapperBuilder)objectMapperBuilder.getIfAvailable(), properties);
    }
}

