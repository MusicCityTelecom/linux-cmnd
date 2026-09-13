/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.http.client.config.RequestConfig$Builder
 *  org.apache.http.impl.nio.client.HttpAsyncClientBuilder
 *  org.elasticsearch.client.RestClientBuilder
 */
package org.springframework.boot.autoconfigure.elasticsearch;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.RestClientBuilder;

@FunctionalInterface
public interface RestClientBuilderCustomizer {
    public void customize(RestClientBuilder var1);

    default public void customize(HttpAsyncClientBuilder builder) {
    }

    default public void customize(RequestConfig.Builder builder) {
    }
}

