/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.lang.Nullable
 */
package org.springframework.messaging.tcp;

import org.springframework.lang.Nullable;

@FunctionalInterface
public interface ReconnectStrategy {
    @Nullable
    public Long getTimeToNextAttempt(int var1);
}

