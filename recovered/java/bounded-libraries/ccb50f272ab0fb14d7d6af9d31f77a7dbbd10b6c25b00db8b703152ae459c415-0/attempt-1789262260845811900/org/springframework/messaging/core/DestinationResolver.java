/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.messaging.core;

import org.springframework.messaging.core.DestinationResolutionException;

@FunctionalInterface
public interface DestinationResolver<D> {
    public D resolveDestination(String var1) throws DestinationResolutionException;
}

