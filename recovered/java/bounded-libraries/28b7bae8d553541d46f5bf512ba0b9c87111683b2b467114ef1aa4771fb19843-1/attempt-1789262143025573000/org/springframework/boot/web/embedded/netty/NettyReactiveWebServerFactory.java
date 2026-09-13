/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.http.client.reactive.ReactorResourceFactory
 *  org.springframework.http.server.reactive.HttpHandler
 *  org.springframework.http.server.reactive.ReactorHttpHandlerAdapter
 *  org.springframework.util.Assert
 *  reactor.netty.http.HttpProtocol
 *  reactor.netty.http.server.HttpServer
 *  reactor.netty.resources.LoopResources
 */
package org.springframework.boot.web.embedded.netty;

import java.net.InetSocketAddress;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.springframework.boot.web.embedded.netty.CompressionCustomizer;
import org.springframework.boot.web.embedded.netty.NettyRouteProvider;
import org.springframework.boot.web.embedded.netty.NettyServerCustomizer;
import org.springframework.boot.web.embedded.netty.NettyWebServer;
import org.springframework.boot.web.embedded.netty.SslServerCustomizer;
import org.springframework.boot.web.reactive.server.AbstractReactiveWebServerFactory;
import org.springframework.boot.web.server.Shutdown;
import org.springframework.boot.web.server.WebServer;
import org.springframework.http.client.reactive.ReactorResourceFactory;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.util.Assert;
import reactor.netty.http.HttpProtocol;
import reactor.netty.http.server.HttpServer;
import reactor.netty.resources.LoopResources;

public class NettyReactiveWebServerFactory
extends AbstractReactiveWebServerFactory {
    private Set<NettyServerCustomizer> serverCustomizers = new LinkedHashSet<NettyServerCustomizer>();
    private List<NettyRouteProvider> routeProviders = new ArrayList<NettyRouteProvider>();
    private Duration lifecycleTimeout;
    private boolean useForwardHeaders;
    private ReactorResourceFactory resourceFactory;
    private Shutdown shutdown;

    public NettyReactiveWebServerFactory() {
    }

    public NettyReactiveWebServerFactory(int port) {
        super(port);
    }

    @Override
    public WebServer getWebServer(HttpHandler httpHandler) {
        HttpServer httpServer = this.createHttpServer();
        ReactorHttpHandlerAdapter handlerAdapter = new ReactorHttpHandlerAdapter(httpHandler);
        NettyWebServer webServer = this.createNettyWebServer(httpServer, handlerAdapter, this.lifecycleTimeout, this.getShutdown());
        webServer.setRouteProviders(this.routeProviders);
        return webServer;
    }

    NettyWebServer createNettyWebServer(HttpServer httpServer, ReactorHttpHandlerAdapter handlerAdapter, Duration lifecycleTimeout, Shutdown shutdown) {
        return new NettyWebServer(httpServer, handlerAdapter, lifecycleTimeout, shutdown);
    }

    public Collection<NettyServerCustomizer> getServerCustomizers() {
        return this.serverCustomizers;
    }

    public void setServerCustomizers(Collection<? extends NettyServerCustomizer> serverCustomizers) {
        Assert.notNull(serverCustomizers, (String)"ServerCustomizers must not be null");
        this.serverCustomizers = new LinkedHashSet<NettyServerCustomizer>(serverCustomizers);
    }

    public void addServerCustomizers(NettyServerCustomizer ... serverCustomizers) {
        Assert.notNull((Object)serverCustomizers, (String)"ServerCustomizer must not be null");
        this.serverCustomizers.addAll(Arrays.asList(serverCustomizers));
    }

    public void addRouteProviders(NettyRouteProvider ... routeProviders) {
        Assert.notNull((Object)routeProviders, (String)"NettyRouteProvider must not be null");
        this.routeProviders.addAll(Arrays.asList(routeProviders));
    }

    public void setLifecycleTimeout(Duration lifecycleTimeout) {
        this.lifecycleTimeout = lifecycleTimeout;
    }

    public void setUseForwardHeaders(boolean useForwardHeaders) {
        this.useForwardHeaders = useForwardHeaders;
    }

    public void setResourceFactory(ReactorResourceFactory resourceFactory) {
        this.resourceFactory = resourceFactory;
    }

    @Override
    public void setShutdown(Shutdown shutdown) {
        this.shutdown = shutdown;
    }

    @Override
    public Shutdown getShutdown() {
        return this.shutdown;
    }

    private HttpServer createHttpServer() {
        HttpServer server = HttpServer.create();
        if (this.resourceFactory != null) {
            LoopResources resources = this.resourceFactory.getLoopResources();
            Assert.notNull((Object)resources, (String)"No LoopResources: is ReactorResourceFactory not initialized yet?");
            server = ((HttpServer)server.runOn(resources)).bindAddress(this::getListenAddress);
        } else {
            server = server.bindAddress(this::getListenAddress);
        }
        if (this.getSsl() != null && this.getSsl().isEnabled()) {
            server = this.customizeSslConfiguration(server);
        }
        if (this.getCompression() != null && this.getCompression().getEnabled()) {
            CompressionCustomizer compressionCustomizer = new CompressionCustomizer(this.getCompression());
            server = compressionCustomizer.apply(server);
        }
        server = server.protocol(this.listProtocols()).forwarded(this.useForwardHeaders);
        return this.applyCustomizers(server);
    }

    private HttpServer customizeSslConfiguration(HttpServer httpServer) {
        SslServerCustomizer sslServerCustomizer = new SslServerCustomizer(this.getSsl(), this.getHttp2(), this.getOrCreateSslStoreProvider());
        return sslServerCustomizer.apply(httpServer);
    }

    private HttpProtocol[] listProtocols() {
        ArrayList<HttpProtocol> protocols = new ArrayList<HttpProtocol>();
        protocols.add(HttpProtocol.HTTP11);
        if (this.getHttp2() != null && this.getHttp2().isEnabled()) {
            if (this.getSsl() != null && this.getSsl().isEnabled()) {
                protocols.add(HttpProtocol.H2);
            } else {
                protocols.add(HttpProtocol.H2C);
            }
        }
        return protocols.toArray(new HttpProtocol[0]);
    }

    private InetSocketAddress getListenAddress() {
        if (this.getAddress() != null) {
            return new InetSocketAddress(this.getAddress().getHostAddress(), this.getPort());
        }
        return new InetSocketAddress(this.getPort());
    }

    private HttpServer applyCustomizers(HttpServer server) {
        for (NettyServerCustomizer customizer : this.serverCustomizers) {
            server = (HttpServer)customizer.apply(server);
        }
        return server;
    }
}

