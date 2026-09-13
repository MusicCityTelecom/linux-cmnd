/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.lang.Nullable
 */
package org.springframework.messaging.handler;

import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;

public interface MessageCondition<T> {
    public T combine(T var1);

    @Nullable
    public T getMatchingCondition(Message<?> var1);

    public int compareTo(T var1, Message<?> var2);
}

