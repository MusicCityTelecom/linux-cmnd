/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.rsocket.SocketAcceptor
 *  io.rsocket.core.RSocketServer
 *  io.rsocket.transport.ServerTransport$ConnectionAcceptor
 *  io.rsocket.transport.netty.server.WebsocketRouteTransport
 *  org.springframework.boot.rsocket.server.RSocketServerCustomizer
 *  org.springframework.boot.web.embedded.netty.NettyRouteProvider
 *  reactor.netty.http.server.HttpServerRoutes
 */
package org.springframework.boot.autoconfigure.rsocket;

import io.rsocket.SocketAcceptor;
import io.rsocket.core.RSocketServer;
import io.rsocket.transport.ServerTransport;
import io.rsocket.transport.netty.server.WebsocketRouteTransport;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.boot.rsocket.server.RSocketServerCustomizer;
import org.springframework.boot.web.embedded.netty.NettyRouteProvider;
import reactor.netty.http.server.HttpServerRoutes;

class RSocketWebSocketNettyRouteProvider
implements NettyRouteProvider {
    private final String mappingPath;
    private final SocketAcceptor socketAcceptor;
    private final List<RSocketServerCustomizer> customizers;

    RSocketWebSocketNettyRouteProvider(String mappingPath, SocketAcceptor socketAcceptor, Stream<RSocketServerCustomizer> customizers) {
        this.mappingPath = mappingPath;
        this.socketAcceptor = socketAcceptor;
        this.customizers = customizers.collect(Collectors.toList());
    }

    public HttpServerRoutes apply(HttpServerRoutes httpServerRoutes) {
        RSocketServer server = RSocketServer.create((SocketAcceptor)this.socketAcceptor);
        this.customizers.forEach(customizer -> customizer.customize(server));
        ServerTransport.ConnectionAcceptor connectionAcceptor = server.asConnectionAcceptor();
        return httpServerRoutes.ws(this.mappingPath, WebsocketRouteTransport.newHandler((ServerTransport.ConnectionAcceptor)connectionAcceptor));
    }
}

