/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  org.springframework.boot.LazyInitializationExcludeFilter
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.messaging.converter.ByteArrayMessageConverter
 *  org.springframework.messaging.converter.ContentTypeResolver
 *  org.springframework.messaging.converter.DefaultContentTypeResolver
 *  org.springframework.messaging.converter.MappingJackson2MessageConverter
 *  org.springframework.messaging.converter.MessageConverter
 *  org.springframework.messaging.converter.StringMessageConverter
 *  org.springframework.messaging.simp.config.AbstractMessageBrokerConfiguration
 *  org.springframework.util.MimeTypeUtils
 *  org.springframework.web.socket.config.annotation.DelegatingWebSocketMessageBrokerConfiguration
 *  org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer
 */
package org.springframework.boot.autoconfigure.websocket.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.springframework.boot.LazyInitializationExcludeFilter;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.ByteArrayMessageConverter;
import org.springframework.messaging.converter.ContentTypeResolver;
import org.springframework.messaging.converter.DefaultContentTypeResolver;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.config.AbstractMessageBrokerConfiguration;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.socket.config.annotation.DelegatingWebSocketMessageBrokerConfiguration;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@AutoConfiguration(after={JacksonAutoConfiguration.class})
@ConditionalOnWebApplication(type=ConditionalOnWebApplication.Type.SERVLET)
@ConditionalOnClass(value={WebSocketMessageBrokerConfigurer.class})
public class WebSocketMessagingAutoConfiguration {

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnBean(value={DelegatingWebSocketMessageBrokerConfiguration.class, ObjectMapper.class})
    @ConditionalOnClass(value={ObjectMapper.class, AbstractMessageBrokerConfiguration.class})
    static class WebSocketMessageConverterConfiguration
    implements WebSocketMessageBrokerConfigurer {
        private final ObjectMapper objectMapper;

        WebSocketMessageConverterConfiguration(ObjectMapper objectMapper) {
            this.objectMapper = objectMapper;
        }

        public boolean configureMessageConverters(List<MessageConverter> messageConverters) {
            MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
            converter.setObjectMapper(this.objectMapper);
            DefaultContentTypeResolver resolver = new DefaultContentTypeResolver();
            resolver.setDefaultMimeType(MimeTypeUtils.APPLICATION_JSON);
            converter.setContentTypeResolver((ContentTypeResolver)resolver);
            messageConverters.add((MessageConverter)new StringMessageConverter());
            messageConverters.add((MessageConverter)new ByteArrayMessageConverter());
            messageConverters.add((MessageConverter)converter);
            return false;
        }

        @Bean
        static LazyInitializationExcludeFilter eagerStompWebSocketHandlerMapping() {
            return (name, definition, type) -> name.equals("stompWebSocketHandlerMapping");
        }
    }
}

