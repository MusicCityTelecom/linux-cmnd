/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.Servlet
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.eclipse.jetty.http2.server.HTTP2CServerConnectionFactory
 *  org.eclipse.jetty.server.AbstractConnector
 *  org.eclipse.jetty.server.ConnectionFactory
 *  org.eclipse.jetty.server.Connector
 *  org.eclipse.jetty.server.Handler
 *  org.eclipse.jetty.server.HandlerContainer
 *  org.eclipse.jetty.server.HttpConfiguration
 *  org.eclipse.jetty.server.HttpConnectionFactory
 *  org.eclipse.jetty.server.Server
 *  org.eclipse.jetty.server.ServerConnector
 *  org.eclipse.jetty.server.handler.HandlerWrapper
 *  org.eclipse.jetty.server.handler.StatisticsHandler
 *  org.eclipse.jetty.servlet.ServletContextHandler
 *  org.eclipse.jetty.servlet.ServletHolder
 *  org.eclipse.jetty.util.thread.ThreadPool
 *  org.springframework.http.client.reactive.JettyResourceFactory
 *  org.springframework.http.server.reactive.HttpHandler
 *  org.springframework.http.server.reactive.JettyHttpHandlerAdapter
 *  org.springframework.util.Assert
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.web.embedded.jetty;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.servlet.Servlet;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.jetty.http2.server.HTTP2CServerConnectionFactory;
import org.eclipse.jetty.server.AbstractConnector;
import org.eclipse.jetty.server.ConnectionFactory;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.HandlerContainer;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.HandlerWrapper;
import org.eclipse.jetty.server.handler.StatisticsHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.thread.ThreadPool;
import org.springframework.boot.web.embedded.jetty.ConfigurableJettyWebServerFactory;
import org.springframework.boot.web.embedded.jetty.ForwardHeadersCustomizer;
import org.springframework.boot.web.embedded.jetty.JettyHandlerWrappers;
import org.springframework.boot.web.embedded.jetty.JettyServerCustomizer;
import org.springframework.boot.web.embedded.jetty.JettyWebServer;
import org.springframework.boot.web.embedded.jetty.SslServerCustomizer;
import org.springframework.boot.web.reactive.server.AbstractReactiveWebServerFactory;
import org.springframework.boot.web.server.Shutdown;
import org.springframework.boot.web.server.WebServer;
import org.springframework.http.client.reactive.JettyResourceFactory;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.JettyHttpHandlerAdapter;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

