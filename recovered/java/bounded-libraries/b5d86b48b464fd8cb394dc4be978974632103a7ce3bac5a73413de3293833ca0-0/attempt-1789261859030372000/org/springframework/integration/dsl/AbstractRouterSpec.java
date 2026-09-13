/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.messaging.MessageChannel
 */
package org.springframework.integration.dsl;

import org.springframework.integration.dsl.ConsumerEndpointSpec;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.router.AbstractMessageRouter;
import org.springframework.messaging.MessageChannel;

public class AbstractRouterSpec<S extends AbstractRouterSpec<S, R>, R extends AbstractMessageRouter>
extends ConsumerEndpointSpec<S, R> {
    private boolean defaultToParentFlow;

    protected AbstractRouterSpec(R router) {
        super(router);
    }

    public S ignoreSendFailures(boolean ignoreSendFailures) {
        ((AbstractMessageRouter)this.handler).setIgnoreSendFailures(ignoreSendFailures);
        return (S)((AbstractRouterSpec)this._this());
    }

    public S applySequence(boolean applySequence) {
        ((AbstractMessageRouter)this.handler).setApplySequence(applySequence);
        return (S)((AbstractRouterSpec)this._this());
    }

    public S defaultOutputChannel(String channelName) {
        ((AbstractMessageRouter)this.handler).setDefaultOutputChannelName(channelName);
        return (S)((AbstractRouterSpec)this._this());
    }

    public S defaultOutputChannel(MessageChannel channel) {
        ((AbstractMessageRouter)this.handler).setDefaultOutputChannel(channel);
        return (S)((AbstractRouterSpec)this._this());
    }

    public S defaultSubFlowMapping(IntegrationFlow subFlow) {
        return this.defaultOutputChannel(this.obtainInputChannelFromFlow(subFlow, false));
    }

    public S defaultOutputToParentFlow() {
        this.defaultToParentFlow = true;
        return (S)((AbstractRouterSpec)this._this());
    }

    protected boolean isDefaultToParentFlow() {
        return this.defaultToParentFlow;
    }
}

