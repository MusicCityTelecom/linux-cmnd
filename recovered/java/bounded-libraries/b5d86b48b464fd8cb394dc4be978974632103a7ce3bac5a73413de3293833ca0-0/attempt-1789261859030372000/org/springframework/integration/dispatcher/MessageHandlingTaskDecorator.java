/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.messaging.support.MessageHandlingRunnable
 */
package org.springframework.integration.dispatcher;

import org.springframework.messaging.support.MessageHandlingRunnable;

@FunctionalInterface
public interface MessageHandlingTaskDecorator {
    public Runnable decorate(MessageHandlingRunnable var1);
}

