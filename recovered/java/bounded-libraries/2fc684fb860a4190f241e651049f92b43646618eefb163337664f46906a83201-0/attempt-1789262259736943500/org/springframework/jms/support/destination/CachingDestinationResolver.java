/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.jms.support.destination;

import org.springframework.jms.support.destination.DestinationResolver;

public interface CachingDestinationResolver
extends DestinationResolver {
    public void removeFromCache(String var1);

    public void clearCache();
}

