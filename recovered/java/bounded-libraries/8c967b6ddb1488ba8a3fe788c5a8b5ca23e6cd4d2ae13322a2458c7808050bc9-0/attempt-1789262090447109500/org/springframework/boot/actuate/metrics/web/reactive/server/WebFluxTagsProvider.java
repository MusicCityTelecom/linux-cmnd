/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.micrometer.core.instrument.Tag
 *  org.springframework.web.server.ServerWebExchange
 */
package org.springframework.boot.actuate.metrics.web.reactive.server;

import io.micrometer.core.instrument.Tag;
import org.springframework.web.server.ServerWebExchange;

@FunctionalInterface
public interface WebFluxTagsProvider {
    public Iterable<Tag> httpRequestTags(ServerWebExchange var1, Throwable var2);
}

