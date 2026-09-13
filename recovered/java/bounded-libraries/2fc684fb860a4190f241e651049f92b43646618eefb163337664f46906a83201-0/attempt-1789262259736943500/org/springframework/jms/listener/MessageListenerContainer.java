/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.context.SmartLifecycle
 *  org.springframework.lang.Nullable
 */
package org.springframework.jms.listener;

import org.springframework.context.SmartLifecycle;
import org.springframework.jms.support.QosSettings;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.destination.DestinationResolver;
import org.springframework.lang.Nullable;

public interface MessageListenerContainer
extends SmartLifecycle {
    public void setupMessageListener(Object var1);

    @Nullable
    public MessageConverter getMessageConverter();

    @Nullable
    public DestinationResolver getDestinationResolver();

    public boolean isPubSubDomain();

    public boolean isReplyPubSubDomain();

    @Nullable
    public QosSettings getReplyQosSettings();
}

