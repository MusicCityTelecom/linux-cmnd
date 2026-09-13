/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.undertow.websockets.jsr.Bootstrap
 *  javax.servlet.Servlet
 *  javax.websocket.server.ServerContainer
 *  org.apache.catalina.startup.Tomcat
 *  org.apache.tomcat.websocket.server.WsSci
 *  org.eclipse.jetty.websocket.jsr356.server.deploy.WebSocketServerContainerInitializer
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Configuration
 */
package org.springframework.boot.autoconfigure.websocket.servlet;

import io.undertow.websockets.jsr.Bootstrap;
import javax.servlet.Servlet;
import javax.websocket.server.ServerContainer;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.websocket.server.WsSci;
import org.eclipse.jetty.websocket.jsr356.server.deploy.WebSocketServerContainerInitializer;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.servlet.ServletWebServerFactoryAutoConfiguration;
import org.springframework.boot.autoconfigure.websocket.servlet.Jetty10WebSocketServletWebServerCustomizer;
import org.springframework.boot.autoconfigure.websocket.servlet.JettyWebSocketServletWebServerCustomizer;
import org.springframework.boot.autoconfigure.websocket.servlet.TomcatWebSocketServletWebServerCustomizer;
import org.springframework.boot.autoconfigure.websocket.servlet.UndertowWebSocketServletWebServerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@AutoConfiguration(before={ServletWebServerFactoryAutoConfiguration.class})
@ConditionalOnClass(value={Servlet.class, ServerContainer.class})
@ConditionalOnWebApplication(type=ConditionalOnWebApplication.Type.SERVLET)
public class WebSocketServletAutoConfiguration {

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnClass(value={Bootstrap.class})
    static class UndertowWebSocketConfiguration {
        UndertowWebSocketConfiguration() {
        }

        @Bean
        @ConditionalOnMissingBean(name={"websocketServletWebServerCustomizer"})
        UndertowWebSocketServletWebServerCustomizer websocketServletWebServerCustomizer() {
            return new UndertowWebSocketServletWebServerCustomizer();
        }
    }

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnClass(name={"org.eclipse.jetty.websocket.javax.server.internal.JavaxWebSocketServerContainer", "org.eclipse.jetty.websocket.server.JettyWebSocketServerContainer"})
    static class Jetty10WebSocketConfiguration {
        Jetty10WebSocketConfiguration() {
        }

        @Bean
        @ConditionalOnMissingBean(name={"websocketServletWebServerCustomizer"})
        Jetty10WebSocketServletWebServerCustomizer websocketServletWebServerCustomizer() {
            return new Jetty10WebSocketServletWebServerCustomizer();
        }
    }

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnClass(value={WebSocketServerContainerInitializer.class})
    static class JettyWebSocketConfiguration {
        JettyWebSocketConfiguration() {
        }

        @Bean
        @ConditionalOnMissingBean(name={"websocketServletWebServerCustomizer"})
        JettyWebSocketServletWebServerCustomizer websocketServletWebServerCustomizer() {
            return new JettyWebSocketServletWebServerCustomizer();
        }
    }

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnClass(value={Tomcat.class, WsSci.class})
    static class TomcatWebSocketConfiguration {
        TomcatWebSocketConfiguration() {
        }

        @Bean
        @ConditionalOnMissingBean(name={"websocketServletWebServerCustomizer"})
        TomcatWebSocketServletWebServerCustomizer websocketServletWebServerCustomizer() {
            return new TomcatWebSocketServletWebServerCustomizer();
        }
    }
}

