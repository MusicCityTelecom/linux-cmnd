/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.eclipse.jetty.server.Connector
 *  org.eclipse.jetty.server.Server
 *  org.springframework.core.log.LogMessage
 *  org.springframework.util.ReflectionUtils
 */
package org.springframework.boot.web.embedded.jetty;

import java.lang.reflect.Method;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.function.Supplier;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.springframework.boot.web.embedded.jetty.JettyWebServer;
import org.springframework.boot.web.server.GracefulShutdownCallback;
import org.springframework.boot.web.server.GracefulShutdownResult;
import org.springframework.core.log.LogMessage;
import org.springframework.util.ReflectionUtils;

final class GracefulShutdown {
    private static final Log logger = LogFactory.getLog(JettyWebServer.class);
    private final Server server;
    private final Supplier<Integer> activeRequests;
    private volatile boolean shuttingDown = false;

    GracefulShutdown(Server server, Supplier<Integer> activeRequests) {
        this.server = server;
        this.activeRequests = activeRequests;
    }

    void shutDownGracefully(GracefulShutdownCallback callback) {
        logger.info((Object)"Commencing graceful shutdown. Waiting for active requests to complete");
        boolean jetty10 = this.isJetty10();
        for (Connector connector : this.server.getConnectors()) {
            this.shutdown(connector, !jetty10);
        }
        this.shuttingDown = true;
        new Thread(() -> this.awaitShutdown(callback), "jetty-shutdown").start();
    }

    private void shutdown(Connector connector, boolean getResult) {
        Future result;
        try {
            result = connector.shutdown();
        }
        catch (NoSuchMethodError ex) {
            Method shutdown = ReflectionUtils.findMethod(connector.getClass(), (String)"shutdown");
            result = (Future)ReflectionUtils.invokeMethod((Method)shutdown, (Object)connector);
        }
        if (getResult) {
            try {
                result.get();
            }
            catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            catch (ExecutionException executionException) {
                // empty catch block
            }
        }
    }

    private boolean isJetty10() {
        try {
            return CompletableFuture.class.equals(Connector.class.getMethod("shutdown", new Class[0]).getReturnType());
        }
        catch (Exception ex) {
            return false;
        }
    }

    private void awaitShutdown(GracefulShutdownCallback callback) {
        while (this.shuttingDown && this.activeRequests.get() > 0) {
            this.sleep(100L);
        }
        this.shuttingDown = false;
        long activeRequests = this.activeRequests.get().intValue();
        if (activeRequests == 0L) {
            logger.info((Object)"Graceful shutdown complete");
            callback.shutdownComplete(GracefulShutdownResult.IDLE);
        } else {
            logger.info((Object)LogMessage.format((String)"Graceful shutdown aborted with %d request(s) still active", (Object)activeRequests));
            callback.shutdownComplete(GracefulShutdownResult.REQUESTS_ACTIVE);
        }
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        }
        catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    void abort() {
        this.shuttingDown = false;
    }
}

