/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.lang.Nullable
 *  org.springframework.util.ErrorHandler
 */
package org.springframework.integration.dsl;

import java.util.concurrent.Executor;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.dsl.MessageChannelSpec;
import org.springframework.lang.Nullable;
import org.springframework.util.ErrorHandler;

public class PublishSubscribeChannelSpec<S extends PublishSubscribeChannelSpec<S>>
extends MessageChannelSpec<S, PublishSubscribeChannel> {
    protected PublishSubscribeChannelSpec() {
        this(false);
    }

    protected PublishSubscribeChannelSpec(boolean requireSubscribers) {
        this(null, requireSubscribers);
    }

    protected PublishSubscribeChannelSpec(@Nullable Executor executor) {
        this(executor, false);
    }

    protected PublishSubscribeChannelSpec(@Nullable Executor executor, boolean requireSubscribers) {
        this.channel = new PublishSubscribeChannel(executor, requireSubscribers);
    }

    public S errorHandler(ErrorHandler errorHandler) {
        ((PublishSubscribeChannel)this.channel).setErrorHandler(errorHandler);
        return (S)((PublishSubscribeChannelSpec)this._this());
    }

    public S ignoreFailures(boolean ignoreFailures) {
        ((PublishSubscribeChannel)this.channel).setIgnoreFailures(ignoreFailures);
        return (S)((PublishSubscribeChannelSpec)this._this());
    }

    public S applySequence(boolean applySequence) {
        ((PublishSubscribeChannel)this.channel).setApplySequence(applySequence);
        return (S)((PublishSubscribeChannelSpec)this._this());
    }

    public S maxSubscribers(Integer maxSubscribers) {
        ((PublishSubscribeChannel)this.channel).setMaxSubscribers(maxSubscribers);
        return (S)((PublishSubscribeChannelSpec)this._this());
    }

    public S minSubscribers(int minSubscribers) {
        ((PublishSubscribeChannel)this.channel).setMinSubscribers(minSubscribers);
        return (S)((PublishSubscribeChannelSpec)this._this());
    }
}

