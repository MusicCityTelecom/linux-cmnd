/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.messaging.MessageHandler
 */
package org.springframework.integration.endpoint;

import org.springframework.messaging.MessageHandler;

public interface PollingOperations {
    public boolean poll(MessageHandler var1);
}

