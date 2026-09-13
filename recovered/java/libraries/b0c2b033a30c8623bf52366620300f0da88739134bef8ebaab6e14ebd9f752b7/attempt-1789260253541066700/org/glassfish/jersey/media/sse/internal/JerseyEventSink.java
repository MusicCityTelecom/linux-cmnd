/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.media.sse.internal;

import java.io.Flushable;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Provider;
import javax.ws.rs.sse.OutboundSseEvent;
import javax.ws.rs.sse.SseEventSink;
import org.glassfish.jersey.internal.jsr166.Flow;
import org.glassfish.jersey.media.sse.LocalizationMessages;
import org.glassfish.jersey.server.AsyncContext;
import org.glassfish.jersey.server.ChunkedOutput;

class JerseyEventSink
extends ChunkedOutput<OutboundSseEvent>
implements SseEventSink,
Flushable,
Flow.Subscriber<OutboundSseEvent> {
    private static final Logger LOGGER = Logger.getLogger(JerseyEventSink.class.getName());
    private static final byte[] SSE_EVENT_DELIMITER = "\n".getBytes(Charset.forName("UTF-8"));
    private Flow.Subscription subscription = null;

    JerseyEventSink(Provider<AsyncContext> asyncContextProvider) {
        super(SSE_EVENT_DELIMITER, asyncContextProvider);
    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.checkClosed();
        if (subscription == null) {
            throw new NullPointerException(LocalizationMessages.PARAM_NULL("subscription"));
        }
        this.subscription = subscription;
        subscription.request(Long.MAX_VALUE);
    }

    @Override
    public void onNext(OutboundSseEvent item) {
        this.checkClosed();
        if (item == null) {
            throw new NullPointerException(LocalizationMessages.PARAM_NULL("outboundSseEvent"));
        }
        try {
            this.write(item);
        }
        catch (IOException e) {
            this.onError(e);
        }
    }

    @Override
    public void onError(Throwable throwable) {
        this.checkClosed();
        if (throwable == null) {
            throw new NullPointerException(LocalizationMessages.PARAM_NULL("throwable"));
        }
        this.subscription.cancel();
    }

    @Override
    public void close() {
        try {
            super.close();
        }
        catch (IOException e) {
            LOGGER.log(Level.INFO, LocalizationMessages.EVENT_SINK_CLOSE_FAILED(), e);
        }
    }

    @Override
    public CompletionStage<?> send(OutboundSseEvent event) {
        this.checkClosed();
        try {
            this.write(event);
            return CompletableFuture.completedFuture(null);
        }
        catch (IOException e) {
            return CompletableFuture.completedFuture(e);
        }
    }

    @Override
    public void flush() throws IOException {
        super.flushQueue();
    }

    @Override
    public void onComplete() {
        this.checkClosed();
        this.subscription.cancel();
        this.close();
    }

    private void checkClosed() {
        if (this.isClosed()) {
            throw new IllegalStateException(LocalizationMessages.EVENT_SOURCE_ALREADY_CLOSED());
        }
    }
}

