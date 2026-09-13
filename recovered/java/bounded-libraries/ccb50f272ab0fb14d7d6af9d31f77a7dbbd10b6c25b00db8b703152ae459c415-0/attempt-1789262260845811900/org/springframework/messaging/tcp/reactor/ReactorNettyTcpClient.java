/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 *  io.netty.channel.ChannelHandlerContext
 *  io.netty.channel.group.ChannelGroup
 *  io.netty.channel.group.DefaultChannelGroup
 *  io.netty.handler.codec.ByteToMessageDecoder
 *  io.netty.util.concurrent.EventExecutor
 *  io.netty.util.concurrent.Future
 *  io.netty.util.concurrent.ImmediateEventExecutor
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.reactivestreams.Publisher
 *  org.springframework.lang.Nullable
 *  org.springframework.util.Assert
 *  org.springframework.util.concurrent.CompletableToListenableFutureAdapter
 *  org.springframework.util.concurrent.ListenableFuture
 *  org.springframework.util.concurrent.MonoToListenableFutureAdapter
 *  org.springframework.util.concurrent.SettableListenableFuture
 *  reactor.core.publisher.Mono
 *  reactor.core.publisher.Sinks
 *  reactor.core.publisher.Sinks$Empty
 *  reactor.core.scheduler.Scheduler
 *  reactor.core.scheduler.Schedulers
 *  reactor.netty.DisposableChannel
 *  reactor.netty.FutureMono
 *  reactor.netty.NettyInbound
 *  reactor.netty.NettyOutbound
 *  reactor.netty.resources.ConnectionProvider
 *  reactor.netty.resources.LoopResources
 *  reactor.netty.tcp.TcpClient
 *  reactor.util.retry.Retry
 */
package org.springframework.messaging.tcp.reactor;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.ImmediateEventExecutor;
import java.time.Duration;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.reactivestreams.Publisher;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.tcp.ReconnectStrategy;
import org.springframework.messaging.tcp.TcpConnectionHandler;
import org.springframework.messaging.tcp.TcpOperations;
import org.springframework.messaging.tcp.reactor.ReactorNettyCodec;
import org.springframework.messaging.tcp.reactor.ReactorNettyTcpConnection;
import org.springframework.util.Assert;
import org.springframework.util.concurrent.CompletableToListenableFutureAdapter;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.MonoToListenableFutureAdapter;
import org.springframework.util.concurrent.SettableListenableFuture;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;
import reactor.netty.DisposableChannel;
import reactor.netty.FutureMono;
import reactor.netty.NettyInbound;
import reactor.netty.NettyOutbound;
import reactor.netty.resources.ConnectionProvider;
import reactor.netty.resources.LoopResources;
import reactor.netty.tcp.TcpClient;
import reactor.util.retry.Retry;

