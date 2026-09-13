/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.micrometer.core.instrument.MeterRegistry
 *  io.micrometer.core.instrument.Tag
 *  io.micrometer.core.instrument.binder.tomcat.TomcatMetrics
 *  org.apache.catalina.Container
 *  org.apache.catalina.Context
 *  org.apache.catalina.Manager
 *  org.springframework.beans.factory.DisposableBean
 *  org.springframework.boot.context.event.ApplicationStartedEvent
 *  org.springframework.boot.web.context.WebServerApplicationContext
 *  org.springframework.boot.web.embedded.tomcat.TomcatWebServer
 *  org.springframework.boot.web.server.WebServer
 *  org.springframework.context.ApplicationContext
 *  org.springframework.context.ApplicationListener
 *  org.springframework.context.ConfigurableApplicationContext
 */
package org.springframework.boot.actuate.metrics.web.tomcat;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.binder.tomcat.TomcatMetrics;
import java.util.Collections;
import org.apache.catalina.Container;
import org.apache.catalina.Context;
import org.apache.catalina.Manager;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.boot.web.context.WebServerApplicationContext;
import org.springframework.boot.web.embedded.tomcat.TomcatWebServer;
import org.springframework.boot.web.server.WebServer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;

public class TomcatMetricsBinder
implements ApplicationListener<ApplicationStartedEvent>,
DisposableBean {
    private final MeterRegistry meterRegistry;
    private final Iterable<Tag> tags;
    private volatile TomcatMetrics tomcatMetrics;

    public TomcatMetricsBinder(MeterRegistry meterRegistry) {
        this(meterRegistry, Collections.emptyList());
    }

    public TomcatMetricsBinder(MeterRegistry meterRegistry, Iterable<Tag> tags) {
        this.meterRegistry = meterRegistry;
        this.tags = tags;
    }

    public void onApplicationEvent(ApplicationStartedEvent event) {
        ConfigurableApplicationContext applicationContext = event.getApplicationContext();
        Manager manager = this.findManager((ApplicationContext)applicationContext);
        this.tomcatMetrics = new TomcatMetrics(manager, this.tags);
        this.tomcatMetrics.bindTo(this.meterRegistry);
    }

    private Manager findManager(ApplicationContext applicationContext) {
        Context context;
        WebServer webServer;
        if (applicationContext instanceof WebServerApplicationContext && (webServer = ((WebServerApplicationContext)applicationContext).getWebServer()) instanceof TomcatWebServer && (context = this.findContext((TomcatWebServer)webServer)) != null) {
            return context.getManager();
        }
        return null;
    }

    private Context findContext(TomcatWebServer tomcatWebServer) {
        for (Container container : tomcatWebServer.getTomcat().getHost().findChildren()) {
            if (!(container instanceof Context)) continue;
            return (Context)container;
        }
        return null;
    }

    public void destroy() {
        if (this.tomcatMetrics != null) {
            this.tomcatMetrics.close();
        }
    }
}

