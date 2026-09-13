/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.messaging.Message
 */
package org.springframework.integration.support;

import org.springframework.integration.support.AbstractIntegrationMessageBuilder;
import org.springframework.messaging.Message;

public interface MessageBuilderFactory {
    public <T> AbstractIntegrationMessageBuilder<T> fromMessage(Message<T> var1);

    public <T> AbstractIntegrationMessageBuilder<T> withPayload(T var1);
}

