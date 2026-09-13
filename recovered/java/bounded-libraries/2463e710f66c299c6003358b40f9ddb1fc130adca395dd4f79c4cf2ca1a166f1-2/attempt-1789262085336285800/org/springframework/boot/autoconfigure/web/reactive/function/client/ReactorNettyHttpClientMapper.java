/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  reactor.netty.http.client.HttpClient
 */
package org.springframework.boot.autoconfigure.web.reactive.function.client;

import reactor.netty.http.client.HttpClient;

@FunctionalInterface
public interface ReactorNettyHttpClientMapper {
    public HttpClient configure(HttpClient var1);
}

