/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.catalina.Container
 *  org.apache.catalina.Context
 *  org.apache.catalina.Engine
 *  org.apache.catalina.LifecycleException
 *  org.apache.catalina.LifecycleState
 *  org.apache.catalina.Service
 *  org.apache.catalina.connector.Connector
 *  org.apache.catalina.core.StandardContext
 *  org.apache.catalina.startup.Tomcat
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.apache.naming.ContextBindings
 *  org.springframework.util.Assert
 */
package org.springframework.boot.web.embedded.tomcat;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import javax.naming.NamingException;
import org.apache.catalina.Container;
import org.apache.catalina.Context;
import org.apache.catalina.Engine;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.LifecycleState;
import org.apache.catalina.Service;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.naming.ContextBindings;
import org.springframework.boot.web.embedded.tomcat.ConnectorStartFailedException;
import org.springframework.boot.web.embedded.tomcat.GracefulShutdown;
import org.springframework.boot.web.embedded.tomcat.TomcatEmbeddedContext;
import org.springframework.boot.web.embedded.tomcat.TomcatEmbeddedWebappClassLoader;
import org.springframework.boot.web.embedded.tomcat.TomcatStarter;
import org.springframework.boot.web.server.GracefulShutdownCallback;
import org.springframework.boot.web.server.GracefulShutdownResult;
import org.springframework.boot.web.server.PortInUseException;
import org.springframework.boot.web.server.Shutdown;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.server.WebServerException;
import org.springframework.util.Assert;

