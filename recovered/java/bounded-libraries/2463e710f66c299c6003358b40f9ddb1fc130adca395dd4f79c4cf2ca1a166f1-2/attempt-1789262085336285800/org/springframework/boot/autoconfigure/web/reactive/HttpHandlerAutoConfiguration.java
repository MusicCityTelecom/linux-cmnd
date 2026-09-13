/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.ObjectProvider
 *  org.springframework.context.ApplicationContext
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.http.server.reactive.ContextPathCompositeHandler
 *  org.springframework.http.server.reactive.HttpHandler
 *  org.springframework.util.StringUtils
 *  org.springframework.web.reactive.DispatcherHandler
 *  org.springframework.web.server.adapter.WebHttpHandlerBuilder
 */
package org.springframework.boot.autoconfigure.web.reactive;

import java.util.Collections;
import java.util.Map;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.reactive.WebFluxAutoConfiguration;
import org.springframework.boot.autoconfigure.web.reactive.WebFluxProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.reactive.ContextPathCompositeHandler;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.DispatcherHandler;
import org.springframework.web.server.adapter.WebHttpHandlerBuilder;

@AutoConfiguration(after={WebFluxAutoConfiguration.class})
@ConditionalOnClass(value={DispatcherHandler.class, HttpHandler.class})
@ConditionalOnWebApplication(type=ConditionalOnWebApplication.Type.REACTIVE)
@ConditionalOnMissingBean(value={HttpHandler.class})
@AutoConfigureOrder(value=-2147483638)
public class HttpHandlerAutoConfiguration {

    @Configuration(proxyBeanMethods=false)
    public static class AnnotationConfig {
        private final ApplicationContext applicationContext;

        public AnnotationConfig(ApplicationContext applicationContext) {
            this.applicationContext = applicationContext;
        }

        @Bean
        public HttpHandler httpHandler(ObjectProvider<WebFluxProperties> propsProvider) {
            HttpHandler httpHandler = WebHttpHandlerBuilder.applicationContext((ApplicationContext)this.applicationContext).build();
            WebFluxProperties properties = (WebFluxProperties)propsProvider.getIfAvailable();
            if (properties != null && StringUtils.hasText((String)properties.getBasePath())) {
                Map<String, HttpHandler> handlersMap = Collections.singletonMap(properties.getBasePath(), httpHandler);
                return new ContextPathCompositeHandler(handlersMap);
            }
            return httpHandler;
        }
    }
}

