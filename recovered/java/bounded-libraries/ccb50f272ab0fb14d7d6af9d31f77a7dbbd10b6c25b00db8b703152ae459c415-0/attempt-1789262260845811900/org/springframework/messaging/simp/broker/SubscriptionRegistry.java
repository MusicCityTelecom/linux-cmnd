/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.MultiValueMap
 */
package org.springframework.messaging.simp.broker;

import org.springframework.messaging.Message;
import org.springframework.util.MultiValueMap;

public interface SubscriptionRegistry {
    public void registerSubscription(Message<?> var1);

    public void unregisterSubscription(Message<?> var1);

    public void unregisterAllSubscriptions(String var1);

    public MultiValueMap<String, String> findSubscriptions(Message<?> var1);
}

