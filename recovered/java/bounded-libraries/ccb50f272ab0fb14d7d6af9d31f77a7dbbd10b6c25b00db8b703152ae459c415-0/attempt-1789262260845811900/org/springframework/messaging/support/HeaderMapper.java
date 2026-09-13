/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.messaging.support;

import org.springframework.messaging.MessageHeaders;

public interface HeaderMapper<T> {
    public void fromHeaders(MessageHeaders var1, T var2);

    public MessageHeaders toHeaders(T var1);
}

