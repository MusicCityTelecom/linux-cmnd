/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.messaging.MessageChannel
 */
package org.springframework.integration.dsl;

import org.springframework.integration.dsl.ConsumerEndpointSpec;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.splitter.AbstractMessageSplitter;
import org.springframework.integration.splitter.DefaultMessageSplitter;
import org.springframework.messaging.MessageChannel;

public class SplitterEndpointSpec<S extends AbstractMessageSplitter>
extends ConsumerEndpointSpec<SplitterEndpointSpec<S>, S> {
    protected SplitterEndpointSpec(S splitter) {
        super(splitter);
    }

    public SplitterEndpointSpec<S> applySequence(boolean applySequence) {
        ((AbstractMessageSplitter)this.handler).setApplySequence(applySequence);
        return this;
    }

    public SplitterEndpointSpec<S> delimiters(String delimiters) {
        if (this.handler instanceof DefaultMessageSplitter) {
            ((DefaultMessageSplitter)this.handler).setDelimiters(delimiters);
        } else {
            this.logger.warn((Object)"'delimiters' can be applied only for the DefaultMessageSplitter");
        }
        return this;
    }

    public SplitterEndpointSpec<S> discardChannel(MessageChannel discardChannel) {
        ((AbstractMessageSplitter)this.handler).setDiscardChannel(discardChannel);
        return this;
    }

    public SplitterEndpointSpec<S> discardChannel(String discardChannelName) {
        ((AbstractMessageSplitter)this.handler).setDiscardChannelName(discardChannelName);
        return this;
    }

    public SplitterEndpointSpec<S> discardFlow(IntegrationFlow discardFlow) {
        return this.discardChannel(this.obtainInputChannelFromFlow(discardFlow));
    }
}

