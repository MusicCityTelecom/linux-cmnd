/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.micrometer.core.instrument.Tag
 *  org.springframework.http.HttpRequest
 *  org.springframework.http.client.ClientHttpResponse
 */
package org.springframework.boot.actuate.metrics.web.client;

import io.micrometer.core.instrument.Tag;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpResponse;

@FunctionalInterface
public interface RestTemplateExchangeTagsProvider {
    public Iterable<Tag> getTags(String var1, HttpRequest var2, ClientHttpResponse var3);
}