public class TomcatWebServer
implements WebServer {
    private static final Log logger = LogFactory.getLog(TomcatWebServer.class);
    private static final AtomicInteger containerCounter = new AtomicInteger(-1);
    private final Object monitor = new Object();
    private final Map<Service, Connector[]> serviceConnectors = new HashMap<Service, Connector[]>();
    private final Tomcat tomcat;
    private final boolean autoStart;
    private final GracefulShutdown gracefulShutdown;
    private volatile boolean started;

    public TomcatWebServer(Tomcat tomcat) {
        this(tomcat, true);
    }

    public TomcatWebServer(Tomcat tomcat, boolean autoStart) {
        this(tomcat, autoStart, Shutdown.IMMEDIATE);
    }

    public TomcatWebServer(Tomcat tomcat, boolean autoStart, Shutdown shutdown) {
        Assert.notNull((Object)tomcat, (String)"Tomcat Server must not be null");
        this.tomcat = tomcat;
        this.autoStart = autoStart;
        this.gracefulShutdown = shutdown == Shutdown.GRACEFUL ? new GracefulShutdown(tomcat) : null;
        this.initialize();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void initialize() throws WebServerException {
        logger.info((Object)("Tomcat initialized with port(s): " + this.getPortsDescription(false)));
        Object object = this.monitor;
        synchronized (object) {
            try {
                this.addInstanceIdToEngineName();
                Context context = this.findContext();
                context.addLifecycleListener(event -> {
                    if (context.equals(event.getSource()) && "start".equals(event.getType())) {
                        this.removeServiceConnectors();
                    }
                });
                this.tomcat.start();
                this.rethrowDeferredStartupExceptions();
                try {
                    ContextBindings.bindClassLoader((Object)context, (Object)context.getNamingToken(), (ClassLoader)this.getClass().getClassLoader());
                }
                catch (NamingException namingException) {
                    // empty catch block
                }
                this.startDaemonAwaitThread();
            }
            catch (Exception ex) {
                this.stopSilently();
                this.destroySilently();
                throw new WebServerException("Unable to start embedded Tomcat", ex);
            }
        }
    }

    private Context findContext() {
        for (Container child : this.tomcat.getHost().findChildren()) {
            if (!(child instanceof Context)) continue;
            return (Context)child;
        }
        throw new IllegalStateException("The host does not contain a Context");
    }

    private void addInstanceIdToEngineName() {
        int instanceId = containerCounter.incrementAndGet();
        if (instanceId > 0) {
            Engine engine = this.tomcat.getEngine();
            engine.setName(engine.getName() + "-" + instanceId);
        }
    }

    private void removeServiceConnectors() {
        for (Service service : this.tomcat.getServer().findServices()) {
            Connector[] connectors = (Connector[])service.findConnectors().clone();
            this.serviceConnectors.put(service, connectors);
            for (Connector connector : connectors) {
                service.removeConnector(connector);
            }
        }
    }

    private void rethrowDeferredStartupExceptions() throws Exception {
        Container[] children;
        for (Container container : children = this.tomcat.getHost().findChildren()) {
            Exception exception;
            TomcatStarter tomcatStarter;
            if (container instanceof TomcatEmbeddedContext && (tomcatStarter = ((TomcatEmbeddedContext)container).getStarter()) != null && (exception = tomcatStarter.getStartUpException()) != null) {
                throw exception;
            }
            if (LifecycleState.STARTED.equals((Object)container.getState())) continue;
            throw new IllegalStateException(container + " failed to start");
        }
    }

    private void startDaemonAwaitThread() {
        Thread awaitThread = new Thread("container-" + containerCounter.get()){

            @Override
            public void run() {
                TomcatWebServer.this.tomcat.getServer().await();
            }
        };
        awaitThread.setContextClassLoader(this.getClass().getClassLoader());
        awaitThread.setDaemon(false);
        awaitThread.start();
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
                this.addPreviouslyRemovedConnectors();
                Connector connector = this.tomcat.getConnector();
                if (connector != null && this.autoStart) {
                    this.performDeferredLoadOnStartup();
                }
                this.checkThatConnectorsHaveStarted();
                this.started = true;
                logger.info((Object)("Tomcat started on port(s): " + this.getPortsDescription(true) + " with context path '" + this.getContextPath() + "'"));
            }
            catch (ConnectorStartFailedException ex) {
                this.stopSilently();
                throw ex;
            }
            catch (Exception ex) {
                PortInUseException.throwIfPortBindingException(ex, () -> this.tomcat.getConnector().getPort());
                throw new WebServerException("Unable to start embedded Tomcat server", ex);
            }
            finally {
                Context context = this.findContext();
                ContextBindings.unbindClassLoader((Object)context, (Object)context.getNamingToken(), (ClassLoader)this.getClass().getClassLoader());
            }
        }
    }

    private void checkThatConnectorsHaveStarted() {
        this.checkConnectorHasStarted(this.tomcat.getConnector());
        for (Connector connector : this.tomcat.getService().findConnectors()) {
            this.checkConnectorHasStarted(connector);
        }
    }

    private void checkConnectorHasStarted(Connector connector) {
        if (LifecycleState.FAILED.equals((Object)connector.getState())) {
            throw new ConnectorStartFailedException(connector.getPort());
        }
    }

    private void stopSilently() {
        try {
            this.stopTomcat();
        }
        catch (LifecycleException lifecycleException) {
            // empty catch block
        }
    }

    private void destroySilently() {
        try {
            this.tomcat.destroy();
        }
        catch (LifecycleException lifecycleException) {
            // empty catch block
        }
    }

    private void stopTomcat() throws LifecycleException {
        if (Thread.currentThread().getContextClassLoader() instanceof TomcatEmbeddedWebappClassLoader) {
            Thread.currentThread().setContextClassLoader(this.getClass().getClassLoader());
        }
        this.tomcat.stop();
    }

    private void addPreviouslyRemovedConnectors() {
        Service[] services;
        for (Service service : services = this.tomcat.getServer().findServices()) {
            Connector[] connectors = this.serviceConnectors.get(service);
            if (connectors == null) continue;
            for (Connector connector : connectors) {
                service.addConnector(connector);
                if (this.autoStart) continue;
                this.stopProtocolHandler(connector);
            }
            this.serviceConnectors.remove(service);
        }
    }

    private void stopProtocolHandler(Connector connector) {
        try {
            connector.getProtocolHandler().stop();
        }
        catch (Exception ex) {
            logger.error((Object)"Cannot pause connector: ", (Throwable)ex);
        }
    }

    private void performDeferredLoadOnStartup() {
        try {
            for (Container child : this.tomcat.getHost().findChildren()) {
                if (!(child instanceof TomcatEmbeddedContext)) continue;
                ((TomcatEmbeddedContext)child).deferredLoadOnStartup();
            }
        }
        catch (Exception ex) {
            if (ex instanceof WebServerException) {
                throw (WebServerException)ex;
            }
            throw new WebServerException("Unable to start embedded Tomcat connectors", ex);
        }
    }

    Map<Service, Connector[]> getServiceConnectors() {
        return this.serviceConnectors;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void stop() throws WebServerException {
        Object object = this.monitor;
        synchronized (object) {
            boolean wasStarted = this.started;
            try {
                this.started = false;
                try {
                    if (this.gracefulShutdown != null) {
                        this.gracefulShutdown.abort();
                    }
                    this.stopTomcat();
                    this.tomcat.destroy();
                }
                catch (LifecycleException lifecycleException) {
                    // empty catch block
                }
            }
            catch (Exception ex) {
                throw new WebServerException("Unable to stop embedded Tomcat", ex);
            }
            finally {
                if (wasStarted) {
                    containerCounter.decrementAndGet();
                }
            }
        }
    }

    private String getPortsDescription(boolean localPort) {
        StringBuilder ports = new StringBuilder();
        for (Connector connector : this.tomcat.getService().findConnectors()) {
            if (ports.length() != 0) {
                ports.append(' ');
            }
            int port = localPort ? connector.getLocalPort() : connector.getPort();
            ports.append(port).append(" (").append(connector.getScheme()).append(')');
        }
        return ports.toString();
    }

    @Override
    public int getPort() {
        Connector connector = this.tomcat.getConnector();
        if (connector != null) {
            return connector.getLocalPort();
        }
        return -1;
    }

    private String getContextPath() {
        return Arrays.stream(this.tomcat.getHost().findChildren()).filter(TomcatEmbeddedContext.class::isInstance).map(TomcatEmbeddedContext.class::cast).map(StandardContext::getPath).collect(Collectors.joining(" "));
    }

    public Tomcat getTomcat() {
        return this.tomcat;
    }

    @Override
    public void shutDownGracefully(GracefulShutdownCallback callback) {
        if (this.gracefulShutdown == null) {
            callback.shutdownComplete(GracefulShutdownResult.IMMEDIATE);
            return;
        }
        this.gracefulShutdown.shutDownGracefully(callback);
    }
}

