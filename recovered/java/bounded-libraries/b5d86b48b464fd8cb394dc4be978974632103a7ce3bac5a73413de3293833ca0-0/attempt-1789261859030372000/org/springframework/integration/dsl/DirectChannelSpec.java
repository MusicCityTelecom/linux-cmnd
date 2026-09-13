/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.integration.dsl;

import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.LoadBalancingChannelSpec;

public class DirectChannelSpec
extends LoadBalancingChannelSpec<DirectChannelSpec, DirectChannel> {
    @Override
    protected DirectChannel doGet() {
        this.channel = new DirectChannel(this.loadBalancingStrategy);
        if (this.failover != null) {
            ((DirectChannel)this.channel).setFailover(this.failover);
        }
        if (this.maxSubscribers != null) {
            ((DirectChannel)this.channel).setMaxSubscribers(this.maxSubscribers);
        }
        return (DirectChannel)super.doGet();
    }
}

