/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.media.sse.internal;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import javax.ws.rs.sse.OutboundSseEvent;
import javax.ws.rs.sse.SseBroadcaster;
import javax.ws.rs.sse.SseEventSink;
import org.glassfish.jersey.internal.jsr166.Flow;
import org.glassfish.jersey.internal.util.JerseyPublisher;
import org.glassfish.jersey.media.sse.LocalizationMessages;

class JerseySseBroadcaster
extends JerseyPublisher<OutboundSseEvent>
implements SseBroadcaster {
    private final CopyOnWriteArrayList<Consumer<SseEventSink>> onCloseListeners;
    private final CopyOnWriteArrayList<BiConsumer<SseEventSink, Throwable>> onExceptionListeners = new CopyOnWriteArrayList();

    JerseySseBroadcaster() {
        this.onCloseListeners = new CopyOnWriteArrayList();
    }

    JerseySseBroadcaster(ExecutorService executorService) {
        super(executorService);
        this.onCloseListeners = new CopyOnWriteArrayList();
    }

    @Override
    public void register(SseEventSink sseEventSink) {
        super.subscribe(new SseEventSinkWrapper(sseEventSink));
    }

    @Override
    public void onError(BiConsumer<SseEventSink, Throwable> onError) {
        if (onError == null) {
            throw new IllegalArgumentException(LocalizationMessages.PARAM_NULL("onError"));
        }
        this.onExceptionListeners.add(onError);
    }

    @Override
    public void onClose(Consumer<SseEventSink> onClose) {
        if (onClose == null) {
            throw new IllegalArgumentException(LocalizationMessages.PARAM_NULL("onClose"));
        }
        this.onCloseListeners.add(onClose);
    }

    @Override
    public CompletionStage<?> broadcast(OutboundSseEvent event) {
        if (event == null) {
            throw new IllegalArgumentException(LocalizationMessages.PARAM_NULL("event"));
        }
        return CompletableFuture.completedFuture(this.publish(event));
    }

    private void notifyOnCompleteHandlers(Flow.Subscriber<? super OutboundSseEvent> subscriber) {
        if (subscriber instanceof SseEventSinkWrapper) {
            this.onCloseListeners.forEach((Consumer<Consumer<SseEventSink>>)((Consumer<Consumer>)listener -> listener.accept(((SseEventSinkWrapper)subscriber).sseEventSink)));
        }
    }

    private void notifyOnErrorCallbacks(Flow.Subscriber<? super OutboundSseEvent> subscriber, Throwable throwable) {
        if (subscriber instanceof SseEventSinkWrapper) {
            this.onExceptionListeners.forEach((Consumer<BiConsumer<SseEventSink, Throwable>>)((Consumer<BiConsumer>)listener -> listener.accept(((SseEventSinkWrapper)subscriber).sseEventSink, throwable)));
        }
    }

    private class SseEventSinkWrapper
    implements Flow.Subscriber<OutboundSseEvent> {
        private final SseEventSink sseEventSink;

        SseEventSinkWrapper(SseEventSink sseEventSink) {
            this.sseEventSink = sseEventSink;
        }

        @Override
        public void onSubscribe(Flow.Subscription subscription) {
            subscription.request(Long.MAX_VALUE);
        }

        @Override
        public void onNext(OutboundSseEvent item) {
            this.sseEventSink.send(item);
        }

        @Override
        public void onError(Throwable throwable) {
            this.sseEventSink.close();
            JerseySseBroadcaster.this.notifyOnErrorCallbacks(this, throwable);
        }

        @Override
        public void onComplete() {
            this.sseEventSink.close();
            JerseySseBroadcaster.this.notifyOnCompleteHandlers(this);
        }
    }
}

