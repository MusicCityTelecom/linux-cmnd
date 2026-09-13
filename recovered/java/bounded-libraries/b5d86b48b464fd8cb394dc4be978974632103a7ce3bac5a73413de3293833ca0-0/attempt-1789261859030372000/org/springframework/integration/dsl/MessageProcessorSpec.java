/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.integration.dsl;

import org.springframework.integration.dsl.IntegrationComponentSpec;
import org.springframework.integration.handler.MessageProcessor;

public abstract class MessageProcessorSpec<S extends MessageProcessorSpec<S>>
extends IntegrationComponentSpec<S, MessageProcessor<?>> {
}

