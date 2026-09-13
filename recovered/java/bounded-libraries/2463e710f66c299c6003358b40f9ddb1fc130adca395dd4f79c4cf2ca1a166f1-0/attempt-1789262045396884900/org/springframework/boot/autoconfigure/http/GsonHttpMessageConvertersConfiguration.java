/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Conditional
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.context.annotation.ConfigurationCondition$ConfigurationPhase
 *  org.springframework.http.converter.json.GsonHttpMessageConverter
 *  org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
 */
package org.springframework.boot.autoconfigure.http;

import com.google.gson.Gson;
import org.springframework.boot.autoconfigure.condition.AnyNestedCondition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.NoneNestedConditions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ConfigurationCondition;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

@Configuration(proxyBeanMethods=false)
@ConditionalOnClass(value={Gson.class})
class GsonHttpMessageConvertersConfiguration {
    GsonHttpMessageConvertersConfiguration() {
    }

    private static class JacksonAndJsonbUnavailableCondition
    extends NoneNestedConditions {
        JacksonAndJsonbUnavailableCondition() {
            super(ConfigurationCondition.ConfigurationPhase.REGISTER_BEAN);
        }

        @ConditionalOnProperty(name={"spring.mvc.converters.preferred-json-mapper"}, havingValue="jsonb")
        static class JsonbPreferred {
            JsonbPreferred() {
            }
        }

        @ConditionalOnBean(value={MappingJackson2HttpMessageConverter.class})
        static class JacksonAvailable {
            JacksonAvailable() {
            }
        }
    }

    private static class PreferGsonOrJacksonAndJsonbUnavailableCondition
    extends AnyNestedCondition {
        PreferGsonOrJacksonAndJsonbUnavailableCondition() {
            super(ConfigurationCondition.ConfigurationPhase.REGISTER_BEAN);
        }

        @Conditional(value={JacksonAndJsonbUnavailableCondition.class})
        static class JacksonJsonbUnavailable {
            JacksonJsonbUnavailable() {
            }
        }

        @ConditionalOnProperty(name={"spring.mvc.converters.preferred-json-mapper"}, havingValue="gson")
        static class GsonPreferred {
            GsonPreferred() {
            }
        }
    }

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnBean(value={Gson.class})
    @Conditional(value={PreferGsonOrJacksonAndJsonbUnavailableCondition.class})
    static class GsonHttpMessageConverterConfiguration {
        GsonHttpMessageConverterConfiguration() {
        }

        @Bean
        @ConditionalOnMissingBean
        GsonHttpMessageConverter gsonHttpMessageConverter(Gson gson) {
            GsonHttpMessageConverter converter = new GsonHttpMessageConverter();
            converter.setGson(gson);
            return converter;
        }
    }
}

