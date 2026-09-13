/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.integration.dsl;

import org.springframework.integration.channel.AbstractMessageChannel;
import org.springframework.integration.dispatcher.LoadBalancingStrategy;
import org.springframework.integration.dispatcher.RoundRobinLoadBalancingStrategy;
import org.springframework.integration.dsl.MessageChannelSpec;

public abstract class LoadBalancingChannelSpec<S extends MessageChannelSpec<S, C>, C extends AbstractMessageChannel>
extends MessageChannelSpec<S, C> {
    protected LoadBalancingStrategy loadBalancingStrategy = new RoundRobinLoadBalancingStrategy();
    protected Boolean failover;
    protected Integer maxSubscribers;

    protected LoadBalancingChannelSpec() {
    }

    public S loadBalancer(LoadBalancingStrategy loadBalancingStrategyToSet) {
        this.loadBalancingStrategy = loadBalancingStrategyToSet;
        return (S)((MessageChannelSpec)this._this());
    }

    public S failover(Boolean failoverToSet) {
        this.failover = failoverToSet;
        return (S)((MessageChannelSpec)this._this());
    }

    public S maxSubscribers(Integer maxSubscribersToSet) {
        this.maxSubscribers = maxSubscribersToSet;
        return (S)((MessageChannelSpec)this._this());
    }
}

