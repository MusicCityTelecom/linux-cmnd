/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.integration.aggregator;

import org.springframework.integration.store.MessageGroup;

@FunctionalInterface
public interface ReleaseStrategy {
    public boolean canRelease(MessageGroup var1);
}

