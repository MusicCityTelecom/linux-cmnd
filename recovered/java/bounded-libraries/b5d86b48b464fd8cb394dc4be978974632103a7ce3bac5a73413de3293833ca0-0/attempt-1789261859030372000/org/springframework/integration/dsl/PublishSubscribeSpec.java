/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.lang.Nullable
 */
package org.springframework.integration.dsl;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import org.springframework.integration.channel.BroadcastCapableChannel;
import org.springframework.integration.dsl.BroadcastPublishSubscribeSpec;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.PublishSubscribeChannelSpec;
import org.springframework.lang.Nullable;

public class PublishSubscribeSpec
extends PublishSubscribeChannelSpec<PublishSubscribeSpec> {
    private final BroadcastPublishSubscribeSpec delegate;

    protected PublishSubscribeSpec() {
        this.delegate = new BroadcastPublishSubscribeSpec((BroadcastCapableChannel)((Object)this.channel));
    }

    protected PublishSubscribeSpec(@Nullable Executor executor) {
        super(executor);
        this.delegate = new BroadcastPublishSubscribeSpec((BroadcastCapableChannel)((Object)this.channel));
    }

    @Override
    public PublishSubscribeSpec id(String id) {
        return (PublishSubscribeSpec)super.id(id);
    }

    public PublishSubscribeSpec subscribe(IntegrationFlow subFlow) {
        this.delegate.subscribe(subFlow);
        return (PublishSubscribeSpec)this._this();
    }

    @Override
    public Map<Object, String> getComponentsToRegister() {
        LinkedHashMap<Object, String> objects = new LinkedHashMap<Object, String>();
        objects.putAll(super.getComponentsToRegister());
        objects.putAll(this.delegate.getComponentsToRegister());
        return objects;
    }
}

