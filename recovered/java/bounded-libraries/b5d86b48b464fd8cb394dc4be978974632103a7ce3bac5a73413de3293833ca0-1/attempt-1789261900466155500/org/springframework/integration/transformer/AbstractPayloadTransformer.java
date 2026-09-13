/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.messaging.Message
 */
package org.springframework.integration.transformer;

import org.springframework.integration.transformer.AbstractTransformer;
import org.springframework.messaging.Message;

public abstract class AbstractPayloadTransformer<T, U>
extends AbstractTransformer {
    public final U doTransform(Message<?> message) {
        return this.transformPayload(message.getPayload());
    }

    protected abstract U transformPayload(T var1);
}

