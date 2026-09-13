/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.boot.context.properties.EnableConfigurationProperties
 *  org.springframework.context.annotation.Bean
 *  org.springframework.data.domain.PageRequest
 *  org.springframework.data.domain.Pageable
 *  org.springframework.data.web.PageableHandlerMethodArgumentResolver
 *  org.springframework.data.web.config.EnableSpringDataWebSupport
 *  org.springframework.data.web.config.PageableHandlerMethodArgumentResolverCustomizer
 *  org.springframework.data.web.config.SortHandlerMethodArgumentResolverCustomizer
 *  org.springframework.web.servlet.config.annotation.WebMvcConfigurer
 */
package org.springframework.boot.autoconfigure.data.web;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.data.rest.RepositoryRestMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.data.web.config.PageableHandlerMethodArgumentResolverCustomizer;
import org.springframework.data.web.config.SortHandlerMethodArgumentResolverCustomizer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@AutoConfiguration(after={RepositoryRestMvcAutoConfiguration.class})
@EnableSpringDataWebSupport
@ConditionalOnWebApplication(type=ConditionalOnWebApplication.Type.SERVLET)
@ConditionalOnClass(value={PageableHandlerMethodArgumentResolver.class, WebMvcConfigurer.class})
@ConditionalOnMissingBean(value={PageableHandlerMethodArgumentResolver.class})
@EnableConfigurationProperties(value={SpringDataWebProperties.class})
public class SpringDataWebAutoConfiguration {
    private final SpringDataWebProperties properties;

    public SpringDataWebAutoConfiguration(SpringDataWebProperties properties) {
        this.properties = properties;
    }

    @Bean
    @ConditionalOnMissingBean
    public PageableHandlerMethodArgumentResolverCustomizer pageableCustomizer() {
        return resolver -> {
            SpringDataWebProperties.Pageable pageable = this.properties.getPageable();
            resolver.setPageParameterName(pageable.getPageParameter());
            resolver.setSizeParameterName(pageable.getSizeParameter());
            resolver.setOneIndexedParameters(pageable.isOneIndexedParameters());
            resolver.setPrefix(pageable.getPrefix());
            resolver.setQualifierDelimiter(pageable.getQualifierDelimiter());
            resolver.setFallbackPageable((Pageable)PageRequest.of((int)0, (int)pageable.getDefaultPageSize()));
            resolver.setMaxPageSize(pageable.getMaxPageSize());
        };
    }

    @Bean
    @ConditionalOnMissingBean
    public SortHandlerMethodArgumentResolverCustomizer sortCustomizer() {
        return resolver -> resolver.setSortParameter(this.properties.getSort().getSortParameter());
    }
}

