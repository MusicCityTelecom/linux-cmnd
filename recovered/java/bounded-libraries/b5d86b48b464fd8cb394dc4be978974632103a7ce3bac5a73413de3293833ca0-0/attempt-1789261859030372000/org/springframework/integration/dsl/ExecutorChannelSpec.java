/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.integration.dsl;

import java.util.concurrent.Executor;
import org.springframework.integration.channel.ExecutorChannel;
import org.springframework.integration.dsl.LoadBalancingChannelSpec;

public class ExecutorChannelSpec
extends LoadBalancingChannelSpec<ExecutorChannelSpec, ExecutorChannel> {
    private final Executor executor;

    protected ExecutorChannelSpec(Executor executor) {
        this.executor = executor;
    }

    @Override
    protected ExecutorChannel doGet() {
        this.channel = new ExecutorChannel(this.executor, this.loadBalancingStrategy);
        if (this.failover != null) {
            ((ExecutorChannel)this.channel).setFailover(this.failover);
        }
        if (this.maxSubscribers != null) {
            ((ExecutorChannel)this.channel).setMaxSubscribers(this.maxSubscribers);
        }
        return (ExecutorChannel)super.doGet();
    }
}

