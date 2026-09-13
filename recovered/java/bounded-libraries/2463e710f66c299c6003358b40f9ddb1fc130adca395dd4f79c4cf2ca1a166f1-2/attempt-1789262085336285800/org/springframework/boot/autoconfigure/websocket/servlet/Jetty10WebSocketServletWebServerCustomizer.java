/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletContext
 *  org.eclipse.jetty.server.Server
 *  org.eclipse.jetty.server.handler.ContextHandler$Context
 *  org.eclipse.jetty.webapp.AbstractConfiguration
 *  org.eclipse.jetty.webapp.Configuration
 *  org.eclipse.jetty.webapp.WebAppContext
 *  org.springframework.boot.web.embedded.jetty.JettyServletWebServerFactory
 *  org.springframework.boot.web.server.WebServerFactoryCustomizer
 *  org.springframework.core.Ordered
 *  org.springframework.util.ClassUtils
 *  org.springframework.util.ReflectionUtils
 */
package org.springframework.boot.autoconfigure.websocket.servlet;

import java.lang.reflect.Method;
import javax.servlet.ServletContext;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.webapp.AbstractConfiguration;
import org.eclipse.jetty.webapp.Configuration;
import org.eclipse.jetty.webapp.WebAppContext;
import org.springframework.boot.web.embedded.jetty.JettyServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.core.Ordered;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

class Jetty10WebSocketServletWebServerCustomizer
implements WebServerFactoryCustomizer<JettyServletWebServerFactory>,
Ordered {
    static final String JETTY_WEB_SOCKET_SERVER_CONTAINER = "org.eclipse.jetty.websocket.server.JettyWebSocketServerContainer";
    static final String JAVAX_WEB_SOCKET_SERVER_CONTAINER = "org.eclipse.jetty.websocket.javax.server.internal.JavaxWebSocketServerContainer";

    Jetty10WebSocketServletWebServerCustomizer() {
    }

    public void customize(JettyServletWebServerFactory factory) {
        factory.addConfigurations(new Configuration[]{new AbstractConfiguration(){

            public void configure(WebAppContext context) throws Exception {
                ContextHandler.Context servletContext = context.getServletContext();
                Class jettyContainer = ClassUtils.forName((String)Jetty10WebSocketServletWebServerCustomizer.JETTY_WEB_SOCKET_SERVER_CONTAINER, null);
                Method getJettyContainer = ReflectionUtils.findMethod((Class)jettyContainer, (String)"getContainer", (Class[])new Class[]{ServletContext.class});
                Server server = context.getServer();
                if (ReflectionUtils.invokeMethod((Method)getJettyContainer, null, (Object[])new Object[]{servletContext}) == null) {
                    this.ensureWebSocketComponents(server, (ServletContext)servletContext);
                    this.ensureContainer(jettyContainer, (ServletContext)servletContext);
                }
                Class javaxContainer = ClassUtils.forName((String)Jetty10WebSocketServletWebServerCustomizer.JAVAX_WEB_SOCKET_SERVER_CONTAINER, null);
                Method getJavaxContainer = ReflectionUtils.findMethod((Class)javaxContainer, (String)"getContainer", (Class[])new Class[]{ServletContext.class});
                if (ReflectionUtils.invokeMethod((Method)getJavaxContainer, (Object)"getContainer", (Object[])new Object[]{servletContext}) == null) {
                    this.ensureWebSocketComponents(server, (ServletContext)servletContext);
                    this.ensureUpgradeFilter((ServletContext)servletContext);
                    this.ensureMappings((ServletContext)servletContext);
                    this.ensureContainer(javaxContainer, (ServletContext)servletContext);
                }
            }

            private void ensureWebSocketComponents(Server server, ServletContext servletContext) throws ClassNotFoundException {
                Class webSocketServerComponents = ClassUtils.forName((String)"org.eclipse.jetty.websocket.core.server.WebSocketServerComponents", null);
                Method ensureWebSocketComponents = ReflectionUtils.findMethod((Class)webSocketServerComponents, (String)"ensureWebSocketComponents", (Class[])new Class[]{Server.class, ServletContext.class});
                ReflectionUtils.invokeMethod((Method)ensureWebSocketComponents, null, (Object[])new Object[]{server, servletContext});
            }

            private void ensureContainer(Class<?> container, ServletContext servletContext) {
                Method ensureContainer = ReflectionUtils.findMethod(container, (String)"ensureContainer", (Class[])new Class[]{ServletContext.class});
                ReflectionUtils.invokeMethod((Method)ensureContainer, null, (Object[])new Object[]{servletContext});
            }

            private void ensureUpgradeFilter(ServletContext servletContext) throws ClassNotFoundException {
                Class webSocketUpgradeFilter = ClassUtils.forName((String)"org.eclipse.jetty.websocket.servlet.WebSocketUpgradeFilter", null);
                Method ensureFilter = ReflectionUtils.findMethod((Class)webSocketUpgradeFilter, (String)"ensureFilter", (Class[])new Class[]{ServletContext.class});
                ReflectionUtils.invokeMethod((Method)ensureFilter, null, (Object[])new Object[]{servletContext});
            }

            private void ensureMappings(ServletContext servletContext) throws ClassNotFoundException {
                Class webSocketMappings = ClassUtils.forName((String)"org.eclipse.jetty.websocket.core.server.WebSocketMappings", null);
                Method ensureMappings = ReflectionUtils.findMethod((Class)webSocketMappings, (String)"ensureMappings", (Class[])new Class[]{ServletContext.class});
                ReflectionUtils.invokeMethod((Method)ensureMappings, null, (Object[])new Object[]{servletContext});
            }
        }});
    }

    public int getOrder() {
        return 0;
    }
}

