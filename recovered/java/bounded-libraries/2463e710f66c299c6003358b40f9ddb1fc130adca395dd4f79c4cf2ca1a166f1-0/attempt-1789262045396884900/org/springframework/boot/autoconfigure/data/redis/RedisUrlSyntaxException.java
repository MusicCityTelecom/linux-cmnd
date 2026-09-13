/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.autoconfigure.data.redis;

class RedisUrlSyntaxException
extends RuntimeException {
    private final String url;

    RedisUrlSyntaxException(String url, Exception cause) {
        super(RedisUrlSyntaxException.buildMessage(url), cause);
        this.url = url;
    }

    RedisUrlSyntaxException(String url) {
        super(RedisUrlSyntaxException.buildMessage(url));
        this.url = url;
    }

    String getUrl() {
        return this.url;
    }

    private static String buildMessage(String url) {
        return "Invalid Redis URL '" + url + "'";
    }
}

