/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.boot.web.reactive.function.client.WebClientCustomizer
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Import
 *  org.springframework.context.annotation.Lazy
 *  org.springframework.core.annotation.Order
 *  org.springframework.http.client.reactive.ClientHttpConnector
 *  org.springframework.web.reactive.function.client.WebClient
 */
package org.springframework.boot.autoconfigure.web.reactive.function.client;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.web.reactive.function.client.ClientHttpConnectorConfiguration;
import org.springframework.boot.web.reactive.function.client.WebClientCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;

@AutoConfiguration
@ConditionalOnClass(value={WebClient.class})
@Import(value={ClientHttpConnectorConfiguration.ReactorNetty.class, ClientHttpConnectorConfiguration.JettyClient.class, ClientHttpConnectorConfiguration.HttpClient5.class})
public class ClientHttpConnectorAutoConfiguration {
    @Bean
    @Lazy
    @Order(value=0)
    @ConditionalOnBean(value={ClientHttpConnector.class})
    public WebClientCustomizer clientConnectorCustomizer(ClientHttpConnector clientHttpConnector) {
        return builder -> builder.clientConnector(clientHttpConnector);
    }
}

