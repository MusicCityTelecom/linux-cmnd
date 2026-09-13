/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.json.bind.Jsonb
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Conditional
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.context.annotation.ConfigurationCondition$ConfigurationPhase
 *  org.springframework.http.converter.json.GsonHttpMessageConverter
 *  org.springframework.http.converter.json.JsonbHttpMessageConverter
 *  org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
 */
package org.springframework.boot.autoconfigure.http;

import javax.json.bind.Jsonb;
import org.springframework.boot.autoconfigure.condition.AnyNestedCondition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ConfigurationCondition;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.http.converter.json.JsonbHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

@Configuration(proxyBeanMethods=false)
@ConditionalOnClass(value={Jsonb.class})
class JsonbHttpMessageConvertersConfiguration {
    JsonbHttpMessageConvertersConfiguration() {
    }

    private static class PreferJsonbOrMissingJacksonAndGsonCondition
    extends AnyNestedCondition {
        PreferJsonbOrMissingJacksonAndGsonCondition() {
            super(ConfigurationCondition.ConfigurationPhase.REGISTER_BEAN);
        }

        @ConditionalOnMissingBean(value={MappingJackson2HttpMessageConverter.class, GsonHttpMessageConverter.class})
        static class JacksonAndGsonMissing {
            JacksonAndGsonMissing() {
            }
        }

        @ConditionalOnProperty(name={"spring.mvc.converters.preferred-json-mapper"}, havingValue="jsonb")
        static class JsonbPreferred {
            JsonbPreferred() {
            }
        }
    }

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnBean(value={Jsonb.class})
    @Conditional(value={PreferJsonbOrMissingJacksonAndGsonCondition.class})
    static class JsonbHttpMessageConverterConfiguration {
        JsonbHttpMessageConverterConfiguration() {
        }

        @Bean
        @ConditionalOnMissingBean
        JsonbHttpMessageConverter jsonbHttpMessageConverter(Jsonb jsonb) {
            JsonbHttpMessageConverter converter = new JsonbHttpMessageConverter();
            converter.setJsonb(jsonb);
            return converter;
        }
    }
}

