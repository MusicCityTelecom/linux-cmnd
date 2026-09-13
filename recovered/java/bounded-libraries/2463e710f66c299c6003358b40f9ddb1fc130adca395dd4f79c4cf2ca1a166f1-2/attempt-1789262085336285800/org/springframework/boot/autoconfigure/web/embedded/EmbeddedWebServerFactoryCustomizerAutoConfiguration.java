/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.undertow.Undertow
 *  org.apache.catalina.startup.Tomcat
 *  org.apache.coyote.UpgradeProtocol
 *  org.eclipse.jetty.server.Server
 *  org.eclipse.jetty.util.Loader
 *  org.eclipse.jetty.webapp.WebAppContext
 *  org.springframework.boot.context.properties.EnableConfigurationProperties
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.core.env.Environment
 *  org.xnio.SslClientAuthMode
 *  reactor.netty.http.server.HttpServer
 */
package org.springframework.boot.autoconfigure.web.embedded;

import io.undertow.Undertow;
import org.apache.catalina.startup.Tomcat;
import org.apache.coyote.UpgradeProtocol;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.Loader;
import org.eclipse.jetty.webapp.WebAppContext;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.embedded.JettyWebServerFactoryCustomizer;
import org.springframework.boot.autoconfigure.web.embedded.NettyWebServerFactoryCustomizer;
import org.springframework.boot.autoconfigure.web.embedded.TomcatWebServerFactoryCustomizer;
import org.springframework.boot.autoconfigure.web.embedded.UndertowWebServerFactoryCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.xnio.SslClientAuthMode;
import reactor.netty.http.server.HttpServer;

@AutoConfiguration
@ConditionalOnWebApplication
@EnableConfigurationProperties(value={ServerProperties.class})
public class EmbeddedWebServerFactoryCustomizerAutoConfiguration {

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnClass(value={HttpServer.class})
    public static class NettyWebServerFactoryCustomizerConfiguration {
        @Bean
        public NettyWebServerFactoryCustomizer nettyWebServerFactoryCustomizer(Environment environment, ServerProperties serverProperties) {
            return new NettyWebServerFactoryCustomizer(environment, serverProperties);
        }
    }

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnClass(value={Undertow.class, SslClientAuthMode.class})
    public static class UndertowWebServerFactoryCustomizerConfiguration {
        @Bean
        public UndertowWebServerFactoryCustomizer undertowWebServerFactoryCustomizer(Environment environment, ServerProperties serverProperties) {
            return new UndertowWebServerFactoryCustomizer(environment, serverProperties);
        }
    }

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnClass(value={Server.class, Loader.class, WebAppContext.class})
    public static class JettyWebServerFactoryCustomizerConfiguration {
        @Bean
        public JettyWebServerFactoryCustomizer jettyWebServerFactoryCustomizer(Environment environment, ServerProperties serverProperties) {
            return new JettyWebServerFactoryCustomizer(environment, serverProperties);
        }
    }

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnClass(value={Tomcat.class, UpgradeProtocol.class})
    public static class TomcatWebServerFactoryCustomizerConfiguration {
        @Bean
        public TomcatWebServerFactoryCustomizer tomcatWebServerFactoryCustomizer(Environment environment, ServerProperties serverProperties) {
            return new TomcatWebServerFactoryCustomizer(environment, serverProperties);
        }
    }
}

