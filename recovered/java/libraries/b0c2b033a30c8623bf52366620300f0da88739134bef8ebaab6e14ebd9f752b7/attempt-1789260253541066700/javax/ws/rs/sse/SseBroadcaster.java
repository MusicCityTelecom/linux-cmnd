/*
 * Decompiled with CFR 0.152.
 */
package javax.ws.rs.sse;

import java.util.concurrent.CompletionStage;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import javax.ws.rs.sse.OutboundSseEvent;
import javax.ws.rs.sse.SseEventSink;

public interface SseBroadcaster
extends AutoCloseable {
    public void onError(BiConsumer<SseEventSink, Throwable> var1);

    public void onClose(Consumer<SseEventSink> var1);

    public void register(SseEventSink var1);

    public CompletionStage<?> broadcast(OutboundSseEvent var1);

    @Override
    public void close();
}

