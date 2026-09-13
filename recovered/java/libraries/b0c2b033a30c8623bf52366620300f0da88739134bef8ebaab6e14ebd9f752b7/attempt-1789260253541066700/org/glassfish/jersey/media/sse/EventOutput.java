/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.media.sse;

import java.nio.charset.Charset;
import org.glassfish.jersey.media.sse.OutboundEvent;
import org.glassfish.jersey.server.ChunkedOutput;

public class EventOutput
extends ChunkedOutput<OutboundEvent> {
    private static final byte[] SSE_EVENT_DELIMITER = "\n".getBytes(Charset.forName("UTF-8"));

    public EventOutput() {
        super(SSE_EVENT_DELIMITER);
    }
}

