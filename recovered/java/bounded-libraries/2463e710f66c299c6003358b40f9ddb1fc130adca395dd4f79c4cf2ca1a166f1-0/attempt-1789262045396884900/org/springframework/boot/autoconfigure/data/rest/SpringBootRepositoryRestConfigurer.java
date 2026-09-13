/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  org.springframework.core.annotation.Order
 *  org.springframework.data.rest.core.config.RepositoryRestConfiguration
 *  org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer
 *  org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
 *  org.springframework.web.servlet.config.annotation.CorsRegistry
 */
package org.springframework.boot.autoconfigure.data.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.data.rest.RepositoryRestProperties;
import org.springframework.core.annotation.Order;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Order(value=0)
class SpringBootRepositoryRestConfigurer
implements RepositoryRestConfigurer {
    private final Jackson2ObjectMapperBuilder objectMapperBuilder;
    private final RepositoryRestProperties properties;

    SpringBootRepositoryRestConfigurer(Jackson2ObjectMapperBuilder objectMapperBuilder, RepositoryRestProperties properties) {
        this.objectMapperBuilder = objectMapperBuilder;
        this.properties = properties;
    }

    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        this.properties.applyTo(config);
    }

    public void configureJacksonObjectMapper(ObjectMapper objectMapper) {
        if (this.objectMapperBuilder != null) {
            this.objectMapperBuilder.configure(objectMapper);
        }
    }
}

