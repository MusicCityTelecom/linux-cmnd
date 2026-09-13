/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonFactory
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.dataformat.cbor.CBORFactory
 *  io.netty.buffer.PooledByteBufAllocator
 *  io.rsocket.RSocket
 *  org.springframework.beans.factory.ObjectProvider
 *  org.springframework.boot.rsocket.messaging.RSocketStrategiesCustomizer
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.core.annotation.Order
 *  org.springframework.core.codec.Decoder
 *  org.springframework.core.codec.Encoder
 *  org.springframework.http.MediaType
 *  org.springframework.http.codec.cbor.Jackson2CborDecoder
 *  org.springframework.http.codec.cbor.Jackson2CborEncoder
 *  org.springframework.http.codec.json.Jackson2JsonDecoder
 *  org.springframework.http.codec.json.Jackson2JsonEncoder
 *  org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
 *  org.springframework.messaging.rsocket.RSocketStrategies
 *  org.springframework.messaging.rsocket.RSocketStrategies$Builder
 *  org.springframework.util.ClassUtils
 *  org.springframework.util.MimeType
 *  org.springframework.util.RouteMatcher
 *  org.springframework.web.util.pattern.PathPatternRouteMatcher
 */
package org.springframework.boot.autoconfigure.rsocket;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.cbor.CBORFactory;
import io.netty.buffer.PooledByteBufAllocator;
import io.rsocket.RSocket;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.rsocket.messaging.RSocketStrategiesCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.codec.Decoder;
import org.springframework.core.codec.Encoder;
import org.springframework.http.MediaType;
import org.springframework.http.codec.cbor.Jackson2CborDecoder;
import org.springframework.http.codec.cbor.Jackson2CborEncoder;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.messaging.rsocket.RSocketStrategies;
import org.springframework.util.ClassUtils;
import org.springframework.util.MimeType;
import org.springframework.util.RouteMatcher;
import org.springframework.web.util.pattern.PathPatternRouteMatcher;

@AutoConfiguration(after={JacksonAutoConfiguration.class})
@ConditionalOnClass(value={RSocket.class, RSocketStrategies.class, PooledByteBufAllocator.class})
public class RSocketStrategiesAutoConfiguration {
    private static final String PATHPATTERN_ROUTEMATCHER_CLASS = "org.springframework.web.util.pattern.PathPatternRouteMatcher";

    @Bean
    @ConditionalOnMissingBean
    public RSocketStrategies rSocketStrategies(ObjectProvider<RSocketStrategiesCustomizer> customizers) {
        RSocketStrategies.Builder builder = RSocketStrategies.builder();
        if (ClassUtils.isPresent((String)PATHPATTERN_ROUTEMATCHER_CLASS, null)) {
            builder.routeMatcher((RouteMatcher)new PathPatternRouteMatcher());
        }
        customizers.orderedStream().forEach(customizer -> customizer.customize(builder));
        return builder.build();
    }

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnClass(value={ObjectMapper.class})
    protected static class JacksonJsonStrategyConfiguration {
        private static final MediaType[] SUPPORTED_TYPES = new MediaType[]{MediaType.APPLICATION_JSON, new MediaType("application", "*+json")};

        protected JacksonJsonStrategyConfiguration() {
        }

        @Bean
        @Order(value=1)
        @ConditionalOnBean(value={ObjectMapper.class})
        public RSocketStrategiesCustomizer jacksonJsonRSocketStrategyCustomizer(ObjectMapper objectMapper) {
            return strategy -> {
                strategy.decoder(new Decoder[]{new Jackson2JsonDecoder(objectMapper, (MimeType[])SUPPORTED_TYPES)});
                strategy.encoder(new Encoder[]{new Jackson2JsonEncoder(objectMapper, (MimeType[])SUPPORTED_TYPES)});
            };
        }
    }

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnClass(value={ObjectMapper.class, CBORFactory.class})
    protected static class JacksonCborStrategyConfiguration {
        private static final MediaType[] SUPPORTED_TYPES = new MediaType[]{MediaType.APPLICATION_CBOR};

        protected JacksonCborStrategyConfiguration() {
        }

        @Bean
        @Order(value=0)
        @ConditionalOnBean(value={Jackson2ObjectMapperBuilder.class})
        public RSocketStrategiesCustomizer jacksonCborRSocketStrategyCustomizer(Jackson2ObjectMapperBuilder builder) {
            return strategy -> {
                ObjectMapper objectMapper = builder.createXmlMapper(false).factory((JsonFactory)new CBORFactory()).build();
                strategy.decoder(new Decoder[]{new Jackson2CborDecoder(objectMapper, (MimeType[])SUPPORTED_TYPES)});
                strategy.encoder(new Encoder[]{new Jackson2CborEncoder(objectMapper, (MimeType[])SUPPORTED_TYPES)});
            };
        }
    }
}

