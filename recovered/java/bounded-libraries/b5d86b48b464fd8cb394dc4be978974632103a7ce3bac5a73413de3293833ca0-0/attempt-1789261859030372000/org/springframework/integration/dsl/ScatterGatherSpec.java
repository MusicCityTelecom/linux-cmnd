/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.messaging.MessageChannel
 */
package org.springframework.integration.dsl;

import org.springframework.integration.dsl.ConsumerEndpointSpec;
import org.springframework.integration.scattergather.ScatterGatherHandler;
import org.springframework.messaging.MessageChannel;

public class ScatterGatherSpec
extends ConsumerEndpointSpec<ScatterGatherSpec, ScatterGatherHandler> {
    protected ScatterGatherSpec(ScatterGatherHandler messageHandler) {
        super(messageHandler);
    }

    public ScatterGatherSpec gatherChannel(MessageChannel gatherChannel) {
        ((ScatterGatherHandler)this.handler).setGatherChannel(gatherChannel);
        return this;
    }

    public ScatterGatherSpec gatherTimeout(long gatherTimeout) {
        ((ScatterGatherHandler)this.handler).setGatherTimeout(gatherTimeout);
        return this;
    }

    public ScatterGatherSpec errorChannel(String errorChannel) {
        ((ScatterGatherHandler)this.handler).setErrorChannelName(errorChannel);
        return this;
    }
}

