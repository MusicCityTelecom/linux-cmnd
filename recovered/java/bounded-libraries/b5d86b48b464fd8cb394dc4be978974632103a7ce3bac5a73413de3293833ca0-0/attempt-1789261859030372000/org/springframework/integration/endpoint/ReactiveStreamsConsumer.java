/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Publisher
 *  org.reactivestreams.Subscriber
 *  org.reactivestreams.Subscription
 *  org.springframework.context.Lifecycle
 *  org.springframework.lang.Nullable
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.MessageChannel
 *  org.springframework.messaging.MessageHandler
 *  org.springframework.messaging.ReactiveMessageHandler
 *  org.springframework.util.Assert
 *  org.springframework.util.ErrorHandler
 *  reactor.core.CoreSubscriber
 *  reactor.core.Disposable
 *  reactor.core.publisher.BaseSubscriber
 *  reactor.core.publisher.Flux
 */
package org.springframework.integration.endpoint;

import java.util.function.Consumer;
import java.util.function.Function;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.springframework.context.Lifecycle;
import org.springframework.integration.channel.ChannelUtils;
import org.springframework.integration.channel.NullChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.endpoint.AbstractEndpoint;
import org.springframework.integration.endpoint.IntegrationConsumer;
import org.springframework.integration.handler.ReactiveMessageHandlerAdapter;
import org.springframework.integration.router.MessageRouter;
import org.springframework.integration.util.IntegrationReactiveUtils;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.ReactiveMessageHandler;
import org.springframework.util.Assert;
import org.springframework.util.ErrorHandler;
import reactor.core.CoreSubscriber;
import reactor.core.Disposable;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;