public class ReactorNettyTcpClient<P>
implements TcpOperations<P> {
    private static final int PUBLISH_ON_BUFFER_SIZE = 16;
    private final TcpClient tcpClient;
    private final ReactorNettyCodec<P> codec;
    @Nullable
    private final ChannelGroup channelGroup;
    @Nullable
    private final LoopResources loopResources;
    @Nullable
    private final ConnectionProvider poolResources;
    private final Scheduler scheduler = Schedulers.newParallel((String)"tcp-client-scheduler");
    private Log logger = LogFactory.getLog(ReactorNettyTcpClient.class);
    private volatile boolean stopping;

    public ReactorNettyTcpClient(String host, int port, ReactorNettyCodec<P> codec) {
        Assert.notNull((Object)host, (String)"host is required");
        Assert.notNull(codec, (String)"ReactorNettyCodec is required");
        this.channelGroup = new DefaultChannelGroup((EventExecutor)ImmediateEventExecutor.INSTANCE);
        this.loopResources = LoopResources.create((String)"tcp-client-loop");
        this.poolResources = ConnectionProvider.create((String)"tcp-client-pool", (int)10000);
        this.codec = codec;
        this.tcpClient = TcpClient.create((ConnectionProvider)this.poolResources).host(host).port(port).runOn(this.loopResources, false).doOnConnected(conn -> this.channelGroup.add((Object)conn.channel()));
    }

    public ReactorNettyTcpClient(Function<TcpClient, TcpClient> clientConfigurer, ReactorNettyCodec<P> codec) {
        Assert.notNull(codec, (String)"ReactorNettyCodec is required");
        this.channelGroup = new DefaultChannelGroup((EventExecutor)ImmediateEventExecutor.INSTANCE);
        this.loopResources = LoopResources.create((String)"tcp-client-loop");
        this.poolResources = ConnectionProvider.create((String)"tcp-client-pool", (int)10000);
        this.codec = codec;
        this.tcpClient = clientConfigurer.apply(TcpClient.create((ConnectionProvider)this.poolResources).runOn(this.loopResources, false).doOnConnected(conn -> this.channelGroup.add((Object)conn.channel())));
    }

    public ReactorNettyTcpClient(TcpClient tcpClient, ReactorNettyCodec<P> codec) {
        Assert.notNull((Object)tcpClient, (String)"TcpClient is required");
        Assert.notNull(codec, (String)"ReactorNettyCodec is required");
        this.tcpClient = tcpClient;
        this.codec = codec;
        this.channelGroup = null;
        this.loopResources = null;
        this.poolResources = null;
    }

    public void setLogger(Log logger) {
        this.logger = logger;
    }

    public Log getLogger() {
        return this.logger;
    }

    @Override
    public ListenableFuture<Void> connect(TcpConnectionHandler<P> handler) {
        Assert.notNull(handler, (String)"TcpConnectionHandler is required");
        if (this.stopping) {
            return this.handleShuttingDownConnectFailure(handler);
        }
        Mono connectMono = this.extendTcpClient(this.tcpClient, handler).handle((BiFunction)new ReactorNettyHandler(handler)).connect().doOnError(handler::afterConnectFailure).then();
        return new MonoToListenableFutureAdapter(connectMono);
    }

    protected TcpClient extendTcpClient(TcpClient tcpClient, TcpConnectionHandler<P> handler) {
        return tcpClient;
    }

    @Override
    public ListenableFuture<Void> connect(TcpConnectionHandler<P> handler, ReconnectStrategy strategy) {
        Assert.notNull(handler, (String)"TcpConnectionHandler is required");
        Assert.notNull((Object)strategy, (String)"ReconnectStrategy is required");
        if (this.stopping) {
            return this.handleShuttingDownConnectFailure(handler);
        }
        CompletableFuture connectFuture = new CompletableFuture();
        this.extendTcpClient(this.tcpClient, handler).handle((BiFunction)new ReactorNettyHandler(handler)).connect().doOnNext(conn -> connectFuture.complete(null)).doOnError(connectFuture::completeExceptionally).doOnError(handler::afterConnectFailure).flatMap(DisposableChannel::onDispose).retryWhen(Retry.from(signals -> signals.map(retrySignal -> (int)retrySignal.totalRetriesInARow()).flatMap(attempt -> this.reconnect((Integer)attempt, strategy)))).repeatWhen(flux -> flux.scan((Object)1, (count, element) -> {
            Integer n = count;
            Integer n2 = count = Integer.valueOf(count + 1);
            return n;
        }).flatMap(attempt -> this.reconnect((Integer)attempt, strategy))).subscribe();
        return new CompletableToListenableFutureAdapter(connectFuture);
    }

    private ListenableFuture<Void> handleShuttingDownConnectFailure(TcpConnectionHandler<P> handler) {
        IllegalStateException ex = new IllegalStateException("Shutting down.");
        handler.afterConnectFailure(ex);
        return new MonoToListenableFutureAdapter(Mono.error((Throwable)ex));
    }

    private Publisher<? extends Long> reconnect(Integer attempt, ReconnectStrategy reconnectStrategy) {
        Long time = reconnectStrategy.getTimeToNextAttempt(attempt);
        return time != null ? Mono.delay((Duration)Duration.ofMillis(time), (Scheduler)this.scheduler) : Mono.empty();
    }

    @Override
    public ListenableFuture<Void> shutdown() {
        Mono result;
        if (this.stopping) {
            SettableListenableFuture future = new SettableListenableFuture();
            future.set(null);
            return future;
        }
        this.stopping = true;
        if (this.channelGroup != null) {
            result = FutureMono.from((Future)this.channelGroup.close());
            if (this.loopResources != null) {
                result = result.onErrorResume(ex -> Mono.empty()).then(this.loopResources.disposeLater());
            }
            if (this.poolResources != null) {
                result = result.onErrorResume(ex -> Mono.empty()).then(this.poolResources.disposeLater());
            }
            result = result.onErrorResume(ex -> Mono.empty()).then(this.stopScheduler());
        } else {
            result = this.stopScheduler();
        }
        return new MonoToListenableFutureAdapter(result);
    }

    private Mono<Void> stopScheduler() {
        return Mono.fromRunnable(() -> {
            this.scheduler.dispose();
            for (int i2 = 0; i2 < 20 && !this.scheduler.isDisposed(); ++i2) {
                try {
                    Thread.sleep(100L);
                    continue;
                }
                catch (Throwable ex) {
                    break;
                }
            }
        });
    }

    public String toString() {
        return "ReactorNettyTcpClient[" + this.tcpClient + "]";
    }

    private static class StompMessageDecoder<P>
    extends ByteToMessageDecoder {
        private final ReactorNettyCodec<P> codec;

        StompMessageDecoder(ReactorNettyCodec<P> codec) {
            this.codec = codec;
        }

        protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
            Collection<Message<P>> messages = this.codec.decode(in);
            out.addAll(messages);
        }
    }

    private class ReactorNettyHandler
    implements BiFunction<NettyInbound, NettyOutbound, Publisher<Void>> {
        private final TcpConnectionHandler<P> connectionHandler;

        ReactorNettyHandler(TcpConnectionHandler<P> handler) {
            this.connectionHandler = handler;
        }

        @Override
        public Publisher<Void> apply(NettyInbound inbound, NettyOutbound outbound) {
            inbound.withConnection(conn -> {
                if (ReactorNettyTcpClient.this.logger.isDebugEnabled()) {
                    ReactorNettyTcpClient.this.logger.debug((Object)("Connected to " + conn.address()));
                }
            });
            Sinks.Empty completionSink = Sinks.empty();
            ReactorNettyTcpConnection connection = new ReactorNettyTcpConnection(inbound, outbound, ReactorNettyTcpClient.this.codec, (Sinks.Empty<Void>)completionSink);
            ReactorNettyTcpClient.this.scheduler.schedule(() -> this.connectionHandler.afterConnected(connection));
            inbound.withConnection(conn -> conn.addHandlerFirst(new StompMessageDecoder(ReactorNettyTcpClient.this.codec)));
            inbound.receiveObject().cast(Message.class).publishOn(ReactorNettyTcpClient.this.scheduler, 16).subscribe(this.connectionHandler::handleMessage, this.connectionHandler::handleFailure, this.connectionHandler::afterConnectionClosed);
            return completionSink.asMono();
        }
    }
}