public class JettyReactiveWebServerFactory
extends AbstractReactiveWebServerFactory
implements ConfigurableJettyWebServerFactory {
    private static final Log logger = LogFactory.getLog(JettyReactiveWebServerFactory.class);
    private int acceptors = -1;
    private int selectors = -1;
    private boolean useForwardHeaders;
    private Set<JettyServerCustomizer> jettyServerCustomizers = new LinkedHashSet<JettyServerCustomizer>();
    private JettyResourceFactory resourceFactory;
    private ThreadPool threadPool;

    public JettyReactiveWebServerFactory() {
    }

    public JettyReactiveWebServerFactory(int port) {
        super(port);
    }

    @Override
    public void setUseForwardHeaders(boolean useForwardHeaders) {
        this.useForwardHeaders = useForwardHeaders;
    }

    @Override
    public void setAcceptors(int acceptors) {
        this.acceptors = acceptors;
    }

    @Override
    public WebServer getWebServer(HttpHandler httpHandler) {
        JettyHttpHandlerAdapter servlet = new JettyHttpHandlerAdapter(httpHandler);
        Server server = this.createJettyServer(servlet);
        return new JettyWebServer(server, this.getPort() >= 0);
    }

    @Override
    public void addServerCustomizers(JettyServerCustomizer ... customizers) {
        Assert.notNull((Object)customizers, (String)"Customizers must not be null");
        this.jettyServerCustomizers.addAll(Arrays.asList(customizers));
    }

    public void setServerCustomizers(Collection<? extends JettyServerCustomizer> customizers) {
        Assert.notNull(customizers, (String)"Customizers must not be null");
        this.jettyServerCustomizers = new LinkedHashSet<JettyServerCustomizer>(customizers);
    }

    public Collection<JettyServerCustomizer> getServerCustomizers() {
        return this.jettyServerCustomizers;
    }

    public ThreadPool getThreadPool() {
        return this.threadPool;
    }

    @Override
    public void setThreadPool(ThreadPool threadPool) {
        this.threadPool = threadPool;
    }

    @Override
    public void setSelectors(int selectors) {
        this.selectors = selectors;
    }

    public void setResourceFactory(JettyResourceFactory resourceFactory) {
        this.resourceFactory = resourceFactory;
    }

    protected JettyResourceFactory getResourceFactory() {
        return this.resourceFactory;
    }

    protected Server createJettyServer(JettyHttpHandlerAdapter servlet) {
        int port = Math.max(this.getPort(), 0);
        InetSocketAddress address = new InetSocketAddress(this.getAddress(), port);
        Server server = new Server(this.getThreadPool());
        server.addConnector((Connector)this.createConnector(address, server));
        server.setStopTimeout(0L);
        ServletHolder servletHolder = new ServletHolder((Servlet)servlet);
        servletHolder.setAsyncSupported(true);
        ServletContextHandler contextHandler = new ServletContextHandler((HandlerContainer)server, "/", false, false);
        contextHandler.addServlet(servletHolder, "/");
        server.setHandler(this.addHandlerWrappers((Handler)contextHandler));
        logger.info((Object)("Server initialized with port: " + port));
        if (this.getSsl() != null && this.getSsl().isEnabled()) {
            this.customizeSsl(server, address);
        }
        for (JettyServerCustomizer customizer : this.getServerCustomizers()) {
            customizer.customize(server);
        }
        if (this.useForwardHeaders) {
            new ForwardHeadersCustomizer().customize(server);
        }
        if (this.getShutdown() == Shutdown.GRACEFUL) {
            StatisticsHandler statisticsHandler = new StatisticsHandler();
            statisticsHandler.setHandler(server.getHandler());
            server.setHandler((Handler)statisticsHandler);
        }
        return server;
    }

    private AbstractConnector createConnector(InetSocketAddress address, Server server) {
        JettyResourceFactory resourceFactory;
        HttpConfiguration httpConfiguration = new HttpConfiguration();
        httpConfiguration.setSendServerVersion(false);
        ArrayList<Object> connectionFactories = new ArrayList<Object>();
        connectionFactories.add(new HttpConnectionFactory(httpConfiguration));
        if (this.getHttp2() != null && this.getHttp2().isEnabled()) {
            connectionFactories.add(new HTTP2CServerConnectionFactory(httpConfiguration));
        }
        ServerConnector connector = (resourceFactory = this.getResourceFactory()) != null ? new ServerConnector(server, resourceFactory.getExecutor(), resourceFactory.getScheduler(), resourceFactory.getByteBufferPool(), this.acceptors, this.selectors, connectionFactories.toArray(new ConnectionFactory[0])) : new ServerConnector(server, this.acceptors, this.selectors, connectionFactories.toArray(new ConnectionFactory[0]));
        connector.setHost(address.getHostString());
        connector.setPort(address.getPort());
        return connector;
    }

    private Handler addHandlerWrappers(Handler handler) {
        if (this.getCompression() != null && this.getCompression().getEnabled()) {
            handler = this.applyWrapper(handler, JettyHandlerWrappers.createGzipHandlerWrapper(this.getCompression()));
        }
        if (StringUtils.hasText((String)this.getServerHeader())) {
            handler = this.applyWrapper(handler, JettyHandlerWrappers.createServerHeaderHandlerWrapper(this.getServerHeader()));
        }
        return handler;
    }

    private Handler applyWrapper(Handler handler, HandlerWrapper wrapper) {
        wrapper.setHandler(handler);
        return wrapper;
    }

    private void customizeSsl(Server server, InetSocketAddress address) {
        new SslServerCustomizer(address, this.getSsl(), this.getOrCreateSslStoreProvider(), this.getHttp2()).customize(server);
    }
}

