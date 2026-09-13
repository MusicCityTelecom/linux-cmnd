/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.eclipse.jetty.servlet.ServletContextHandler
 *  org.eclipse.jetty.util.component.LifeCycle
 *  org.eclipse.jetty.util.thread.ShutdownThread
 *  org.eclipse.jetty.webapp.AbstractConfiguration
 *  org.eclipse.jetty.webapp.Configuration
 *  org.eclipse.jetty.webapp.WebAppContext
 *  org.eclipse.jetty.websocket.jsr356.server.ServerContainer
 *  org.eclipse.jetty.websocket.jsr356.server.deploy.WebSocketServerContainerInitializer
 *  org.springframework.boot.web.embedded.jetty.JettyServletWebServerFactory
 *  org.springframework.boot.web.server.WebServerFactoryCustomizer
 *  org.springframework.core.Ordered
 */
package org.springframework.boot.autoconfigure.websocket.servlet;

import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.util.component.LifeCycle;
import org.eclipse.jetty.util.thread.ShutdownThread;
import org.eclipse.jetty.webapp.AbstractConfiguration;
import org.eclipse.jetty.webapp.Configuration;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.websocket.jsr356.server.ServerContainer;
import org.eclipse.jetty.websocket.jsr356.server.deploy.WebSocketServerContainerInitializer;
import org.springframework.boot.web.embedded.jetty.JettyServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.core.Ordered;

public class JettyWebSocketServletWebServerCustomizer
implements WebServerFactoryCustomizer<JettyServletWebServerFactory>,
Ordered {
    public void customize(JettyServletWebServerFactory factory) {
        factory.addConfigurations(new Configuration[]{new AbstractConfiguration(){

            public void configure(WebAppContext context) throws Exception {
                ServerContainer serverContainer = WebSocketServerContainerInitializer.initialize((ServletContextHandler)context);
                ShutdownThread.deregister((LifeCycle)serverContainer);
            }
        }});
    }

    public int getOrder() {
        return 0;
    }
}

