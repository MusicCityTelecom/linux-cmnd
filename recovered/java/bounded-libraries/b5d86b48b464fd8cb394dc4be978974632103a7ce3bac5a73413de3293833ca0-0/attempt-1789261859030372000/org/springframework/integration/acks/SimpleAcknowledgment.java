/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.integration.acks;

@FunctionalInterface
public interface SimpleAcknowledgment {
    public void acknowledge();
}

