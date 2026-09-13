/*
 * Decompiled with CFR 0.152.
 */
package com.github.benmanes.caffeine.cache;

import com.github.benmanes.caffeine.cache.DisabledTicker;
import com.github.benmanes.caffeine.cache.SystemTicker;

@FunctionalInterface
public interface Ticker {
    public long read();

    public static Ticker systemTicker() {
        return SystemTicker.INSTANCE;
    }

    public static Ticker disabledTicker() {
        return DisabledTicker.INSTANCE;
    }
}

