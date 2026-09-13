/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Publisher
 *  org.reactivestreams.Subscriber
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.MessageDeliveryException
 *  org.springframework.util.Assert
 *  reactor.core.Disposable$Composite
 *  reactor.core.Disposables
 *  reactor.core.publisher.Flux
 *  reactor.core.publisher.Mono
 *  reactor.core.publisher.Sinks
 *  reactor.core.publisher.Sinks$EmitFailureHandler
 *  reactor.core.publisher.Sinks$Many
 *  reactor.core.scheduler.Scheduler
 *  reactor.core.scheduler.Schedulers
 */
package org.springframework.integration.channel;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.springframework.integration.channel.AbstractMessageChannel;
import org.springframework.integration.channel.ReactiveStreamsSubscribableChannel;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageDeliveryException;
import org.springframework.util.Assert;
import reactor.core.Disposable;
import reactor.core.Disposables;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

public class FluxMessageChannel
extends AbstractMessageChannel
implements Publisher<Message<?>>,
ReactiveStreamsSubscribableChannel {
    private final Scheduler scheduler = Schedulers.boundedElastic();
    private final Sinks.Many<Message<?>> sink = Sinks.many().multicast().onBackpressureBuffer(1, false);
    private final Sinks.Many<Boolean> subscribedSignal = Sinks.many().replay().limit(1);
    private final Disposable.Composite upstreamSubscriptions = Disposables.composite();
    private volatile boolean active = true;

    @Override
    protected boolean doSend(Message<?> message, long timeout) {
        Assert.state((this.active && this.sink.currentSubscriberCount() > 0 ? 1 : 0) != 0, () -> "The [" + this + "] doesn't have subscribers to accept messages");
        long remainingTime = 0L;
        if (timeout > 0L) {
            remainingTime = timeout;
        }
        long parkTimeout = 10L;
        long parkTimeoutNs = TimeUnit.MILLISECONDS.toNanos(parkTimeout);
        while (this.active && !this.tryEmitMessage(message)) {
            if (timeout >= 0L && (remainingTime -= parkTimeout) <= 0L) {
                return false;
            }
            LockSupport.parkNanos(parkTimeoutNs);
        }
        return true;
    }

    private boolean tryEmitMessage(Message<?> message) {
        switch (this.sink.tryEmitNext(message)) {
            case OK: {
                return true;
            }
            case FAIL_NON_SERIALIZED: 
            case FAIL_OVERFLOW: {
                return false;
            }
            case FAIL_ZERO_SUBSCRIBER: {
                throw new IllegalStateException("The [" + this + "] doesn't have subscribers to accept messages");
            }
            case FAIL_TERMINATED: 
            case FAIL_CANCELLED: {
                throw new IllegalStateException("Cannot emit messages into the cancelled or terminated sink: " + this.sink);
            }
        }
        throw new UnsupportedOperationException();
    }

    public void subscribe(Subscriber<? super Message<?>> subscriber) {
        this.sink.asFlux().doFinally(s -> this.subscribedSignal.tryEmitNext((Object)(this.sink.currentSubscriberCount() > 0 ? 1 : 0))).share().subscribe(subscriber);
        this.upstreamSubscriptions.add(Mono.fromCallable(() -> this.sink.currentSubscriberCount() > 0).filter(Boolean::booleanValue).doOnNext(arg_0 -> this.subscribedSignal.tryEmitNext(arg_0)).repeatWhenEmpty(repeat -> this.active ? repeat.delayElements(Duration.ofMillis(100L)) : repeat).subscribe());
    }

    @Override
    public void subscribeTo(Publisher<? extends Message<?>> publisher) {
        this.upstreamSubscriptions.add(Flux.from(publisher).delaySubscription((Publisher)this.subscribedSignal.asFlux().filter(Boolean::booleanValue).next()).publishOn(this.scheduler).doOnNext(message -> {
            try {
                if (!this.send((Message<?>)message)) {
                    throw new MessageDeliveryException(message, "Failed to send message to channel '" + this);
                }
            }
            catch (Exception ex) {
                this.logger.warn((Throwable)ex, () -> "Error during processing event: " + message);
            }
        }).subscribe());
    }

    @Override
    public void destroy() {
        this.active = false;
        this.upstreamSubscriptions.dispose();
        this.subscribedSignal.emitComplete(Sinks.EmitFailureHandler.FAIL_FAST);
        this.sink.emitComplete(Sinks.EmitFailureHandler.FAIL_FAST);
        this.scheduler.dispose();
        super.destroy();
    }
}

