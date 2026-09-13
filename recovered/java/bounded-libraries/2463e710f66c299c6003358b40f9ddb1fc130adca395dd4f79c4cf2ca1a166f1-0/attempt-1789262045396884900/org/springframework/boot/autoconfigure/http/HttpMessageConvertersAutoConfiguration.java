/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.ObjectProvider
 *  org.springframework.boot.context.properties.bind.Binder
 *  org.springframework.boot.web.servlet.server.Encoding
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Conditional
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.context.annotation.ConfigurationCondition$ConfigurationPhase
 *  org.springframework.context.annotation.Import
 *  org.springframework.core.env.Environment
 *  org.springframework.http.converter.HttpMessageConverter
 *  org.springframework.http.converter.StringHttpMessageConverter
 */
package org.springframework.boot.autoconfigure.http;

import java.util.stream.Collectors;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.NoneNestedConditions;
import org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration;
import org.springframework.boot.autoconfigure.http.GsonHttpMessageConvertersConfiguration;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.autoconfigure.http.JacksonHttpMessageConvertersConfiguration;
import org.springframework.boot.autoconfigure.http.JsonbHttpMessageConvertersConfiguration;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.autoconfigure.jsonb.JsonbAutoConfiguration;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.web.servlet.server.Encoding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ConfigurationCondition;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;

@AutoConfiguration(after={GsonAutoConfiguration.class, JacksonAutoConfiguration.class, JsonbAutoConfiguration.class})
@ConditionalOnClass(value={HttpMessageConverter.class})
@Conditional(value={NotReactiveWebApplicationCondition.class})
@Import(value={JacksonHttpMessageConvertersConfiguration.class, GsonHttpMessageConvertersConfiguration.class, JsonbHttpMessageConvertersConfiguration.class})
public class HttpMessageConvertersAutoConfiguration {
    static final String PREFERRED_MAPPER_PROPERTY = "spring.mvc.converters.preferred-json-mapper";

    @Bean
    @ConditionalOnMissingBean
    public HttpMessageConverters messageConverters(ObjectProvider<HttpMessageConverter<?>> converters) {
        return new HttpMessageConverters(converters.orderedStream().collect(Collectors.toList()));
    }

    static class NotReactiveWebApplicationCondition
    extends NoneNestedConditions {
        NotReactiveWebApplicationCondition() {
            super(ConfigurationCondition.ConfigurationPhase.PARSE_CONFIGURATION);
        }

        @ConditionalOnWebApplication(type=ConditionalOnWebApplication.Type.REACTIVE)
        private static class ReactiveWebApplication {
            private ReactiveWebApplication() {
            }
        }
    }

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnClass(value={StringHttpMessageConverter.class})
    protected static class StringHttpMessageConverterConfiguration {
        protected StringHttpMessageConverterConfiguration() {
        }

        @Bean
        @ConditionalOnMissingBean
        public StringHttpMessageConverter stringHttpMessageConverter(Environment environment) {
            Encoding encoding = (Encoding)Binder.get((Environment)environment).bindOrCreate("server.servlet.encoding", Encoding.class);
            StringHttpMessageConverter converter = new StringHttpMessageConverter(encoding.getCharset());
            converter.setWriteAcceptCharset(false);
            return converter;
        }
    }
}

