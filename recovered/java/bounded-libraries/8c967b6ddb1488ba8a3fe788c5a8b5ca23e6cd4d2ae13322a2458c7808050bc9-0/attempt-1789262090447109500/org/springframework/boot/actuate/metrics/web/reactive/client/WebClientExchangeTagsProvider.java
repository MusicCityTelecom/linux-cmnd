/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.micrometer.core.instrument.Tag
 *  org.springframework.web.reactive.function.client.ClientRequest
 *  org.springframework.web.reactive.function.client.ClientResponse
 */
package org.springframework.boot.actuate.metrics.web.reactive.client;

import io.micrometer.core.instrument.Tag;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;

@FunctionalInterface
public interface WebClientExchangeTagsProvider {
    public Iterable<Tag> tags(ClientRequest var1, ClientResponse var2, Throwable var3);
}

