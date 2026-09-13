/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.messaging.MessageHandler
 */
package org.springframework.integration.dsl;

import org.springframework.integration.dsl.IntegrationComponentSpec;
import org.springframework.messaging.MessageHandler;

public abstract class MessageHandlerSpec<S extends MessageHandlerSpec<S, H>, H extends MessageHandler>
extends IntegrationComponentSpec<S, H> {
}

