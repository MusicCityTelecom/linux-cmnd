/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.messaging.MessageChannel
 */
package org.springframework.integration.dsl;

import org.springframework.integration.dsl.ConsumerEndpointSpec;
import org.springframework.integration.gateway.GatewayMessageHandler;
import org.springframework.messaging.MessageChannel;

public class GatewayEndpointSpec
extends ConsumerEndpointSpec<GatewayEndpointSpec, GatewayMessageHandler> {
    protected GatewayEndpointSpec(MessageChannel requestChannel) {
        super(new GatewayMessageHandler());
        ((GatewayMessageHandler)this.handler).setRequestChannel(requestChannel);
    }

    protected GatewayEndpointSpec(String requestChannel) {
        super(new GatewayMessageHandler());
        ((GatewayMessageHandler)this.handler).setRequestChannelName(requestChannel);
    }

    public GatewayEndpointSpec replyChannel(MessageChannel replyChannel) {
        ((GatewayMessageHandler)this.handler).setReplyChannel(replyChannel);
        return this;
    }

    public GatewayEndpointSpec replyChannel(String replyChannel) {
        ((GatewayMessageHandler)this.handler).setReplyChannelName(replyChannel);
        return this;
    }

    public GatewayEndpointSpec errorChannel(MessageChannel errorChannel) {
        ((GatewayMessageHandler)this.handler).setErrorChannel(errorChannel);
        return this;
    }

    public GatewayEndpointSpec errorChannel(String errorChannel) {
        ((GatewayMessageHandler)this.handler).setErrorChannelName(errorChannel);
        return this;
    }

    public GatewayEndpointSpec requestTimeout(Long requestTimeout) {
        ((GatewayMessageHandler)this.handler).setRequestTimeout(requestTimeout);
        return this;
    }

    public GatewayEndpointSpec replyTimeout(Long replyTimeout) {
        ((GatewayMessageHandler)this.handler).setReplyTimeout(replyTimeout);
        return this;
    }
}

