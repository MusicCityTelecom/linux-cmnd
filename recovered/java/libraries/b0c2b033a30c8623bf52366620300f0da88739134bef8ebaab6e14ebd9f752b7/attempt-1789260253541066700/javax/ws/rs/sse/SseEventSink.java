/*
 * Decompiled with CFR 0.152.
 */
package javax.ws.rs.sse;

import java.util.concurrent.CompletionStage;
import javax.ws.rs.sse.OutboundSseEvent;

public interface SseEventSink
extends AutoCloseable {
    public boolean isClosed();

    public CompletionStage<?> send(OutboundSseEvent var1);

    @Override
    public void close();
}

