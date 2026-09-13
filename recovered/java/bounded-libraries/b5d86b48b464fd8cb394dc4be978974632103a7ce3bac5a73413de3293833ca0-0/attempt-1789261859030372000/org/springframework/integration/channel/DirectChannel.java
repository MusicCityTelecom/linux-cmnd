/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.lang.Nullable
 */
package org.springframework.integration.channel;

import org.springframework.integration.channel.AbstractSubscribableChannel;
import org.springframework.integration.dispatcher.LoadBalancingStrategy;
import org.springframework.integration.dispatcher.RoundRobinLoadBalancingStrategy;
import org.springframework.integration.dispatcher.UnicastingDispatcher;
import org.springframework.lang.Nullable;

public class DirectChannel
extends AbstractSubscribableChannel {
    private final UnicastingDispatcher dispatcher = new UnicastingDispatcher();
    private volatile Integer maxSubscribers;

    public DirectChannel() {
        this(new RoundRobinLoadBalancingStrategy());
    }

    public DirectChannel(@Nullable LoadBalancingStrategy loadBalancingStrategy) {
        this.dispatcher.setLoadBalancingStrategy(loadBalancingStrategy);
    }

    public void setFailover(boolean failover) {
        this.dispatcher.setFailover(failover);
    }

    public void setMaxSubscribers(int maxSubscribers) {
        this.maxSubscribers = maxSubscribers;
        this.dispatcher.setMaxSubscribers(maxSubscribers);
    }

    @Override
    protected UnicastingDispatcher getDispatcher() {
        return this.dispatcher;
    }

    @Override
    protected void onInit() {
        super.onInit();
        if (this.maxSubscribers == null) {
            Integer max = this.getIntegrationProperty("spring.integration.channels.maxUnicastSubscribers", Integer.class);
            this.setMaxSubscribers(max);
        }
    }
}

