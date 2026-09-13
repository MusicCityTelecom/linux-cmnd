/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.messaging.MessageChannel
 *  org.springframework.messaging.MessageHandler
 */
package org.springframework.integration.endpoint;

import org.springframework.integration.support.context.NamedComponent;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

public interface IntegrationConsumer
extends NamedComponent {
    public MessageChannel getInputChannel();

    public MessageChannel getOutputChannel();

    public MessageHandler getHandler();
}

