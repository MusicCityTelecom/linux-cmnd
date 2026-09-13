/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.media.sse;

import javax.ws.rs.core.Configurable;
import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.core.MediaType;
import org.glassfish.jersey.internal.util.Property;
import org.glassfish.jersey.media.sse.EventInputReader;
import org.glassfish.jersey.media.sse.InboundEventReader;
import org.glassfish.jersey.media.sse.OutboundEventWriter;
import org.glassfish.jersey.media.sse.internal.SseBinder;
import org.glassfish.jersey.media.sse.internal.SseEventSinkValueParamProvider;
import org.glassfish.jersey.server.spi.internal.ValueParamProvider;

public class SseFeature
implements Feature {
    public static final String SERVER_SENT_EVENTS = "text/event-stream";
    public static final MediaType SERVER_SENT_EVENTS_TYPE = MediaType.valueOf("text/event-stream");
    @Property
    public static final String DISABLE_SSE = "jersey.config.media.sse.disable";
    @Property
    public static final String DISABLE_SSE_CLIENT = "jersey.config.client.media.sse.disable";
    @Property
    public static final String DISABLE_SSE_SERVER = "jersey.config.server.media.sse.disable";
    public static final long RECONNECT_NOT_SET = -1L;
    public static final String LAST_EVENT_ID_HEADER = "Last-Event-ID";

    @Override
    public boolean configure(FeatureContext context) {
        if (context.getConfiguration().isEnabled(this.getClass())) {
            return false;
        }
        switch (context.getConfiguration().getRuntimeType()) {
            case CLIENT: {
                context.register(EventInputReader.class);
                context.register(InboundEventReader.class);
                break;
            }
            case SERVER: {
                context.register(OutboundEventWriter.class);
                context.register(new SseBinder());
                context.register(SseEventSinkValueParamProvider.class, ValueParamProvider.class);
            }
        }
        return true;
    }

    static <T extends Configurable<T>> T register(T ctx) {
        if (!ctx.getConfiguration().isRegistered(SseFeature.class)) {
            ctx.register(SseFeature.class);
        }
        return ctx;
    }
}

