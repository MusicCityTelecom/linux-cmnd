/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.boot.web.client.RestTemplateBuilder
 *  org.springframework.boot.web.client.RestTemplateCustomizer
 *  org.springframework.boot.web.client.RestTemplateRequestCustomizer
 *  org.springframework.util.ObjectUtils
 */
package org.springframework.boot.autoconfigure.web.client;

import java.util.Collection;
import java.util.List;
import java.util.function.BiFunction;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.boot.web.client.RestTemplateRequestCustomizer;
import org.springframework.util.ObjectUtils;

public final class RestTemplateBuilderConfigurer {
    private HttpMessageConverters httpMessageConverters;
    private List<RestTemplateCustomizer> restTemplateCustomizers;
    private List<RestTemplateRequestCustomizer<?>> restTemplateRequestCustomizers;

    void setHttpMessageConverters(HttpMessageConverters httpMessageConverters) {
        this.httpMessageConverters = httpMessageConverters;
    }

    void setRestTemplateCustomizers(List<RestTemplateCustomizer> restTemplateCustomizers) {
        this.restTemplateCustomizers = restTemplateCustomizers;
    }

    void setRestTemplateRequestCustomizers(List<RestTemplateRequestCustomizer<?>> restTemplateRequestCustomizers) {
        this.restTemplateRequestCustomizers = restTemplateRequestCustomizers;
    }

    public RestTemplateBuilder configure(RestTemplateBuilder builder) {
        if (this.httpMessageConverters != null) {
            builder = builder.messageConverters(this.httpMessageConverters.getConverters());
        }
        builder = this.addCustomizers(builder, this.restTemplateCustomizers, RestTemplateBuilder::customizers);
        builder = this.addCustomizers(builder, this.restTemplateRequestCustomizers, RestTemplateBuilder::requestCustomizers);
        return builder;
    }

    private <T> RestTemplateBuilder addCustomizers(RestTemplateBuilder builder, List<T> customizers, BiFunction<RestTemplateBuilder, Collection<T>, RestTemplateBuilder> method) {
        if (!ObjectUtils.isEmpty(customizers)) {
            return method.apply(builder, customizers);
        }
        return builder;
    }
}

