/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.messaging.Message
 */
package org.springframework.integration.transformer;

import org.springframework.integration.transformer.GenericTransformer;
import org.springframework.messaging.Message;

@FunctionalInterface
public interface Transformer
extends GenericTransformer<Message<?>, Message<?>> {
    @Override
    public Message<?> transform(Message<?> var1);
}

