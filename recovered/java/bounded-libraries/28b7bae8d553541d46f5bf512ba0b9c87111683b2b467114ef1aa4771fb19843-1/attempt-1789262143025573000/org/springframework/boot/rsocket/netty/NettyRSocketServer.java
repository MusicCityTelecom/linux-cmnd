/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.rsocket.transport.netty.server.CloseableChannel
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.util.Assert
 *  reactor.core.publisher.Mono
 */
package org.springframework.boot.rsocket.netty;

import io.rsocket.transport.netty.server.CloseableChannel;
import java.net.InetSocketAddress;
import java.time.Duration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.rsocket.server.RSocketServer;
import org.springframework.boot.rsocket.server.RSocketServerException;
import org.springframework.util.Assert;
import reactor.core.publisher.Mono;

public class NettyRSocketServer
implements RSocketServer {
    private static final Log logger = LogFactory.getLog(NettyRSocketServer.class);
    private final Mono<CloseableChannel> starter;
    private final Duration lifecycleTimeout;
    private CloseableChannel channel;

    public NettyRSocketServer(Mono<CloseableChannel> starter, Duration lifecycleTimeout) {
        Assert.notNull(starter, (String)"starter must not be null");
        this.starter = starter;
        this.lifecycleTimeout = lifecycleTimeout;
    }

    @Override
    public InetSocketAddress address() {
        if (this.channel != null) {
            return this.channel.address();
        }
        return null;
    }

    @Override
    public void start() throws RSocketServerException {
        this.channel = this.block(this.starter, this.lifecycleTimeout);
        logger.info((Object)("Netty RSocket started on port(s): " + this.address().getPort()));
        this.startDaemonAwaitThread(this.channel);
    }

    private void startDaemonAwaitThread(CloseableChannel channel) {
        Thread awaitThread = new Thread(() -> {
            Void cfr_ignored_0 = (Void)channel.onClose().block();
        }, "rsocket");
        awaitThread.setContextClassLoader(this.getClass().getClassLoader());
        awaitThread.setDaemon(false);
        awaitThread.start();
    }

    @Override
    public void stop() throws RSocketServerException {
        if (this.channel != null) {
            this.channel.dispose();
            this.channel = null;
        }
    }

    private <T> T block(Mono<T> mono, Duration timeout) {
        return (T)(timeout != null ? mono.block(timeout) : mono.block());
    }
}

