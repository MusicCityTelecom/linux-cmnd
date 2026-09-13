/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  org.springframework.boot.context.properties.EnableConfigurationProperties
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.hateoas.EntityModel
 *  org.springframework.hateoas.client.LinkDiscoverers
 *  org.springframework.hateoas.config.EnableHypermediaSupport
 *  org.springframework.hateoas.config.EnableHypermediaSupport$HypermediaType
 *  org.springframework.hateoas.mediatype.hal.HalConfiguration
 *  org.springframework.http.MediaType
 *  org.springframework.plugin.core.Plugin
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter
 */
package org.springframework.boot.autoconfigure.hateoas;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.data.rest.RepositoryRestMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.hateoas.HateoasProperties;
import org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.client.LinkDiscoverers;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.mediatype.hal.HalConfiguration;
import org.springframework.http.MediaType;
import org.springframework.plugin.core.Plugin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

@AutoConfiguration(after={WebMvcAutoConfiguration.class, JacksonAutoConfiguration.class, HttpMessageConvertersAutoConfiguration.class, RepositoryRestMvcAutoConfiguration.class})
@ConditionalOnClass(value={EntityModel.class, RequestMapping.class, RequestMappingHandlerAdapter.class, Plugin.class})
@ConditionalOnWebApplication
@EnableConfigurationProperties(value={HateoasProperties.class})
public class HypermediaAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnClass(name={"com.fasterxml.jackson.databind.ObjectMapper"})
    @ConditionalOnProperty(prefix="spring.hateoas", name={"use-hal-as-default-json-media-type"}, matchIfMissing=true)
    HalConfiguration applicationJsonHalConfiguration() {
        return new HalConfiguration().withMediaType(MediaType.APPLICATION_JSON);
    }

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnMissingBean(value={LinkDiscoverers.class})
    @ConditionalOnClass(value={ObjectMapper.class})
    @EnableHypermediaSupport(type={EnableHypermediaSupport.HypermediaType.HAL})
    protected static class HypermediaConfiguration {
        protected HypermediaConfiguration() {
        }
    }
}

