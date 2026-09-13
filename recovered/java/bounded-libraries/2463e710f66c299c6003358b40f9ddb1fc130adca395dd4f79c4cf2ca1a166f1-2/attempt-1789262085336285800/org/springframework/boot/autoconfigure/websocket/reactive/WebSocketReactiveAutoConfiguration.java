/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.Servlet
 *  javax.websocket.server.ServerContainer
 *  org.apache.catalina.startup.Tomcat
 *  org.apache.tomcat.websocket.server.WsSci
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Configuration
 */
package org.springframework.boot.autoconfigure.websocket.reactive;

import javax.servlet.Servlet;
import javax.websocket.server.ServerContainer;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.websocket.server.WsSci;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.reactive.ReactiveWebServerFactoryAutoConfiguration;
import org.springframework.boot.autoconfigure.websocket.reactive.TomcatWebSocketReactiveWebServerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@AutoConfiguration(before={ReactiveWebServerFactoryAutoConfiguration.class})
@ConditionalOnClass(value={Servlet.class, ServerContainer.class})
@ConditionalOnWebApplication(type=ConditionalOnWebApplication.Type.REACTIVE)
public class WebSocketReactiveAutoConfiguration {

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnClass(value={Tomcat.class, WsSci.class})
    static class TomcatWebSocketConfiguration {
        TomcatWebSocketConfiguration() {
        }

        @Bean
        @ConditionalOnMissingBean(name={"websocketReactiveWebServerCustomizer"})
        TomcatWebSocketReactiveWebServerCustomizer websocketReactiveWebServerCustomizer() {
            return new TomcatWebSocketReactiveWebServerCustomizer();
        }
    }
}

