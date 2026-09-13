/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.messaging.MessageChannel
 */
package org.springframework.integration.dsl;

import org.springframework.integration.dsl.IntegrationComponentSpec;
import org.springframework.integration.endpoint.MessageProducerSupport;
import org.springframework.integration.support.ErrorMessageStrategy;
import org.springframework.messaging.MessageChannel;

public abstract class MessageProducerSpec<S extends MessageProducerSpec<S, P>, P extends MessageProducerSupport>
extends IntegrationComponentSpec<S, P> {
    public MessageProducerSpec(P producer) {
        this.target = producer;
    }

    @Override
    public S id(String id) {
        ((MessageProducerSupport)this.target).setBeanName(id);
        return (S)((Object)((MessageProducerSpec)((Object)super.id(id))));
    }

    public S phase(int phase) {
        ((MessageProducerSupport)this.target).setPhase(phase);
        return (S)((Object)((MessageProducerSpec)((Object)this._this())));
    }

    public S autoStartup(boolean autoStartup) {
        ((MessageProducerSupport)this.target).setAutoStartup(autoStartup);
        return (S)((Object)((MessageProducerSpec)((Object)this._this())));
    }

    public S outputChannel(MessageChannel outputChannel) {
        ((MessageProducerSupport)this.target).setOutputChannel(outputChannel);
        return (S)((Object)((MessageProducerSpec)((Object)this._this())));
    }

    public S outputChannel(String outputChannel) {
        ((MessageProducerSupport)this.target).setOutputChannelName(outputChannel);
        return (S)((Object)((MessageProducerSpec)((Object)this._this())));
    }

    public S errorChannel(MessageChannel errorChannel) {
        ((MessageProducerSupport)this.target).setErrorChannel(errorChannel);
        return (S)((Object)((MessageProducerSpec)((Object)this._this())));
    }

    public S errorChannel(String errorChannel) {
        ((MessageProducerSupport)this.target).setErrorChannelName(errorChannel);
        return (S)((Object)((MessageProducerSpec)((Object)this._this())));
    }

    public S sendTimeout(long sendTimeout) {
        ((MessageProducerSupport)this.target).setSendTimeout(sendTimeout);
        return (S)((Object)((MessageProducerSpec)((Object)this._this())));
    }

    public S shouldTrack(boolean shouldTrack) {
        ((MessageProducerSupport)this.target).setShouldTrack(shouldTrack);
        return (S)((Object)((MessageProducerSpec)((Object)this._this())));
    }

    public S errorMessageStrategy(ErrorMessageStrategy errorMessageStrategy) {
        ((MessageProducerSupport)this.target).setErrorMessageStrategy(errorMessageStrategy);
        return (S)((Object)((MessageProducerSpec)((Object)this._this())));
    }
}

