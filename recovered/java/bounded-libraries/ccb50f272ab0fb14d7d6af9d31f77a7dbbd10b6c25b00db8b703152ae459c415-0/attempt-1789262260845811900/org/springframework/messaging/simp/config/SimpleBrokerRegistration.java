/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.lang.Nullable
 *  org.springframework.scheduling.TaskScheduler
 */
package org.springframework.messaging.simp.config;

import org.springframework.lang.Nullable;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.messaging.simp.broker.SimpleBrokerMessageHandler;
import org.springframework.messaging.simp.config.AbstractBrokerRegistration;
import org.springframework.scheduling.TaskScheduler;

public class SimpleBrokerRegistration
extends AbstractBrokerRegistration {
    @Nullable
    private TaskScheduler taskScheduler;
    @Nullable
    private long[] heartbeat;
    @Nullable
    private String selectorHeaderName = "selector";

    public SimpleBrokerRegistration(SubscribableChannel clientInboundChannel, MessageChannel clientOutboundChannel, String[] destinationPrefixes) {
        super(clientInboundChannel, clientOutboundChannel, destinationPrefixes);
    }

    public SimpleBrokerRegistration setTaskScheduler(TaskScheduler taskScheduler) {
        this.taskScheduler = taskScheduler;
        return this;
    }

    public SimpleBrokerRegistration setHeartbeatValue(long[] heartbeat) {
        this.heartbeat = heartbeat;
        return this;
    }

    public void setSelectorHeaderName(@Nullable String selectorHeaderName) {
        this.selectorHeaderName = selectorHeaderName;
    }

    @Override
    protected SimpleBrokerMessageHandler getMessageHandler(SubscribableChannel brokerChannel) {
        SimpleBrokerMessageHandler handler = new SimpleBrokerMessageHandler(this.getClientInboundChannel(), this.getClientOutboundChannel(), brokerChannel, this.getDestinationPrefixes());
        if (this.taskScheduler != null) {
            handler.setTaskScheduler(this.taskScheduler);
        }
        if (this.heartbeat != null) {
            handler.setHeartbeatValue(this.heartbeat);
        }
        handler.setSelectorHeaderName(this.selectorHeaderName);
        return handler;
    }
}

