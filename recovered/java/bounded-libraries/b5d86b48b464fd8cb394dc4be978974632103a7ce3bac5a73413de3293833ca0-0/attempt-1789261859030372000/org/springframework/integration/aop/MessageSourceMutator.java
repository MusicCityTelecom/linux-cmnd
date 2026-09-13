/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.lang.Nullable
 *  org.springframework.messaging.Message
 */
package org.springframework.integration.aop;

import org.springframework.integration.aop.ReceiveMessageAdvice;
import org.springframework.integration.core.MessageSource;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;

@FunctionalInterface
public interface MessageSourceMutator
extends ReceiveMessageAdvice {
    @Override
    default public boolean beforeReceive(Object source) {
        if (source instanceof MessageSource) {
            return this.beforeReceive((MessageSource)source);
        }
        throw new IllegalArgumentException("The 'MessageSourceMutator' supports only a 'MessageSource' in the before/after hooks: " + source);
    }

    default public boolean beforeReceive(MessageSource<?> source) {
        return true;
    }

    @Override
    @Nullable
    default public Message<?> afterReceive(@Nullable Message<?> result, Object source) {
        if (source instanceof MessageSource) {
            return this.afterReceive(result, (MessageSource)source);
        }
        throw new IllegalArgumentException("The 'MessageSourceMutator' supports only a 'MessageSource' in the before/after hooks: " + source);
    }

    @Nullable
    public Message<?> afterReceive(@Nullable Message<?> var1, MessageSource<?> var2);
}

