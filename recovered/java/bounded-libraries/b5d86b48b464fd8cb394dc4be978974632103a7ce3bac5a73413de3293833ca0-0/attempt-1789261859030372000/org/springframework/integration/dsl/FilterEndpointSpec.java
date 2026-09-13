/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.messaging.MessageChannel
 */
package org.springframework.integration.dsl;

import org.springframework.integration.dsl.ConsumerEndpointSpec;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.filter.MessageFilter;
import org.springframework.messaging.MessageChannel;

public class FilterEndpointSpec
extends ConsumerEndpointSpec<FilterEndpointSpec, MessageFilter> {
    protected FilterEndpointSpec(MessageFilter messageFilter) {
        super(messageFilter);
    }

    public FilterEndpointSpec throwExceptionOnRejection(boolean throwExceptionOnRejection) {
        ((MessageFilter)this.handler).setThrowExceptionOnRejection(throwExceptionOnRejection);
        return (FilterEndpointSpec)this._this();
    }

    public FilterEndpointSpec discardChannel(MessageChannel discardChannel) {
        ((MessageFilter)this.handler).setDiscardChannel(discardChannel);
        return (FilterEndpointSpec)this._this();
    }

    public FilterEndpointSpec discardChannel(String discardChannelName) {
        ((MessageFilter)this.handler).setDiscardChannelName(discardChannelName);
        return (FilterEndpointSpec)this._this();
    }

    public FilterEndpointSpec discardFlow(IntegrationFlow discardFlow) {
        return this.discardChannel(this.obtainInputChannelFromFlow(discardFlow));
    }

    public FilterEndpointSpec discardWithinAdvice(boolean discardWithinAdvice) {
        ((MessageFilter)this.handler).setDiscardWithinAdvice(discardWithinAdvice);
        return (FilterEndpointSpec)this._this();
    }
}

