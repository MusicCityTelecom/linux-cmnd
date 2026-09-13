/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.messaging.Message
 */
package org.springframework.integration.core;

import org.springframework.integration.core.GenericSelector;
import org.springframework.messaging.Message;

@FunctionalInterface
public interface MessageSelector
extends GenericSelector<Message<?>> {
    @Override
    public boolean accept(Message<?> var1);
}

