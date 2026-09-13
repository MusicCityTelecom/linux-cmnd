/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.messaging.MessageHeaders
 */
package org.springframework.integration.handler;

import org.springframework.messaging.MessageHeaders;

@FunctionalInterface
public interface GenericHandler<P> {
    public Object handle(P var1, MessageHeaders var2);
}

