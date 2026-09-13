/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.InitializingBean
 *  org.springframework.lang.Nullable
 *  org.springframework.util.Assert
 */
package org.springframework.messaging.core;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.lang.Nullable;
import org.springframework.messaging.core.DestinationResolutionException;
import org.springframework.messaging.core.DestinationResolver;
import org.springframework.util.Assert;

public class CachingDestinationResolverProxy<D>
implements DestinationResolver<D>,
InitializingBean {
    private final Map<String, D> resolvedDestinationCache = new ConcurrentHashMap<String, D>();
    @Nullable
    private DestinationResolver<D> targetDestinationResolver;

    public CachingDestinationResolverProxy() {
    }

    public CachingDestinationResolverProxy(DestinationResolver<D> targetDestinationResolver) {
        Assert.notNull(targetDestinationResolver, (String)"Target DestinationResolver must not be null");
        this.targetDestinationResolver = targetDestinationResolver;
    }

    public void setTargetDestinationResolver(DestinationResolver<D> targetDestinationResolver) {
        this.targetDestinationResolver = targetDestinationResolver;
    }

    public void afterPropertiesSet() {
        if (this.targetDestinationResolver == null) {
            throw new IllegalArgumentException("Property 'targetDestinationResolver' is required");
        }
    }

    @Override
    public D resolveDestination(String name) throws DestinationResolutionException {
        D destination = this.resolvedDestinationCache.get(name);
        if (destination == null) {
            Assert.state((this.targetDestinationResolver != null ? 1 : 0) != 0, (String)"No target DestinationResolver set");
            destination = this.targetDestinationResolver.resolveDestination(name);
            this.resolvedDestinationCache.put(name, destination);
        }
        return destination;
    }
}

