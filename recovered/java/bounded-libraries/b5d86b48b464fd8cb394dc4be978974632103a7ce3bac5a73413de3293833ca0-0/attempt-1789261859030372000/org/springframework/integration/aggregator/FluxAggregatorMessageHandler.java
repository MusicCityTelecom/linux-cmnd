/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Publisher
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.MessageChannel
 *  org.springframework.util.Assert
 *  reactor.core.Disposable
 *  reactor.core.publisher.Flux
 *  reactor.core.publisher.FluxSink
 *  reactor.core.publisher.FluxSink$OverflowStrategy
 *  reactor.core.publisher.Mono
 */
package org.springframework.integration.aggregator;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.function.Predicate;
import org.reactivestreams.Publisher;
import org.springframework.integration.IntegrationPatternType;
import org.springframework.integration.aggregator.CorrelationStrategy;
import org.springframework.integration.aggregator.HeaderAttributeCorrelationStrategy;
import org.springframework.integration.channel.ReactiveStreamsSubscribableChannel;
import org.springframework.integration.handler.AbstractMessageProducingHandler;
import org.springframework.integration.support.management.ManageableLifecycle;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.util.Assert;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.Mono;

public class FluxAggregatorMessageHandler
extends AbstractMessageProducingHandler
implements ManageableLifecycle {
    private final AtomicBoolean subscribed = new AtomicBoolean();
    private final Flux<Message<?>> aggregatorFlux;
    private CorrelationStrategy correlationStrategy = new HeaderAttributeCorrelationStrategy("correlationId");
    private Predicate<Message<?>> boundaryTrigger;
    private Function<Message<?>, Integer> windowSizeFunction = FluxAggregatorMessageHandler::sequenceSizeHeader;
    private Function<Flux<Message<?>>, Flux<Flux<Message<?>>>> windowConfigurer;
    private Duration windowTimespan;
    private Function<Flux<Message<?>>, Mono<Message<?>>> combineFunction = this::messageForWindowFlux;
    private FluxSink<Message<?>> sink;
    private volatile Disposable subscription;

    public FluxAggregatorMessageHandler() {
        this.aggregatorFlux = Flux.create(emitter -> {
            this.sink = emitter;
        }, (FluxSink.OverflowStrategy)FluxSink.OverflowStrategy.BUFFER).groupBy(this::groupBy).flatMap(group -> group.transform(this::releaseBy)).publish().autoConnect();
    }

    private Object groupBy(Message<?> message) {
        return this.correlationStrategy.getCorrelationKey(message);
    }

    private Flux<Message<?>> releaseBy(Flux<Message<?>> groupFlux) {
        return groupFlux.transform(this.windowConfigurer != null ? this.windowConfigurer : this::applyWindowOptions).flatMap(windowFlux -> windowFlux.transform(this.combineFunction));
    }

    private Flux<Flux<Message<?>>> applyWindowOptions(Flux<Message<?>> groupFlux) {
        if (this.boundaryTrigger != null) {
            return groupFlux.windowUntil(this.boundaryTrigger);
        }
        return groupFlux.switchOnFirst((signal, group) -> {
            if (signal.hasValue()) {
                Integer maxSize = this.windowSizeFunction.apply((Message)signal.get());
                if (maxSize != null) {
                    if (this.windowTimespan != null) {
                        return group.windowTimeout(maxSize.intValue(), this.windowTimespan);
                    }
                    return group.window(maxSize.intValue());
                }
                if (this.windowTimespan != null) {
                    return group.window(this.windowTimespan);
                }
                return Flux.error((Throwable)new IllegalStateException("One of the 'boundaryTrigger', 'windowSizeFunction' or 'windowTimespan' options must be configured or 'sequenceSize' header must be supplied in the messages to aggregate."));
            }
            return Flux.just((Object)group);
        });
    }

    public void setCorrelationStrategy(CorrelationStrategy correlationStrategy) {
        Assert.notNull((Object)correlationStrategy, (String)"'correlationStrategy' must not be null");
        this.correlationStrategy = correlationStrategy;
    }

    public void setCombineFunction(Function<Flux<Message<?>>, Mono<Message<?>>> combineFunction) {
        Assert.notNull(combineFunction, (String)"'combineFunction' must not be null");
        this.combineFunction = combineFunction;
    }

    public void setBoundaryTrigger(Predicate<Message<?>> boundaryTrigger) {
        this.boundaryTrigger = boundaryTrigger;
    }

    public void setWindowSize(int windowSize) {
        this.setWindowSizeFunction(message -> windowSize);
    }

    public void setWindowSizeFunction(Function<Message<?>, Integer> windowSizeFunction) {
        Assert.notNull(windowSizeFunction, (String)"'windowSizeFunction' must not be null");
        this.windowSizeFunction = windowSizeFunction;
    }

    public void setWindowTimespan(Duration windowTimespan) {
        this.windowTimespan = windowTimespan;
    }

    public void setWindowConfigurer(Function<Flux<Message<?>>, Flux<Flux<Message<?>>>> windowConfigurer) {
        this.windowConfigurer = windowConfigurer;
    }

    @Override
    public String getComponentType() {
        return "flux-aggregator";
    }

    @Override
    public IntegrationPatternType getIntegrationPatternType() {
        return IntegrationPatternType.aggregator;
    }

    @Override
    public void start() {
        if (this.subscribed.compareAndSet(false, true)) {
            MessageChannel outputChannel = this.getOutputChannel();
            if (outputChannel instanceof ReactiveStreamsSubscribableChannel) {
                ((ReactiveStreamsSubscribableChannel)outputChannel).subscribeTo((Publisher<? extends Message<?>>)this.aggregatorFlux);
            } else {
                this.subscription = this.aggregatorFlux.subscribe(messageToSend -> this.produceOutput(messageToSend, (Message<?>)messageToSend));
            }
        }
    }

    @Override
    public void stop() {
        if (this.subscribed.compareAndSet(true, false) && this.subscription != null) {
            this.subscription.dispose();
        }
    }

    @Override
    public boolean isRunning() {
        return this.subscribed.get();
    }

    @Override
    protected void handleMessageInternal(Message<?> message) {
        Assert.state((boolean)this.isRunning(), (String)"The 'FluxAggregatorMessageHandler' has not been started to accept incoming messages");
        this.sink.next(message);
    }

    @Override
    protected boolean shouldCopyRequestHeaders() {
        return false;
    }

    private Mono<Message<?>> messageForWindowFlux(Flux<Message<?>> messageFlux) {
        Flux window = messageFlux.publish().autoConnect();
        return window.next().map(first -> this.getMessageBuilderFactory().withPayload(Flux.concat((Publisher[])new Publisher[]{Mono.just((Object)first), window})).copyHeaders((Map<String, ?>)first.getHeaders()).build());
    }

    private static Integer sequenceSizeHeader(Message<?> message) {
        return (Integer)message.getHeaders().get((Object)"sequenceSize", Integer.class);
    }
}

