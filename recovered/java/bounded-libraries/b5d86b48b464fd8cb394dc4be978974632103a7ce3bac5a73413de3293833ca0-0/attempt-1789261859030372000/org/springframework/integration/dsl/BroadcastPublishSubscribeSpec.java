/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.messaging.MessageChannel
 *  org.springframework.util.Assert
 */
package org.springframework.integration.dsl;

import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.integration.channel.BroadcastCapableChannel;
import org.springframework.integration.dsl.ComponentsRegistration;
import org.springframework.integration.dsl.IntegrationComponentSpec;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlowBuilder;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.messaging.MessageChannel;
import org.springframework.util.Assert;

public class BroadcastPublishSubscribeSpec
extends IntegrationComponentSpec<BroadcastPublishSubscribeSpec, BroadcastCapableChannel>
implements ComponentsRegistration {
    private final Map<Object, String> subscriberFlows = new LinkedHashMap<Object, String>();
    private int order;

    protected BroadcastPublishSubscribeSpec(BroadcastCapableChannel channel) {
        Assert.state((boolean)channel.isBroadcast(), () -> "the " + channel + " must be in the 'broadcast' state for using from this 'BroadcastPublishSubscribeSpec'");
        this.target = channel;
    }

    public BroadcastPublishSubscribeSpec subscribe(IntegrationFlow subFlow) {
        Assert.notNull((Object)subFlow, (String)"'subFlow' must not be null");
        IntegrationFlowBuilder flowBuilder2 = (IntegrationFlowBuilder)IntegrationFlows.from((MessageChannel)this.target).bridge(consumer -> consumer.order(this.order++));
        MessageChannel subFlowInput = subFlow.getInputChannel();
        if (subFlowInput == null) {
            subFlow.configure(flowBuilder2);
        } else {
            flowBuilder2.channel(subFlowInput);
        }
        this.subscriberFlows.put(flowBuilder2.get(), null);
        return (BroadcastPublishSubscribeSpec)this._this();
    }

    @Override
    public Map<Object, String> getComponentsToRegister() {
        return this.subscriberFlows;
    }
}

