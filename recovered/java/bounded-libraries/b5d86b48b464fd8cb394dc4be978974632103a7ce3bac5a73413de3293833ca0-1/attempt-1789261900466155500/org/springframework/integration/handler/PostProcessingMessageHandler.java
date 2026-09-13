/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.messaging.Message
 */
package org.springframework.integration.handler;

import org.springframework.messaging.Message;

public interface PostProcessingMessageHandler {
    public Object postProcess(Message<?> var1, Object var2);
}

