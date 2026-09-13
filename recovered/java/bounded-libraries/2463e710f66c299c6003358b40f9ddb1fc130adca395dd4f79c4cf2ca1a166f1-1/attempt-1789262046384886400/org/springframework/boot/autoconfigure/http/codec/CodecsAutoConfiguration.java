/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  org.springframework.boot.context.properties.EnableConfigurationProperties
 *  org.springframework.boot.context.properties.PropertyMapper
 *  org.springframework.boot.web.codec.CodecCustomizer
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.core.annotation.Order
 *  org.springframework.core.codec.Decoder
 *  org.springframework.core.codec.Encoder
 *  org.springframework.http.codec.CodecConfigurer
 *  org.springframework.http.codec.CodecConfigurer$DefaultCodecs
 *  org.springframework.http.codec.json.Jackson2JsonDecoder
 *  org.springframework.http.codec.json.Jackson2JsonEncoder
 *  org.springframework.util.MimeType
 *  org.springframework.util.unit.DataSize
 *  org.springframework.web.reactive.function.client.WebClient
 */
package org.springframework.boot.autoconfigure.http.codec;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.codec.CodecProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.boot.web.codec.CodecCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.codec.Decoder;
import org.springframework.core.codec.Encoder;
import org.springframework.http.codec.CodecConfigurer;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.util.MimeType;
import org.springframework.util.unit.DataSize;
import org.springframework.web.reactive.function.client.WebClient;

@AutoConfiguration(after={JacksonAutoConfiguration.class})
@ConditionalOnClass(value={CodecConfigurer.class, WebClient.class})
@EnableConfigurationProperties(value={CodecProperties.class})
public class CodecsAutoConfiguration {
    private static final MimeType[] EMPTY_MIME_TYPES = new MimeType[0];

    @Configuration(proxyBeanMethods=false)
    static class DefaultCodecsConfiguration {
        DefaultCodecsConfiguration() {
        }

        @Bean
        @Order(value=0)
        CodecCustomizer defaultCodecCustomizer(CodecProperties codecProperties) {
            return configurer -> {
                PropertyMapper map = PropertyMapper.get();
                CodecConfigurer.DefaultCodecs defaultCodecs = configurer.defaultCodecs();
                defaultCodecs.enableLoggingRequestDetails(codecProperties.isLogRequestDetails());
                map.from((Object)codecProperties.getMaxInMemorySize()).whenNonNull().asInt(DataSize::toBytes).to(arg_0 -> ((CodecConfigurer.DefaultCodecs)defaultCodecs).maxInMemorySize(arg_0));
            };
        }
    }

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnClass(value={ObjectMapper.class})
    static class JacksonCodecConfiguration {
        JacksonCodecConfiguration() {
        }

        @Bean
        @Order(value=0)
        @ConditionalOnBean(value={ObjectMapper.class})
        CodecCustomizer jacksonCodecCustomizer(ObjectMapper objectMapper) {
            return configurer -> {
                CodecConfigurer.DefaultCodecs defaults = configurer.defaultCodecs();
                defaults.jackson2JsonDecoder((Decoder)new Jackson2JsonDecoder(objectMapper, EMPTY_MIME_TYPES));
                defaults.jackson2JsonEncoder((Encoder)new Jackson2JsonEncoder(objectMapper, EMPTY_MIME_TYPES));
            };
        }
    }
}

