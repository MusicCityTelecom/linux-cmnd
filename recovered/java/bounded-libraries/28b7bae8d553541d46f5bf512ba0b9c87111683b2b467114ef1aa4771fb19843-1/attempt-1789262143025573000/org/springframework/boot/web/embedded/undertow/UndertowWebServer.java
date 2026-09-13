/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.undertow.Undertow
 *  io.undertow.Undertow$Builder
 *  io.undertow.server.HttpHandler
 *  io.undertow.server.HttpServerExchange
 *  io.undertow.server.handlers.GracefulShutdownHandler
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.util.Assert
 *  org.springframework.util.ReflectionUtils
 *  org.springframework.util.StringUtils
 *  org.xnio.channels.BoundChannel
 */
package org.springframework.boot.web.embedded.undertow;

import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.server.handlers.GracefulShutdownHandler;
import java.io.Closeable;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.web.embedded.undertow.HttpHandlerFactory;
import org.springframework.boot.web.server.GracefulShutdownCallback;
import org.springframework.boot.web.server.GracefulShutdownResult;
import org.springframework.boot.web.server.PortInUseException;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.server.WebServerException;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;
import org.xnio.channels.BoundChannel;

public class UndertowWebServer
implements WebServer {
    private static final Log logger = LogFactory.getLog(UndertowWebServer.class);
    private final AtomicReference<GracefulShutdownCallback> gracefulShutdownCallback = new AtomicReference();
    private final Object monitor = new Object();
    private final Undertow.Builder builder;
    private final Iterable<HttpHandlerFactory> httpHandlerFactories;
    private final boolean autoStart;
    private Undertow undertow;
    private volatile boolean started = false;
    private volatile GracefulShutdownHandler gracefulShutdown;
    private volatile List<Closeable> closeables;

    public UndertowWebServer(Undertow.Builder builder, boolean autoStart) {
        this(builder, Collections.singleton(new CloseableHttpHandlerFactory(null)), autoStart);
    }

    public UndertowWebServer(Undertow.Builder builder, Iterable<HttpHandlerFactory> httpHandlerFactories, boolean autoStart) {
        this.builder = builder;
        this.httpHandlerFactories = httpHandlerFactories;
        this.autoStart = autoStart;
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
            try {
                if (!this.autoStart) {
                    return;
                }
                if (this.undertow == null) {
                    this.undertow = this.createUndertowServer();
                }
                this.undertow.start();
                this.started = true;
                String message = this.getStartLogMessage();
                logger.info((Object)message);
            }
            catch (Exception ex) {
                try {
                    PortInUseException.ifPortBindingException(ex, bindException -> {
                        List<Port> failedPorts = this.getConfiguredPorts();
                        failedPorts.removeAll(this.getActualPorts());
                        if (failedPorts.size() == 1) {
                            throw new PortInUseException(failedPorts.get(0).getNumber());
                        }
                    });
                    throw new WebServerException("Unable to start embedded Undertow", ex);
                }
                catch (Throwable throwable) {
                    this.stopSilently();
                    throw throwable;
                }
            }
        }
    }

    private void stopSilently() {
        try {
            if (this.undertow != null) {
                this.undertow.stop();
                this.closeables.forEach(this::closeSilently);
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    private void closeSilently(Closeable closeable) {
        try {
            closeable.close();
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    private Undertow createUndertowServer() {
        this.closeables = new ArrayList<Closeable>();
        this.gracefulShutdown = null;
        HttpHandler handler = this.createHttpHandler();
        this.builder.setHandler(handler);
        return this.builder.build();
    }

    protected HttpHandler createHttpHandler() {
        HttpHandler handler = null;
        for (HttpHandlerFactory factory : this.httpHandlerFactories) {
            handler = factory.getHandler(handler);
            if (handler instanceof Closeable) {
                this.closeables.add((Closeable)handler);
            }
            if (!(handler instanceof GracefulShutdownHandler)) continue;
            Assert.isNull((Object)this.gracefulShutdown, (String)"Only a single GracefulShutdownHandler can be defined");
            this.gracefulShutdown = (GracefulShutdownHandler)handler;
        }
        return handler;
    }

    private String getPortsDescription() {
        List<Port> ports = this.getActualPorts();
        if (!ports.isEmpty()) {
            return StringUtils.collectionToDelimitedString(ports, (String)" ");
        }
        return "unknown";
    }

    private List<Port> getActualPorts() {
        ArrayList<Port> ports = new ArrayList<Port>();
        try {
            if (!this.autoStart) {
                ports.add(new Port(-1, "unknown"));
            } else {
                for (BoundChannel channel : this.extractChannels()) {
                    ports.add(this.getPortFromChannel(channel));
                }
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        return ports;
    }

    private List<BoundChannel> extractChannels() {
        Field channelsField = ReflectionUtils.findField(Undertow.class, (String)"channels");
        ReflectionUtils.makeAccessible((Field)channelsField);
        return (List)ReflectionUtils.getField((Field)channelsField, (Object)this.undertow);
    }

    private Port getPortFromChannel(BoundChannel channel) {
        SocketAddress socketAddress = channel.getLocalAddress();
        if (socketAddress instanceof InetSocketAddress) {
            Field sslField = ReflectionUtils.findField(channel.getClass(), (String)"ssl");
            String protocol = sslField != null ? "https" : "http";
            return new Port(((InetSocketAddress)socketAddress).getPort(), protocol);
        }
        return null;
    }

    private List<Port> getConfiguredPorts() {
        ArrayList<Port> ports = new ArrayList<Port>();
        for (Object listener : this.extractListeners()) {
            try {
                Port port = this.getPortFromListener(listener);
                if (port.getNumber() == 0) continue;
                ports.add(port);
            }
            catch (Exception exception) {}
        }
        return ports;
    }

    private List<Object> extractListeners() {
        Field listenersField = ReflectionUtils.findField(Undertow.class, (String)"listeners");
        ReflectionUtils.makeAccessible((Field)listenersField);
        return (List)ReflectionUtils.getField((Field)listenersField, (Object)this.undertow);
    }

    private Port getPortFromListener(Object listener) {
        Field typeField = ReflectionUtils.findField(listener.getClass(), (String)"type");
        ReflectionUtils.makeAccessible((Field)typeField);
        String protocol = ReflectionUtils.getField((Field)typeField, (Object)listener).toString();
        Field portField = ReflectionUtils.findField(listener.getClass(), (String)"port");
        ReflectionUtils.makeAccessible((Field)portField);
        int port = (Integer)ReflectionUtils.getField((Field)portField, (Object)listener);
        return new Port(port, protocol);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void stop() throws WebServerException {
        Object object = this.monitor;
        synchronized (object) {
            if (!this.started) {
                return;
            }
            this.started = false;
            if (this.gracefulShutdown != null) {
                this.notifyGracefulCallback(false);
            }
            try {
                this.undertow.stop();
                for (Closeable closeable : this.closeables) {
                    closeable.close();
                }
            }
            catch (Exception ex) {
                throw new WebServerException("Unable to stop undertow", ex);
            }
        }
    }

    @Override
    public int getPort() {
        List<Port> ports = this.getActualPorts();
        if (ports.isEmpty()) {
            return -1;
        }
        return ports.get(0).getNumber();
    }

    @Override
    public void shutDownGracefully(GracefulShutdownCallback callback) {
        if (this.gracefulShutdown == null) {
            callback.shutdownComplete(GracefulShutdownResult.IMMEDIATE);
            return;
        }
        logger.info((Object)"Commencing graceful shutdown. Waiting for active requests to complete");
        this.gracefulShutdownCallback.set(callback);
        this.gracefulShutdown.shutdown();
        this.gracefulShutdown.addShutdownListener(this::notifyGracefulCallback);
    }

    private void notifyGracefulCallback(boolean success) {
        GracefulShutdownCallback callback = this.gracefulShutdownCallback.getAndSet(null);
        if (callback != null) {
            if (success) {
                logger.info((Object)"Graceful shutdown complete");
                callback.shutdownComplete(GracefulShutdownResult.IDLE);
            } else {
                logger.info((Object)"Graceful shutdown aborted with one or more requests still active");
                callback.shutdownComplete(GracefulShutdownResult.REQUESTS_ACTIVE);
            }
        }
    }

    protected String getStartLogMessage() {
        return "Undertow started on port(s) " + this.getPortsDescription();
    }

    private static interface CloseableHttpHandler
    extends HttpHandler,
    Closeable {
    }

    private static final class CloseableHttpHandlerFactory
    implements HttpHandlerFactory {
        private final Closeable closeable;

        private CloseableHttpHandlerFactory(Closeable closeable) {
            this.closeable = closeable;
        }

        @Override
        public HttpHandler getHandler(final HttpHandler next) {
            if (this.closeable == null) {
                return next;
            }
            return new CloseableHttpHandler(){

                public void handleRequest(HttpServerExchange exchange) throws Exception {
                    next.handleRequest(exchange);
                }

                @Override
                public void close() throws IOException {
                    closeable.close();
                }
            };
        }
    }

    private static final class Port {
        private final int number;
        private final String protocol;

        private Port(int number, String protocol) {
            this.number = number;
            this.protocol = protocol;
        }

        int getNumber() {
            return this.number;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (this.getClass() != obj.getClass()) {
                return false;
            }
            Port other = (Port)obj;
            return this.number == other.number;
        }

        public int hashCode() {
            return this.number;
        }

        public String toString() {
            return this.number + " (" + this.protocol + ")";
        }
    }
}

