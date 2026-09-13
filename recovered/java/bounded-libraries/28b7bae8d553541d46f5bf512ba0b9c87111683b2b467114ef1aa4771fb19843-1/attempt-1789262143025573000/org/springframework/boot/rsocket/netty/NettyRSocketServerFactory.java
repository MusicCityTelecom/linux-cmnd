/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.rsocket.SocketAcceptor
 *  io.rsocket.core.RSocketServer
 *  io.rsocket.transport.ServerTransport
 *  io.rsocket.transport.netty.server.CloseableChannel
 *  io.rsocket.transport.netty.server.TcpServerTransport
 *  io.rsocket.transport.netty.server.WebsocketServerTransport
 *  org.springframework.http.client.reactive.ReactorResourceFactory
 *  org.springframework.util.Assert
 *  org.springframework.util.unit.DataSize
 *  reactor.core.publisher.Mono
 *  reactor.netty.http.server.HttpServer
 *  reactor.netty.tcp.AbstractProtocolSslContextSpec
 *  reactor.netty.tcp.SslProvider$ProtocolSslContextSpec
 *  reactor.netty.tcp.TcpServer
 */
package org.springframework.boot.rsocket.netty;

import io.rsocket.SocketAcceptor;
import io.rsocket.core.RSocketServer;
import io.rsocket.transport.ServerTransport;
import io.rsocket.transport.netty.server.CloseableChannel;
import io.rsocket.transport.netty.server.TcpServerTransport;
import io.rsocket.transport.netty.server.WebsocketServerTransport;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.boot.rsocket.netty.NettyRSocketServer;
import org.springframework.boot.rsocket.server.ConfigurableRSocketServerFactory;
import org.springframework.boot.rsocket.server.RSocketServer;
import org.springframework.boot.rsocket.server.RSocketServerCustomizer;
import org.springframework.boot.rsocket.server.RSocketServerFactory;
import org.springframework.boot.web.embedded.netty.SslServerCustomizer;
import org.springframework.boot.web.server.CertificateFileSslStoreProvider;
import org.springframework.boot.web.server.Ssl;
import org.springframework.boot.web.server.SslStoreProvider;
import org.springframework.http.client.reactive.ReactorResourceFactory;
import org.springframework.util.Assert;
import org.springframework.util.unit.DataSize;
import reactor.core.publisher.Mono;
import reactor.netty.http.server.HttpServer;
import reactor.netty.tcp.AbstractProtocolSslContextSpec;
import reactor.netty.tcp.SslProvider;
import reactor.netty.tcp.TcpServer;

