/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.media.sse;

import org.glassfish.jersey.media.sse.OutboundEvent;
import org.glassfish.jersey.server.Broadcaster;

public class SseBroadcaster
extends Broadcaster<OutboundEvent> {
    public SseBroadcaster() {
        this((Class<? extends SseBroadcaster>)SseBroadcaster.class);
    }

    protected SseBroadcaster(Class<? extends SseBroadcaster> subclass) {
        super(subclass);
    }
}

