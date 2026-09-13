/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.security.web.server.util.matcher;

import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;

public class ServerWebExchangeMatcherEntry<T> {
    private final ServerWebExchangeMatcher matcher;
    private final T entry;

    public ServerWebExchangeMatcherEntry(ServerWebExchangeMatcher matcher, T entry) {
        this.matcher = matcher;
        this.entry = entry;
    }

    public ServerWebExchangeMatcher getMatcher() {
        return this.matcher;
    }

    public T getEntry() {
        return this.entry;
    }
}