public class NettyRSocketServerFactory
implements RSocketServerFactory,
ConfigurableRSocketServerFactory {
    private int port = 9898;
    private DataSize fragmentSize;
    private InetAddress address;
    private RSocketServer.Transport transport = RSocketServer.Transport.TCP;
    private ReactorResourceFactory resourceFactory;
    private Duration lifecycleTimeout;
    private List<RSocketServerCustomizer> rSocketServerCustomizers = new ArrayList<RSocketServerCustomizer>();
    private Ssl ssl;
    private SslStoreProvider sslStoreProvider;

    @Override
    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public void setFragmentSize(DataSize fragmentSize) {
        this.fragmentSize = fragmentSize;
    }

    @Override
    public void setAddress(InetAddress address) {
        this.address = address;
    }

    @Override
    public void setTransport(RSocketServer.Transport transport) {
        this.transport = transport;
    }

    @Override
    public void setSsl(Ssl ssl) {
        this.ssl = ssl;
    }

    @Override
    public void setSslStoreProvider(SslStoreProvider sslStoreProvider) {
        this.sslStoreProvider = sslStoreProvider;
    }

    public void setResourceFactory(ReactorResourceFactory resourceFactory) {
        this.resourceFactory = resourceFactory;
    }

    public void setRSocketServerCustomizers(Collection<? extends RSocketServerCustomizer> rSocketServerCustomizers) {
        Assert.notNull(rSocketServerCustomizers, (String)"RSocketServerCustomizers must not be null");
        this.rSocketServerCustomizers = new ArrayList<RSocketServerCustomizer>(rSocketServerCustomizers);
    }

    public void addRSocketServerCustomizers(RSocketServerCustomizer ... rSocketServerCustomizers) {
        Assert.notNull((Object)rSocketServerCustomizers, (String)"RSocketServerCustomizers must not be null");
        this.rSocketServerCustomizers.addAll(Arrays.asList(rSocketServerCustomizers));
    }

    public void setLifecycleTimeout(Duration lifecycleTimeout) {
        this.lifecycleTimeout = lifecycleTimeout;
    }

    @Override
    public NettyRSocketServer create(SocketAcceptor socketAcceptor) {
        ServerTransport<CloseableChannel> transport = this.createTransport();
        RSocketServer server = RSocketServer.create((SocketAcceptor)socketAcceptor);
        this.configureServer(server);
        Mono starter = server.bind(transport);
        return new NettyRSocketServer((Mono<CloseableChannel>)starter, this.lifecycleTimeout);
    }

    private void configureServer(RSocketServer server) {
        PropertyMapper map = PropertyMapper.get().alwaysApplyingWhenNonNull();
        map.from(this.fragmentSize).asInt(DataSize::toBytes).to(arg_0 -> ((RSocketServer)server).fragment(arg_0));
        this.rSocketServerCustomizers.forEach(customizer -> customizer.customize(server));
    }

    private ServerTransport<CloseableChannel> createTransport() {
        if (this.transport == RSocketServer.Transport.WEBSOCKET) {
            return this.createWebSocketTransport();
        }
        return this.createTcpTransport();
    }

    private ServerTransport<CloseableChannel> createWebSocketTransport() {
        HttpServer httpServer = HttpServer.create();
        if (this.resourceFactory != null) {
            httpServer = (HttpServer)httpServer.runOn(this.resourceFactory.getLoopResources());
        }
        if (this.ssl != null && this.ssl.isEnabled()) {
            httpServer = this.customizeSslConfiguration(httpServer);
        }
        return WebsocketServerTransport.create((HttpServer)httpServer.bindAddress(this::getListenAddress));
    }

    private HttpServer customizeSslConfiguration(HttpServer httpServer) {
        SslServerCustomizer sslServerCustomizer = new SslServerCustomizer(this.ssl, null, this.getOrCreateSslStoreProvider());
        return sslServerCustomizer.apply(httpServer);
    }

    private ServerTransport<CloseableChannel> createTcpTransport() {
        TcpServer tcpServer = TcpServer.create();
        if (this.resourceFactory != null) {
            tcpServer = tcpServer.runOn(this.resourceFactory.getLoopResources());
        }
        if (this.ssl != null && this.ssl.isEnabled()) {
            TcpSslServerCustomizer sslServerCustomizer = new TcpSslServerCustomizer(this.ssl, this.getOrCreateSslStoreProvider());
            tcpServer = sslServerCustomizer.apply(tcpServer);
        }
        return TcpServerTransport.create((TcpServer)tcpServer.bindAddress(this::getListenAddress));
    }

    private SslStoreProvider getOrCreateSslStoreProvider() {
        if (this.sslStoreProvider != null) {
            return this.sslStoreProvider;
        }
        return CertificateFileSslStoreProvider.from(this.ssl);
    }

    private InetSocketAddress getListenAddress() {
        if (this.address != null) {
            return new InetSocketAddress(this.address.getHostAddress(), this.port);
        }
        return new InetSocketAddress(this.port);
    }

    private static final class TcpSslServerCustomizer
    extends SslServerCustomizer {
        private TcpSslServerCustomizer(Ssl ssl, SslStoreProvider sslStoreProvider) {
            super(ssl, null, sslStoreProvider);
        }

        @Override
        private TcpServer apply(TcpServer server) {
            AbstractProtocolSslContextSpec<?> sslContextSpec = this.createSslContextSpec();
            return server.secure(spec -> spec.sslContext((SslProvider.ProtocolSslContextSpec)sslContextSpec));
        }
    }
}

