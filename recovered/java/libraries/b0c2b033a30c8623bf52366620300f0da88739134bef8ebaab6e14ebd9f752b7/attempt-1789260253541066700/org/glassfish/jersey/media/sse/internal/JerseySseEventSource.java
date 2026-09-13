/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.media.sse.internal;

import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.sse.InboundSseEvent;
import javax.ws.rs.sse.SseEventSource;
import org.glassfish.jersey.client.ClientExecutor;
import org.glassfish.jersey.client.JerseyWebTarget;
import org.glassfish.jersey.internal.jsr166.Flow;
import org.glassfish.jersey.internal.util.JerseyPublisher;
import org.glassfish.jersey.media.sse.LocalizationMessages;
import org.glassfish.jersey.media.sse.internal.EventProcessor;

public class JerseySseEventSource
implements SseEventSource {
    private static final long DEFAULT_RECONNECT_DELAY = 500L;
    private static final Logger LOGGER = Logger.getLogger(JerseySseEventSource.class.getName());
    private static final Consumer<Flow.Subscription> DEFAULT_SUBSCRIPTION_HANDLER = sseSubscription -> sseSubscription.request(Long.MAX_VALUE);
    private static final Consumer<Throwable> DEFAULT_ERROR_HANDLER = throwable -> LOGGER.log(Level.WARNING, LocalizationMessages.EVENT_SOURCE_DEFAULT_ONERROR(), (Throwable)throwable);
    private JerseyPublisher<InboundSseEvent> publisher;
    private final AtomicReference<EventProcessor.State> state = new AtomicReference<EventProcessor.State>(EventProcessor.State.READY);
    private final JerseyWebTarget endpoint;
    private final long reconnectDelay;
    private final TimeUnit reconnectTimeUnit;
    private final ClientExecutor clientExecutor;

    private JerseySseEventSource(JerseyWebTarget endpoint, long reconnectDelay, TimeUnit reconnectTimeUnit) {
        this.endpoint = endpoint;
        this.reconnectDelay = reconnectDelay;
        this.reconnectTimeUnit = reconnectTimeUnit;
        this.clientExecutor = endpoint.getConfiguration().getClientExecutor();
        this.publisher = new JerseyPublisher(this.clientExecutor::submit, JerseyPublisher.PublisherStrategy.BLOCKING);
    }

    public void onEvent(InboundSseEvent inboundEvent) {
        this.publisher.publish(inboundEvent);
    }

    @Override
    public void register(Consumer<InboundSseEvent> onEvent) {
        this.subscribe(DEFAULT_SUBSCRIPTION_HANDLER, onEvent, DEFAULT_ERROR_HANDLER, () -> {});
    }

    @Override
    public void register(Consumer<InboundSseEvent> onEvent, Consumer<Throwable> onError) {
        this.subscribe(DEFAULT_SUBSCRIPTION_HANDLER, onEvent, onError, () -> {});
    }

    @Override
    public void register(Consumer<InboundSseEvent> onEvent, Consumer<Throwable> onError, Runnable onComplete) {
        this.subscribe(DEFAULT_SUBSCRIPTION_HANDLER, onEvent, onError, onComplete);
    }

    private void subscribe(final Consumer<Flow.Subscription> onSubscribe, final Consumer<InboundSseEvent> onEvent, final Consumer<Throwable> onError, final Runnable onComplete) {
        if (onSubscribe == null || onEvent == null || onError == null || onComplete == null) {
            throw new IllegalArgumentException(LocalizationMessages.PARAMS_NULL());
        }
        this.publisher.subscribe(new Flow.Subscriber<InboundSseEvent>(){

            @Override
            public void onSubscribe(final Flow.Subscription subscription) {
                onSubscribe.accept(new Flow.Subscription(){

                    @Override
                    public void request(long n) {
                        subscription.request(n);
                    }

                    @Override
                    public void cancel() {
                        subscription.cancel();
                    }
                });
            }

            @Override
            public void onNext(InboundSseEvent item) {
                onEvent.accept(item);
            }

            @Override
            public void onError(Throwable throwable) {
                onError.accept(throwable);
            }

            @Override
            public void onComplete() {
                onComplete.run();
            }
        });
    }

    @Override
    public void open() {
        if (!this.state.compareAndSet(EventProcessor.State.READY, EventProcessor.State.OPEN)) {
            switch (this.state.get()) {
                case CLOSED: {
                    throw new IllegalStateException(LocalizationMessages.EVENT_SOURCE_ALREADY_CLOSED());
                }
                case OPEN: {
                    throw new IllegalStateException(LocalizationMessages.EVENT_SOURCE_ALREADY_CONNECTED());
                }
            }
        }
        EventProcessor processor = EventProcessor.builder(this.endpoint, this.state, this.clientExecutor, this::onEvent, this::close).reconnectDelay(this.reconnectDelay, this.reconnectTimeUnit).build();
        this.clientExecutor.submit(processor);
        processor.awaitFirstContact();
    }

    @Override
    public boolean isOpen() {
        return this.state.get() == EventProcessor.State.OPEN;
    }

    @Override
    public boolean close(long timeout, TimeUnit unit) {
        if (this.state.getAndSet(EventProcessor.State.CLOSED) != EventProcessor.State.CLOSED) {
            this.publisher.close();
        }
        return true;
    }

    public static class Builder
    extends SseEventSource.Builder {
        private WebTarget endpoint;
        private long reconnectDelay = 500L;
        private TimeUnit reconnectTimeUnit = TimeUnit.MILLISECONDS;

        @Override
        protected Builder target(WebTarget endpoint) {
            Objects.requireNonNull(endpoint);
            this.endpoint = endpoint;
            return this;
        }

        @Override
        public Builder reconnectingEvery(long delay, TimeUnit unit) {
            this.reconnectDelay = delay;
            this.reconnectTimeUnit = unit;
            return this;
        }

        @Override
        public JerseySseEventSource build() {
            if (this.endpoint instanceof JerseyWebTarget) {
                return new JerseySseEventSource((JerseyWebTarget)this.endpoint, this.reconnectDelay, this.reconnectTimeUnit);
            }
            throw new IllegalArgumentException(LocalizationMessages.UNSUPPORTED_WEBTARGET_TYPE(this.endpoint));
        }
    }
}

