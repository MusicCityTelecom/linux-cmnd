/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.hc.client5.http.impl.async.HttpAsyncClients
 *  org.apache.hc.core5.http.nio.AsyncRequestProducer
 *  org.eclipse.jetty.client.HttpClient
 *  org.eclipse.jetty.reactive.client.ReactiveRequest
 *  org.eclipse.jetty.util.ssl.SslContextFactory
 *  org.eclipse.jetty.util.ssl.SslContextFactory$Client
 *  org.springframework.beans.factory.ObjectProvider
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.context.annotation.Lazy
 *  org.springframework.http.client.reactive.ClientHttpConnector
 *  org.springframework.http.client.reactive.HttpComponentsClientHttpConnector
 *  org.springframework.http.client.reactive.JettyClientHttpConnector
 *  org.springframework.http.client.reactive.JettyResourceFactory
 *  org.springframework.http.client.reactive.ReactorClientHttpConnector
 *  org.springframework.http.client.reactive.ReactorResourceFactory
 *  reactor.netty.http.client.HttpClient
 */
package org.springframework.boot.autoconfigure.web.reactive.function.client;

import org.apache.hc.client5.http.impl.async.HttpAsyncClients;
import org.apache.hc.core5.http.nio.AsyncRequestProducer;
import org.eclipse.jetty.reactive.client.ReactiveRequest;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.web.reactive.function.client.ReactorNettyHttpClientMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.HttpComponentsClientHttpConnector;
import org.springframework.http.client.reactive.JettyClientHttpConnector;
import org.springframework.http.client.reactive.JettyResourceFactory;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.client.reactive.ReactorResourceFactory;
import reactor.netty.http.client.HttpClient;

@Configuration(proxyBeanMethods=false)
class ClientHttpConnectorConfiguration {
    ClientHttpConnectorConfiguration() {
    }

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnClass(value={HttpAsyncClients.class, AsyncRequestProducer.class})
    @ConditionalOnMissingBean(value={ClientHttpConnector.class})
    static class HttpClient5 {
        HttpClient5() {
        }

        @Bean
        @Lazy
        HttpComponentsClientHttpConnector httpComponentsClientHttpConnector() {
            return new HttpComponentsClientHttpConnector();
        }
    }

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnClass(value={ReactiveRequest.class})
    @ConditionalOnMissingBean(value={ClientHttpConnector.class})
    static class JettyClient {
        JettyClient() {
        }

        @Bean
        @ConditionalOnMissingBean
        JettyResourceFactory jettyClientResourceFactory() {
            return new JettyResourceFactory();
        }

        @Bean
        @Lazy
        JettyClientHttpConnector jettyClientHttpConnector(JettyResourceFactory jettyResourceFactory) {
            SslContextFactory.Client sslContextFactory = new SslContextFactory.Client();
            org.eclipse.jetty.client.HttpClient httpClient = new org.eclipse.jetty.client.HttpClient((SslContextFactory)sslContextFactory);
            return new JettyClientHttpConnector(httpClient, jettyResourceFactory);
        }
    }

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnClass(value={HttpClient.class})
    @ConditionalOnMissingBean(value={ClientHttpConnector.class})
    static class ReactorNetty {
        ReactorNetty() {
        }

        @Bean
        @ConditionalOnMissingBean
        ReactorResourceFactory reactorClientResourceFactory() {
            return new ReactorResourceFactory();
        }

        @Bean
        @Lazy
        ReactorClientHttpConnector reactorClientHttpConnector(ReactorResourceFactory reactorResourceFactory, ObjectProvider<ReactorNettyHttpClientMapper> mapperProvider) {
            ReactorNettyHttpClientMapper mapper = mapperProvider.orderedStream().reduce((before, after) -> client -> after.configure(before.configure(client))).orElse(client -> client);
            return new ReactorClientHttpConnector(reactorResourceFactory, mapper::configure);
        }
    }
}

