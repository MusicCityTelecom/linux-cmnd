/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.undertow.Undertow
 *  org.apache.catalina.startup.Tomcat
 *  org.eclipse.jetty.server.Server
 *  org.eclipse.jetty.servlet.ServletHolder
 *  org.springframework.beans.factory.ObjectProvider
 *  org.springframework.boot.web.embedded.jetty.JettyReactiveWebServerFactory
 *  org.springframework.boot.web.embedded.jetty.JettyServerCustomizer
 *  org.springframework.boot.web.embedded.netty.NettyReactiveWebServerFactory
 *  org.springframework.boot.web.embedded.netty.NettyRouteProvider
 *  org.springframework.boot.web.embedded.netty.NettyServerCustomizer
 *  org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer
 *  org.springframework.boot.web.embedded.tomcat.TomcatContextCustomizer
 *  org.springframework.boot.web.embedded.tomcat.TomcatProtocolHandlerCustomizer
 *  org.springframework.boot.web.embedded.tomcat.TomcatReactiveWebServerFactory
 *  org.springframework.boot.web.embedded.undertow.UndertowBuilderCustomizer
 *  org.springframework.boot.web.embedded.undertow.UndertowReactiveWebServerFactory
 *  org.springframework.boot.web.reactive.server.ReactiveWebServerFactory
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.http.client.reactive.JettyResourceFactory
 *  org.springframework.http.client.reactive.ReactorResourceFactory
 *  reactor.netty.http.server.HttpServer
 */
package org.springframework.boot.autoconfigure.web.reactive;

import io.undertow.Undertow;
import java.util.stream.Collectors;
import org.apache.catalina.startup.Tomcat;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHolder;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.embedded.jetty.JettyReactiveWebServerFactory;
import org.springframework.boot.web.embedded.jetty.JettyServerCustomizer;
import org.springframework.boot.web.embedded.netty.NettyReactiveWebServerFactory;
import org.springframework.boot.web.embedded.netty.NettyRouteProvider;
import org.springframework.boot.web.embedded.netty.NettyServerCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatContextCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatProtocolHandlerCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatReactiveWebServerFactory;
import org.springframework.boot.web.embedded.undertow.UndertowBuilderCustomizer;
import org.springframework.boot.web.embedded.undertow.UndertowReactiveWebServerFactory;
import org.springframework.boot.web.reactive.server.ReactiveWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.JettyResourceFactory;
import org.springframework.http.client.reactive.ReactorResourceFactory;
import reactor.netty.http.server.HttpServer;

abstract class ReactiveWebServerFactoryConfiguration {
    ReactiveWebServerFactoryConfiguration() {
    }

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnMissingBean(value={ReactiveWebServerFactory.class})
    @ConditionalOnClass(value={Undertow.class})
    static class EmbeddedUndertow {
        EmbeddedUndertow() {
        }

        @Bean
        UndertowReactiveWebServerFactory undertowReactiveWebServerFactory(ObjectProvider<UndertowBuilderCustomizer> builderCustomizers) {
            UndertowReactiveWebServerFactory factory = new UndertowReactiveWebServerFactory();
            factory.getBuilderCustomizers().addAll(builderCustomizers.orderedStream().collect(Collectors.toList()));
            return factory;
        }
    }

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnMissingBean(value={ReactiveWebServerFactory.class})
    @ConditionalOnClass(value={Server.class, ServletHolder.class})
    static class EmbeddedJetty {
        EmbeddedJetty() {
        }

        @Bean
        @ConditionalOnMissingBean
        JettyResourceFactory jettyServerResourceFactory() {
            return new JettyResourceFactory();
        }

        @Bean
        JettyReactiveWebServerFactory jettyReactiveWebServerFactory(JettyResourceFactory resourceFactory, ObjectProvider<JettyServerCustomizer> serverCustomizers) {
            JettyReactiveWebServerFactory serverFactory = new JettyReactiveWebServerFactory();
            serverFactory.getServerCustomizers().addAll(serverCustomizers.orderedStream().collect(Collectors.toList()));
            serverFactory.setResourceFactory(resourceFactory);
            return serverFactory;
        }
    }

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnMissingBean(value={ReactiveWebServerFactory.class})
    @ConditionalOnClass(value={Tomcat.class})
    static class EmbeddedTomcat {
        EmbeddedTomcat() {
        }

        @Bean
        TomcatReactiveWebServerFactory tomcatReactiveWebServerFactory(ObjectProvider<TomcatConnectorCustomizer> connectorCustomizers, ObjectProvider<TomcatContextCustomizer> contextCustomizers, ObjectProvider<TomcatProtocolHandlerCustomizer<?>> protocolHandlerCustomizers) {
            TomcatReactiveWebServerFactory factory = new TomcatReactiveWebServerFactory();
            factory.getTomcatConnectorCustomizers().addAll(connectorCustomizers.orderedStream().collect(Collectors.toList()));
            factory.getTomcatContextCustomizers().addAll(contextCustomizers.orderedStream().collect(Collectors.toList()));
            factory.getTomcatProtocolHandlerCustomizers().addAll(protocolHandlerCustomizers.orderedStream().collect(Collectors.toList()));
            return factory;
        }
    }

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnMissingBean(value={ReactiveWebServerFactory.class})
    @ConditionalOnClass(value={HttpServer.class})
    static class EmbeddedNetty {
        EmbeddedNetty() {
        }

        @Bean
        @ConditionalOnMissingBean
        ReactorResourceFactory reactorServerResourceFactory() {
            return new ReactorResourceFactory();
        }

        @Bean
        NettyReactiveWebServerFactory nettyReactiveWebServerFactory(ReactorResourceFactory resourceFactory, ObjectProvider<NettyRouteProvider> routes, ObjectProvider<NettyServerCustomizer> serverCustomizers) {
            NettyReactiveWebServerFactory serverFactory = new NettyReactiveWebServerFactory();
            serverFactory.setResourceFactory(resourceFactory);
            routes.orderedStream().forEach(xva$0 -> serverFactory.addRouteProviders(new NettyRouteProvider[]{xva$0}));
            serverFactory.getServerCustomizers().addAll(serverCustomizers.orderedStream().collect(Collectors.toList()));
            return serverFactory;
        }
    }
}

