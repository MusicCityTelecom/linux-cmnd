/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.eclipse.jetty.server.Connector
 *  org.eclipse.jetty.server.Handler
 *  org.eclipse.jetty.server.NetworkConnector
 *  org.eclipse.jetty.server.Server
 *  org.eclipse.jetty.server.handler.ContextHandler
 *  org.eclipse.jetty.server.handler.HandlerCollection
 *  org.eclipse.jetty.server.handler.HandlerWrapper
 *  org.eclipse.jetty.server.handler.StatisticsHandler
 *  org.springframework.util.Assert
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.web.embedded.jetty;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.NetworkConnector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.HandlerWrapper;
import org.eclipse.jetty.server.handler.StatisticsHandler;
import org.springframework.boot.web.embedded.jetty.GracefulShutdown;
import org.springframework.boot.web.embedded.jetty.JettyEmbeddedWebAppContext;
import org.springframework.boot.web.server.GracefulShutdownCallback;
import org.springframework.boot.web.server.GracefulShutdownResult;
import org.springframework.boot.web.server.PortInUseException;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.server.WebServerException;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

public class JettyWebServer
implements WebServer {
    private static final Log logger = LogFactory.getLog(JettyWebServer.class);
    private final Object monitor = new Object();
    private final Server server;
    private final boolean autoStart;
    private final GracefulShutdown gracefulShutdown;
    private Connector[] connectors;
    private volatile boolean started;

    public JettyWebServer(Server server) {
        this(server, true);
    }

    public JettyWebServer(Server server, boolean autoStart) {
        this.autoStart = autoStart;
        Assert.notNull((Object)server, (String)"Jetty Server must not be null");
        this.server = server;
        this.gracefulShutdown = this.createGracefulShutdown(server);
        this.initialize();
    }

    private GracefulShutdown createGracefulShutdown(Server server) {
        StatisticsHandler statisticsHandler = this.findStatisticsHandler(server);
        if (statisticsHandler == null) {
            return null;
        }
        return new GracefulShutdown(server, () -> ((StatisticsHandler)statisticsHandler).getRequestsActive());
    }

    private StatisticsHandler findStatisticsHandler(Server server) {
        return this.findStatisticsHandler(server.getHandler());
    }

    private StatisticsHandler findStatisticsHandler(Handler handler) {
        if (handler instanceof StatisticsHandler) {
            return (StatisticsHandler)handler;
        }
        if (handler instanceof HandlerWrapper) {
            return this.findStatisticsHandler(((HandlerWrapper)handler).getHandler());
        }
        return null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void initialize() {
        Object object = this.monitor;
        synchronized (object) {
            try {
                this.connectors = this.server.getConnectors();
                this.server.setConnectors(null);
                this.server.start();
                this.server.setStopAtShutdown(false);
            }
            catch (Throwable ex) {
                this.stopSilently();
                throw new WebServerException("Unable to start embedded Jetty web server", ex);
            }
        }
    }

    private void stopSilently() {
        try {
            this.server.stop();
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void start() throws WebServerException {
        Object object = this.monitor;
        synchronized (object) {
            if (this.started) {
                return;
            }
            this.server.setConnectors(this.connectors);
            if (!this.autoStart) {
                return;
            }
            try {
                Connector[] connectors;
                this.server.start();
                for (Handler handler : this.server.getHandlers()) {
                    this.handleDeferredInitialize(handler);
                }
                for (Connector connector : connectors = this.server.getConnectors()) {
                    try {
                        connector.start();
                    }
                    catch (IOException ex) {
                        if (connector instanceof NetworkConnector) {
                            PortInUseException.throwIfPortBindingException(ex, () -> ((NetworkConnector)connector).getPort());
                        }
                        throw ex;
                    }
                }
                this.started = true;
                logger.info((Object)("Jetty started on port(s) " + this.getActualPortsDescription() + " with context path '" + this.getContextPath() + "'"));
            }
            catch (WebServerException ex) {
                this.stopSilently();
                throw ex;
            }
            catch (Exception ex) {
                this.stopSilently();
                throw new WebServerException("Unable to start embedded Jetty server", ex);
            }
        }
    }

    private String getActualPortsDescription() {
        StringBuilder ports = new StringBuilder();
        for (Connector connector : this.server.getConnectors()) {
            if (ports.length() != 0) {
                ports.append(", ");
            }
            ports.append(this.getLocalPort(connector)).append(this.getProtocols(connector));
        }
        return ports.toString();
    }

    private String getProtocols(Connector connector) {
        List protocols = connector.getProtocols();
        return " (" + StringUtils.collectionToDelimitedString((Collection)protocols, (String)", ") + ")";
    }

    private String getContextPath() {
        return Arrays.stream(this.server.getHandlers()).map(this::findContextHandler).filter(Objects::nonNull).map(ContextHandler::getContextPath).collect(Collectors.joining(" "));
    }

    private ContextHandler findContextHandler(Handler handler) {
        while (handler instanceof HandlerWrapper) {
            if (handler instanceof ContextHandler) {
                return (ContextHandler)handler;
            }
            handler = ((HandlerWrapper)handler).getHandler();
        }
        return null;
    }

    private void handleDeferredInitialize(Handler ... handlers) throws Exception {
        for (Handler handler : handlers) {
            if (handler instanceof JettyEmbeddedWebAppContext) {
                ((JettyEmbeddedWebAppContext)handler).deferredInitialize();
                continue;
            }
            if (handler instanceof HandlerWrapper) {
                this.handleDeferredInitialize(((HandlerWrapper)handler).getHandler());
                continue;
            }
            if (!(handler instanceof HandlerCollection)) continue;
            this.handleDeferredInitialize(((HandlerCollection)handler).getHandlers());
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void stop() {
        Object object = this.monitor;
        synchronized (object) {
            this.started = false;
            if (this.gracefulShutdown != null) {
                this.gracefulShutdown.abort();
            }
            try {
                this.server.stop();
            }
            catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            catch (Exception ex) {
                throw new WebServerException("Unable to stop embedded Jetty server", ex);
            }
        }
    }

    @Override
    public int getPort() {
        Connector[] connectors;
        for (Connector connector : connectors = this.server.getConnectors()) {
            Integer localPort = this.getLocalPort(connector);
            if (localPort == null || localPort <= 0) continue;
            return localPort;
        }
        return -1;
    }

    private Integer getLocalPort(Connector connector) {
        if (connector instanceof NetworkConnector) {
            return ((NetworkConnector)connector).getLocalPort();
        }
        return 0;
    }

    @Override
    public void shutDownGracefully(GracefulShutdownCallback callback) {
        if (this.gracefulShutdown == null) {
            callback.shutdownComplete(GracefulShutdownResult.IMMEDIATE);
            return;
        }
        this.gracefulShutdown.shutDownGracefully(callback);
    }

    public Server getServer() {
        return this.server;
    }
}