public class ReactiveStreamsConsumer
extends AbstractEndpoint
implements IntegrationConsumer {
    private final MessageChannel inputChannel;
    private final Publisher<Message<Object>> publisher;
    private final MessageHandler handler;
    @Nullable
    private final ReactiveMessageHandler reactiveMessageHandler;
    @Nullable
    private final Subscriber<Message<?>> subscriber;
    @Nullable
    private final Lifecycle lifecycleDelegate;
    @Nullable
    private Function<? super Flux<Message<?>>, ? extends Publisher<Message<?>>> reactiveCustomizer;
    private ErrorHandler errorHandler;
    private volatile Disposable subscription;

    public ReactiveStreamsConsumer(MessageChannel inputChannel, MessageHandler messageHandler) {
        this(inputChannel, (Subscriber<Message<?>>)(messageHandler instanceof Subscriber ? (Subscriber)messageHandler : new MessageHandlerSubscriber(messageHandler)));
    }

    public ReactiveStreamsConsumer(MessageChannel inputChannel, Subscriber<Message<?>> subscriber) {
        Assert.notNull((Object)inputChannel, (String)"'inputChannel' must not be null");
        Assert.notNull(subscriber, (String)"'subscriber' must not be null");
        this.inputChannel = inputChannel;
        if (inputChannel instanceof NullChannel) {
            this.logger.warn((CharSequence)"The consuming from the NullChannel does not have any effects: it doesn't forward messages sent to it. A NullChannel is the end of the flow.");
        }
        this.publisher = IntegrationReactiveUtils.messageChannelToFlux(inputChannel);
        this.subscriber = subscriber;
        Lifecycle lifecycle = this.lifecycleDelegate = subscriber instanceof Lifecycle ? (Lifecycle)subscriber : null;
        this.handler = subscriber instanceof MessageHandlerSubscriber ? ((MessageHandlerSubscriber)subscriber).messageHandler : (subscriber instanceof MessageHandler ? (MessageHandler)subscriber : arg_0 -> this.subscriber.onNext(arg_0));
        this.reactiveMessageHandler = null;
    }

    public ReactiveStreamsConsumer(MessageChannel inputChannel, ReactiveMessageHandler reactiveMessageHandler) {
        Assert.notNull((Object)inputChannel, (String)"'inputChannel' must not be null");
        this.inputChannel = inputChannel;
        this.handler = new ReactiveMessageHandlerAdapter(reactiveMessageHandler);
        this.reactiveMessageHandler = reactiveMessageHandler;
        this.publisher = IntegrationReactiveUtils.messageChannelToFlux(inputChannel);
        this.subscriber = null;
        this.lifecycleDelegate = reactiveMessageHandler instanceof Lifecycle ? (Lifecycle)reactiveMessageHandler : null;
    }

    public void setErrorHandler(ErrorHandler errorHandler) {
        this.errorHandler = errorHandler;
    }

    public void setReactiveCustomizer(@Nullable Function<? super Flux<Message<?>>, ? extends Publisher<Message<?>>> reactiveCustomizer) {
        this.reactiveCustomizer = reactiveCustomizer;
    }

    @Override
    public MessageChannel getInputChannel() {
        return this.inputChannel;
    }

    @Override
    public MessageChannel getOutputChannel() {
        if (this.handler instanceof MessageProducer) {
            return ((MessageProducer)this.handler).getOutputChannel();
        }
        if (this.handler instanceof MessageRouter) {
            return ((MessageRouter)this.handler).getDefaultOutputChannel();
        }
        return null;
    }

    @Override
    public MessageHandler getHandler() {
        return this.handler;
    }

    @Override
    protected void onInit() {
        super.onInit();
        if (this.errorHandler == null) {
            this.errorHandler = ChannelUtils.getErrorHandler(this.getBeanFactory());
        }
    }

    @Override
    protected void doStart() {
        if (this.lifecycleDelegate != null) {
            this.lifecycleDelegate.start();
        }
        Flux fluxFromChannel = Flux.from(this.publisher);
        if (this.reactiveCustomizer != null) {
            fluxFromChannel = fluxFromChannel.transform(this.reactiveCustomizer);
        }
        if (this.reactiveMessageHandler != null) {
            this.subscription = fluxFromChannel.flatMap(arg_0 -> ((ReactiveMessageHandler)this.reactiveMessageHandler).handleMessage(arg_0)).onErrorContinue((ex, data) -> this.errorHandler.handleError(ex)).subscribe();
        } else if (this.subscriber != null) {
            this.subscription = (Disposable)fluxFromChannel.subscribeWith((Subscriber)new SubscriberDecorator(this.subscriber, this.errorHandler));
        }
    }

    @Override
    protected void doStop() {
        if (this.subscription != null) {
            this.subscription.dispose();
        }
        if (this.lifecycleDelegate != null) {
            this.lifecycleDelegate.stop();
        }
    }

    private static final class MessageHandlerSubscriber
    implements CoreSubscriber<Message<?>>,
    Disposable,
    Lifecycle {
        private final Consumer<Message<?>> consumer;
        private Subscription subscription;
        private final MessageHandler messageHandler;

        MessageHandlerSubscriber(MessageHandler messageHandler) {
            Assert.notNull((Object)messageHandler, (String)"'messageHandler' must not be null");
            this.messageHandler = messageHandler;
            this.consumer = arg_0 -> ((MessageHandler)this.messageHandler).handleMessage(arg_0);
        }

        public void onSubscribe(Subscription s) {
            this.subscription = s;
            s.request(Long.MAX_VALUE);
        }

        public void onNext(Message<?> message) {
            this.consumer.accept(message);
        }

        public void onError(Throwable t) {
        }

        public void onComplete() {
            this.dispose();
        }

        public void dispose() {
            Subscription s = this.subscription;
            if (s != null) {
                this.subscription = null;
                s.cancel();
            }
        }

        public boolean isDisposed() {
            return this.subscription == null;
        }

        public void start() {
            if (this.messageHandler instanceof Lifecycle) {
                ((Lifecycle)this.messageHandler).start();
            }
        }

        public void stop() {
            if (this.messageHandler instanceof Lifecycle) {
                ((Lifecycle)this.messageHandler).stop();
            }
        }

        public boolean isRunning() {
            return !(this.messageHandler instanceof Lifecycle) || ((Lifecycle)this.messageHandler).isRunning();
        }
    }

    private static final class SubscriberDecorator
    extends BaseSubscriber<Message<?>> {
        private final Subscriber<Message<?>> delegate;
        private final ErrorHandler errorHandler;

        SubscriberDecorator(Subscriber<Message<?>> delegate, ErrorHandler errorHandler) {
            this.delegate = delegate;
            this.errorHandler = errorHandler;
        }

        protected void hookOnSubscribe(Subscription subscription) {
            this.delegate.onSubscribe(subscription);
        }

        protected void hookOnNext(Message<?> value) {
            try {
                this.delegate.onNext(value);
            }
            catch (Exception ex) {
                this.errorHandler.handleError((Throwable)ex);
            }
        }

        protected void hookOnComplete() {
            this.delegate.onComplete();
        }
    }
}

