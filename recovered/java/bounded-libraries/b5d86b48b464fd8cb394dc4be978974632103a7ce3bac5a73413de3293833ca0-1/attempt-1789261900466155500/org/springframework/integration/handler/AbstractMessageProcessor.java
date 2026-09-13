/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.lang.Nullable
 *  org.springframework.messaging.Message
 */
package org.springframework.integration.handler;

import org.springframework.integration.handler.MessageProcessor;
import org.springframework.integration.util.AbstractExpressionEvaluator;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;

public abstract class AbstractMessageProcessor<T>
extends AbstractExpressionEvaluator
implements MessageProcessor<T> {
    @Override
    @Nullable
    public abstract T processMessage(Message<?> var1);
}

