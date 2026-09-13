/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.messaging.Message
 */
package org.springframework.integration.handler;

import org.springframework.messaging.Message;

@FunctionalInterface
public interface MessageTriggerAction {
    public void trigger(Message<?> var1);
}

