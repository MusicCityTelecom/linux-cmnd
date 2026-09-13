/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.messaging.MessageChannel
 */
package org.springframework.integration.router;

import org.springframework.messaging.MessageChannel;

public interface MessageRouter {
    public MessageChannel getDefaultOutputChannel();
}

