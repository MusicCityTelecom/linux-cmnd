/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.integration.dsl;

import org.springframework.integration.aggregator.ResequencingMessageGroupProcessor;
import org.springframework.integration.aggregator.ResequencingMessageHandler;
import org.springframework.integration.dsl.CorrelationHandlerSpec;

public class ResequencerSpec
extends CorrelationHandlerSpec<ResequencerSpec, ResequencingMessageHandler> {
    protected ResequencerSpec() {
        super(new ResequencingMessageHandler(new ResequencingMessageGroupProcessor()));
    }

    public ResequencerSpec releasePartialSequences(boolean releasePartialSequences) {
        ((ResequencingMessageHandler)this.handler).setReleasePartialSequences(releasePartialSequences);
        return (ResequencerSpec)this._this();
    }
}

