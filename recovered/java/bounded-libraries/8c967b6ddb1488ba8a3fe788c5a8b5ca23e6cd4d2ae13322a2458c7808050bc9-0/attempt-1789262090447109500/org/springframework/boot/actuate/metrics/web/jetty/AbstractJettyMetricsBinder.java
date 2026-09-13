/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.eclipse.jetty.server.Server
 *  org.springframework.boot.context.event.ApplicationStartedEvent
 *  org.springframework.boot.web.context.WebServerApplicationContext
 *  org.springframework.boot.web.embedded.jetty.JettyWebServer
 *  org.springframework.boot.web.server.WebServer
 *  org.springframework.context.ApplicationContext
 *  org.springframework.context.ApplicationListener
 */
package org.springframework.boot.actuate.metrics.web.jetty;

import org.eclipse.jetty.server.Server;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.boot.web.context.WebServerApplicationContext;
import org.springframework.boot.web.embedded.jetty.JettyWebServer;
import org.springframework.boot.web.server.WebServer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;

public abstract class AbstractJettyMetricsBinder
implements ApplicationListener<ApplicationStartedEvent> {
    public void onApplicationEvent(ApplicationStartedEvent event) {
        Server server = this.findServer((ApplicationContext)event.getApplicationContext());
        if (server != null) {
            this.bindMetrics(server);
        }
    }

    private Server findServer(ApplicationContext applicationContext) {
        WebServer webServer;
        if (applicationContext instanceof WebServerApplicationContext && (webServer = ((WebServerApplicationContext)applicationContext).getWebServer()) instanceof JettyWebServer) {
            return ((JettyWebServer)webServer).getServer();
        }
        return null;
    }

    protected abstract void bindMetrics(Server var1);
}

