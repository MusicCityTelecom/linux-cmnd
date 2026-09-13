/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.undertow.Undertow
 *  javax.servlet.Servlet
 *  org.apache.catalina.startup.Tomcat
 *  org.apache.coyote.UpgradeProtocol
 *  org.eclipse.jetty.server.Server
 *  org.eclipse.jetty.util.Loader
 *  org.eclipse.jetty.webapp.WebAppContext
 *  org.springframework.beans.factory.ObjectProvider
 *  org.springframework.boot.web.embedded.jetty.JettyServerCustomizer
 *  org.springframework.boot.web.embedded.jetty.JettyServletWebServerFactory
 *  org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer
 *  org.springframework.boot.web.embedded.tomcat.TomcatContextCustomizer
 *  org.springframework.boot.web.embedded.tomcat.TomcatProtocolHandlerCustomizer
 *  org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory
 *  org.springframework.boot.web.embedded.undertow.UndertowBuilderCustomizer
 *  org.springframework.boot.web.embedded.undertow.UndertowDeploymentInfoCustomizer
 *  org.springframework.boot.web.embedded.undertow.UndertowServletWebServerFactory
 *  org.springframework.boot.web.servlet.server.ServletWebServerFactory
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Configuration
 *  org.xnio.SslClientAuthMode
 */
package org.springframework.boot.autoconfigure.web.servlet;

import io.undertow.Undertow;
import java.util.stream.Collectors;
import javax.servlet.Servlet;
import org.apache.catalina.startup.Tomcat;
import org.apache.coyote.UpgradeProtocol;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.Loader;
import org.eclipse.jetty.webapp.WebAppContext;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.SearchStrategy;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.UndertowServletWebServerFactoryCustomizer;
import org.springframework.boot.web.embedded.jetty.JettyServerCustomizer;
import org.springframework.boot.web.embedded.jetty.JettyServletWebServerFactory;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatContextCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatProtocolHandlerCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.embedded.undertow.UndertowBuilderCustomizer;
import org.springframework.boot.web.embedded.undertow.UndertowDeploymentInfoCustomizer;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.xnio.SslClientAuthMode;

@Configuration(proxyBeanMethods=false)
class ServletWebServerFactoryConfiguration {
    ServletWebServerFactoryConfiguration() {
    }

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnClass(value={Servlet.class, Undertow.class, SslClientAuthMode.class})
    @ConditionalOnMissingBean(value={ServletWebServerFactory.class}, search=SearchStrategy.CURRENT)
    static class EmbeddedUndertow {
        EmbeddedUndertow() {
        }

        @Bean
        UndertowServletWebServerFactory undertowServletWebServerFactory(ObjectProvider<UndertowDeploymentInfoCustomizer> deploymentInfoCustomizers, ObjectProvider<UndertowBuilderCustomizer> builderCustomizers) {
            UndertowServletWebServerFactory factory = new UndertowServletWebServerFactory();
            factory.getDeploymentInfoCustomizers().addAll(deploymentInfoCustomizers.orderedStream().collect(Collectors.toList()));
            factory.getBuilderCustomizers().addAll(builderCustomizers.orderedStream().collect(Collectors.toList()));
            return factory;
        }

        @Bean
        UndertowServletWebServerFactoryCustomizer undertowServletWebServerFactoryCustomizer(ServerProperties serverProperties) {
            return new UndertowServletWebServerFactoryCustomizer(serverProperties);
        }
    }

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnClass(value={Servlet.class, Server.class, Loader.class, WebAppContext.class})
    @ConditionalOnMissingBean(value={ServletWebServerFactory.class}, search=SearchStrategy.CURRENT)
    static class EmbeddedJetty {
        EmbeddedJetty() {
        }

        @Bean
        JettyServletWebServerFactory JettyServletWebServerFactory(ObjectProvider<JettyServerCustomizer> serverCustomizers) {
            JettyServletWebServerFactory factory = new JettyServletWebServerFactory();
            factory.getServerCustomizers().addAll(serverCustomizers.orderedStream().collect(Collectors.toList()));
            return factory;
        }
    }

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnClass(value={Servlet.class, Tomcat.class, UpgradeProtocol.class})
    @ConditionalOnMissingBean(value={ServletWebServerFactory.class}, search=SearchStrategy.CURRENT)
    static class EmbeddedTomcat {
        EmbeddedTomcat() {
        }

        @Bean
        TomcatServletWebServerFactory tomcatServletWebServerFactory(ObjectProvider<TomcatConnectorCustomizer> connectorCustomizers, ObjectProvider<TomcatContextCustomizer> contextCustomizers, ObjectProvider<TomcatProtocolHandlerCustomizer<?>> protocolHandlerCustomizers) {
            TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
            factory.getTomcatConnectorCustomizers().addAll(connectorCustomizers.orderedStream().collect(Collectors.toList()));
            factory.getTomcatContextCustomizers().addAll(contextCustomizers.orderedStream().collect(Collectors.toList()));
            factory.getTomcatProtocolHandlerCustomizers().addAll(protocolHandlerCustomizers.orderedStream().collect(Collectors.toList()));
            return factory;
        }
    }
}

