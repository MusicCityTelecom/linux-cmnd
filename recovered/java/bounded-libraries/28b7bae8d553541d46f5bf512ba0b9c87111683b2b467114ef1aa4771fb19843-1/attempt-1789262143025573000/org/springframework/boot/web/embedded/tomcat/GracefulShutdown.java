/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.catalina.Container
 *  org.apache.catalina.Service
 *  org.apache.catalina.connector.Connector
 *  org.apache.catalina.core.StandardContext
 *  org.apache.catalina.core.StandardWrapper
 *  org.apache.catalina.startup.Tomcat
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 */
package org.springframework.boot.web.embedded.tomcat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.catalina.Container;
import org.apache.catalina.Service;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.core.StandardWrapper;
import org.apache.catalina.startup.Tomcat;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.web.server.GracefulShutdownCallback;
import org.springframework.boot.web.server.GracefulShutdownResult;

final class GracefulShutdown {
    private static final Log logger = LogFactory.getLog(GracefulShutdown.class);
    private final Tomcat tomcat;
    private volatile boolean aborted = false;

    GracefulShutdown(Tomcat tomcat) {
        this.tomcat = tomcat;
    }

    void shutDownGracefully(GracefulShutdownCallback callback) {
        logger.info((Object)"Commencing graceful shutdown. Waiting for active requests to complete");
        new Thread(() -> this.doShutdown(callback), "tomcat-shutdown").start();
    }

    private void doShutdown(GracefulShutdownCallback callback) {
        List<Connector> connectors = this.getConnectors();
        connectors.forEach(this::close);
        try {
            for (Container host : this.tomcat.getEngine().findChildren()) {
                for (Container context : host.findChildren()) {
                    while (this.isActive(context)) {
                        if (this.aborted) {
                            logger.info((Object)"Graceful shutdown aborted with one or more requests still active");
                            callback.shutdownComplete(GracefulShutdownResult.REQUESTS_ACTIVE);
                            return;
                        }
                        Thread.sleep(50L);
                    }
                }
            }
        }
        catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        logger.info((Object)"Graceful shutdown complete");
        callback.shutdownComplete(GracefulShutdownResult.IDLE);
    }

    private List<Connector> getConnectors() {
        ArrayList<Connector> connectors = new ArrayList<Connector>();
        for (Service service : this.tomcat.getServer().findServices()) {
            Collections.addAll(connectors, service.findConnectors());
        }
        return connectors;
    }

    private void close(Connector connector) {
        connector.pause();
        connector.getProtocolHandler().closeServerSocketGraceful();
    }

    private boolean isActive(Container context) {
        try {
            if (((StandardContext)context).getInProgressAsyncCount() > 0L) {
                return true;
            }
            for (Container wrapper : context.findChildren()) {
                if (((StandardWrapper)wrapper).getCountAllocated() <= 0) continue;
                return true;
            }
            return false;
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    void abort() {
        this.aborted = true;
    }
}

