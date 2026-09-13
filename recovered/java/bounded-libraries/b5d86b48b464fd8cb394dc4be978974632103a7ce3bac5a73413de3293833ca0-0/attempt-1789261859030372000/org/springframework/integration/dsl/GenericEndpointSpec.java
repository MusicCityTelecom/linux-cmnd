/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.messaging.MessageHandler
 */
package org.springframework.integration.dsl;

import org.springframework.integration.dsl.ConsumerEndpointSpec;
import org.springframework.messaging.MessageHandler;

public class GenericEndpointSpec<H extends MessageHandler>
extends ConsumerEndpointSpec<GenericEndpointSpec<H>, H> {
    protected GenericEndpointSpec(H messageHandler) {
        super(messageHandler);
    }
}

