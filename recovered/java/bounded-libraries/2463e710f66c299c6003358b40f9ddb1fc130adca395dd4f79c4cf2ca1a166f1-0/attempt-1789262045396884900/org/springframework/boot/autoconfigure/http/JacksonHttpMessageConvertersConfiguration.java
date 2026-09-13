/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.dataformat.xml.XmlMapper
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
 *  org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
 *  org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter
 */
package org.springframework.boot.autoconfigure.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;

@Configuration(proxyBeanMethods=false)
class JacksonHttpMessageConvertersConfiguration {
    JacksonHttpMessageConvertersConfiguration() {
    }

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnClass(value={XmlMapper.class})
    @ConditionalOnBean(value={Jackson2ObjectMapperBuilder.class})
    protected static class MappingJackson2XmlHttpMessageConverterConfiguration {
        protected MappingJackson2XmlHttpMessageConverterConfiguration() {
        }

        @Bean
        @ConditionalOnMissingBean
        public MappingJackson2XmlHttpMessageConverter mappingJackson2XmlHttpMessageConverter(Jackson2ObjectMapperBuilder builder) {
            return new MappingJackson2XmlHttpMessageConverter(builder.createXmlMapper(true).build());
        }
    }

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnClass(value={ObjectMapper.class})
    @ConditionalOnBean(value={ObjectMapper.class})
    @ConditionalOnProperty(name={"spring.mvc.converters.preferred-json-mapper"}, havingValue="jackson", matchIfMissing=true)
    static class MappingJackson2HttpMessageConverterConfiguration {
        MappingJackson2HttpMessageConverterConfiguration() {
        }

        @Bean
        @ConditionalOnMissingBean(value={MappingJackson2HttpMessageConverter.class}, ignoredType={"org.springframework.hateoas.server.mvc.TypeConstrainedMappingJackson2HttpMessageConverter", "org.springframework.data.rest.webmvc.alps.AlpsJsonHttpMessageConverter"})
        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter(ObjectMapper objectMapper) {
            return new MappingJackson2HttpMessageConverter(objectMapper);
        }
    }
}

