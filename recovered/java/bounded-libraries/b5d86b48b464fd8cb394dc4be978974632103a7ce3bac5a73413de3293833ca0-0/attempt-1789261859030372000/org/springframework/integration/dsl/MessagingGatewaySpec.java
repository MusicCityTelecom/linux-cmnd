/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.messaging.MessageChannel
 */
package org.springframework.integration.dsl;

import org.springframework.integration.dsl.IntegrationComponentSpec;
import org.springframework.integration.gateway.MessagingGatewaySupport;
import org.springframework.integration.mapping.InboundMessageMapper;
import org.springframework.integration.mapping.OutboundMessageMapper;
import org.springframework.messaging.MessageChannel;

public abstract class MessagingGatewaySpec<S extends MessagingGatewaySpec<S, G>, G extends MessagingGatewaySupport>
extends IntegrationComponentSpec<S, G> {
    public MessagingGatewaySpec(G gateway2) {
        this.target = gateway2;
    }

    @Override
    public S id(String id) {
        ((MessagingGatewaySupport)this.target).setBeanName(id);
        return (S)((Object)((MessagingGatewaySpec)((Object)super.id(id))));
    }

    public S phase(int phase) {
        ((MessagingGatewaySupport)this.target).setPhase(phase);
        return (S)((Object)((MessagingGatewaySpec)((Object)this._this())));
    }

    public S autoStartup(boolean autoStartup) {
        ((MessagingGatewaySupport)this.target).setAutoStartup(autoStartup);
        return (S)((Object)((MessagingGatewaySpec)((Object)this._this())));
    }

    public S replyChannel(MessageChannel replyChannel) {
        ((MessagingGatewaySupport)this.target).setReplyChannel(replyChannel);
        return (S)((Object)((MessagingGatewaySpec)((Object)this._this())));
    }

    public S replyChannel(String replyChannelName) {
        ((MessagingGatewaySupport)this.target).setReplyChannelName(replyChannelName);
        return (S)((Object)((MessagingGatewaySpec)((Object)this._this())));
    }

    public S requestChannel(MessageChannel requestChannel) {
        ((MessagingGatewaySupport)this.target).setRequestChannel(requestChannel);
        return (S)((Object)((MessagingGatewaySpec)((Object)this._this())));
    }

    public S requestChannel(String requestChannelName) {
        ((MessagingGatewaySupport)this.target).setRequestChannelName(requestChannelName);
        return (S)((Object)((MessagingGatewaySpec)((Object)this._this())));
    }

    public S errorChannel(MessageChannel errorChannel) {
        ((MessagingGatewaySupport)this.target).setErrorChannel(errorChannel);
        return (S)((Object)((MessagingGatewaySpec)((Object)this._this())));
    }

    public S errorChannel(String errorChannelName) {
        ((MessagingGatewaySupport)this.target).setErrorChannelName(errorChannelName);
        return (S)((Object)((MessagingGatewaySpec)((Object)this._this())));
    }

    public S requestTimeout(long requestTimeout) {
        ((MessagingGatewaySupport)this.target).setRequestTimeout(requestTimeout);
        return (S)((Object)((MessagingGatewaySpec)((Object)this._this())));
    }

    public S replyTimeout(long replyTimeout) {
        ((MessagingGatewaySupport)this.target).setReplyTimeout(replyTimeout);
        return (S)((Object)((MessagingGatewaySpec)((Object)this._this())));
    }

    public S errorOnTimeout(boolean errorOnTimeout) {
        ((MessagingGatewaySupport)this.target).setErrorOnTimeout(errorOnTimeout);
        return (S)((Object)((MessagingGatewaySpec)((Object)this._this())));
    }

    public S requestMapper(InboundMessageMapper<?> requestMapper) {
        ((MessagingGatewaySupport)this.target).setRequestMapper(requestMapper);
        return (S)((Object)((MessagingGatewaySpec)((Object)this._this())));
    }

    public S replyMapper(OutboundMessageMapper<?> replyMapper) {
        ((MessagingGatewaySupport)this.target).setReplyMapper(replyMapper);
        return (S)((Object)((MessagingGatewaySpec)((Object)this._this())));
    }

    public S shouldTrack(boolean shouldTrack) {
        ((MessagingGatewaySupport)this.target).setShouldTrack(shouldTrack);
        return (S)((Object)((MessagingGatewaySpec)((Object)this._this())));
    }
}

