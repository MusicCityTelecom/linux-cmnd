/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.messaging.MessageHandler
 */
package org.springframework.integration.handler;

import java.util.List;
import org.springframework.messaging.MessageHandler;

public interface CompositeMessageHandler
extends MessageHandler {
    public List<MessageHandler> getHandlers();
}

