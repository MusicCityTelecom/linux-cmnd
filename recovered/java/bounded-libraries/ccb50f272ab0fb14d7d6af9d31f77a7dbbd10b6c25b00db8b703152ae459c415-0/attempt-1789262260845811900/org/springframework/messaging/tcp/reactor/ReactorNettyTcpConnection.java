/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 *  org.reactivestreams.Publisher
 *  org.springframework.util.concurrent.ListenableFuture
 *  org.springframework.util.concurrent.MonoToListenableFutureAdapter
 *  reactor.core.publisher.Mono
 *  reactor.core.publisher.Sinks$Empty
 *  reactor.netty.NettyInbound
 *  reactor.netty.NettyOutbound
 */
package org.springframework.messaging.tcp.reactor;

import io.netty.buffer.ByteBuf;
import org.reactivestreams.Publisher;
import org.springframework.messaging.Message;
import org.springframework.messaging.tcp.TcpConnection;
import org.springframework.messaging.tcp.reactor.ReactorNettyCodec;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.MonoToListenableFutureAdapter;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;
import reactor.netty.NettyInbound;
import reactor.netty.NettyOutbound;

public class ReactorNettyTcpConnection<P>
implements TcpConnection<P> {
    private final NettyInbound inbound;
    private final NettyOutbound outbound;
    private final ReactorNettyCodec<P> codec;
    private final Sinks.Empty<Void> completionSink;

    public ReactorNettyTcpConnection(NettyInbound inbound, NettyOutbound outbound, ReactorNettyCodec<P> codec, Sinks.Empty<Void> completionSink) {
        this.inbound = inbound;
        this.outbound = outbound;
        this.codec = codec;
        this.completionSink = completionSink;
    }

    @Override
    public ListenableFuture<Void> send(Message<P> message) {
        ByteBuf byteBuf = this.outbound.alloc().buffer();
        this.codec.encode(message, byteBuf);
        Mono sendCompletion = this.outbound.send((Publisher)Mono.just((Object)byteBuf)).then();
        return new MonoToListenableFutureAdapter(sendCompletion);
    }

    @Override
    public void onReadInactivity(Runnable runnable, long inactivityDuration) {
        this.inbound.withConnection(conn -> conn.onReadIdle(inactivityDuration, runnable));
    }

    @Override
    public void onWriteInactivity(Runnable runnable, long inactivityDuration) {
        this.inbound.withConnection(conn -> conn.onWriteIdle(inactivityDuration, runnable));
    }

    @Override
    public void close() {
        this.completionSink.tryEmitEmpty();
    }
}

