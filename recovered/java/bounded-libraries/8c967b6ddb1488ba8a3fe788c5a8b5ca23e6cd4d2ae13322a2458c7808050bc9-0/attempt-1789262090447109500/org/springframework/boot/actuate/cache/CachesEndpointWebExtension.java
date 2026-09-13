/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.lang.Nullable
 */
package org.springframework.boot.actuate.cache;

import org.springframework.boot.actuate.cache.CachesEndpoint;
import org.springframework.boot.actuate.cache.NonUniqueCacheException;
import org.springframework.boot.actuate.endpoint.annotation.DeleteOperation;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.boot.actuate.endpoint.web.WebEndpointResponse;
import org.springframework.boot.actuate.endpoint.web.annotation.EndpointWebExtension;
import org.springframework.lang.Nullable;

@EndpointWebExtension(endpoint=CachesEndpoint.class)
public class CachesEndpointWebExtension {
    private final CachesEndpoint delegate;

    public CachesEndpointWebExtension(CachesEndpoint delegate) {
        this.delegate = delegate;
    }

    @ReadOperation
    public WebEndpointResponse<CachesEndpoint.CacheEntry> cache(@Selector String cache, @Nullable String cacheManager) {
        try {
            CachesEndpoint.CacheEntry entry = this.delegate.cache(cache, cacheManager);
            int status = entry != null ? 200 : 404;
            return new WebEndpointResponse<CachesEndpoint.CacheEntry>(entry, status);
        }
        catch (NonUniqueCacheException ex) {
            return new WebEndpointResponse<CachesEndpoint.CacheEntry>(400);
        }
    }

    @DeleteOperation
    public WebEndpointResponse<Void> clearCache(@Selector String cache, @Nullable String cacheManager) {
        try {
            boolean cleared = this.delegate.clearCache(cache, cacheManager);
            int status = cleared ? 204 : 404;
            return new WebEndpointResponse<Void>(status);
        }
        catch (NonUniqueCacheException ex) {
            return new WebEndpointResponse<Void>(400);
        }
    }
}

