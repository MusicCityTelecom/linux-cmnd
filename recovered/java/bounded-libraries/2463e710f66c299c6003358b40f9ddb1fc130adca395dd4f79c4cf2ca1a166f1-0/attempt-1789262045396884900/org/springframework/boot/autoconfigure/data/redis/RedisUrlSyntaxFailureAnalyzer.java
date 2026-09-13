/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.boot.diagnostics.AbstractFailureAnalyzer
 *  org.springframework.boot.diagnostics.FailureAnalysis
 */
package org.springframework.boot.autoconfigure.data.redis;

import java.net.URI;
import java.net.URISyntaxException;
import org.springframework.boot.autoconfigure.data.redis.RedisUrlSyntaxException;
import org.springframework.boot.diagnostics.AbstractFailureAnalyzer;
import org.springframework.boot.diagnostics.FailureAnalysis;

class RedisUrlSyntaxFailureAnalyzer
extends AbstractFailureAnalyzer<RedisUrlSyntaxException> {
    RedisUrlSyntaxFailureAnalyzer() {
    }

    protected FailureAnalysis analyze(Throwable rootFailure, RedisUrlSyntaxException cause) {
        try {
            URI uri = new URI(cause.getUrl());
            if ("redis-sentinel".equals(uri.getScheme())) {
                return new FailureAnalysis(this.getUnsupportedSchemeDescription(cause.getUrl(), uri.getScheme()), "Use spring.redis.sentinel properties instead of spring.redis.url to configure Redis sentinel addresses.", (Throwable)cause);
            }
            if ("redis-socket".equals(uri.getScheme())) {
                return new FailureAnalysis(this.getUnsupportedSchemeDescription(cause.getUrl(), uri.getScheme()), "Configure the appropriate Spring Data Redis connection beans directly instead of setting the property 'spring.redis.url'.", (Throwable)cause);
            }
            if (!"redis".equals(uri.getScheme()) && !"rediss".equals(uri.getScheme())) {
                return new FailureAnalysis(this.getUnsupportedSchemeDescription(cause.getUrl(), uri.getScheme()), "Use the scheme 'redis://' for insecure or 'rediss://' for secure Redis standalone configuration.", (Throwable)cause);
            }
        }
        catch (URISyntaxException uRISyntaxException) {
            // empty catch block
        }
        return new FailureAnalysis(this.getDefaultDescription(cause.getUrl()), "Review the value of the property 'spring.redis.url'.", (Throwable)cause);
    }

    private String getDefaultDescription(String url) {
        return "The URL '" + url + "' is not valid for configuring Spring Data Redis. ";
    }

    private String getUnsupportedSchemeDescription(String url, String scheme) {
        return this.getDefaultDescription(url) + "The scheme '" + scheme + "' is not supported.";
    }
}

