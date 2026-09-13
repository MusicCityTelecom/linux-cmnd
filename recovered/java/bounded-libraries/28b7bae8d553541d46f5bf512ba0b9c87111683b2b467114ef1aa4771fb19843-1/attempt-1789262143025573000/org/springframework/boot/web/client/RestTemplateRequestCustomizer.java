/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.http.client.ClientHttpRequest
 */
package org.springframework.boot.web.client;

import org.springframework.http.client.ClientHttpRequest;

@FunctionalInterface
public interface RestTemplateRequestCustomizer<T extends ClientHttpRequest> {
    public void customize(T var1);
}

