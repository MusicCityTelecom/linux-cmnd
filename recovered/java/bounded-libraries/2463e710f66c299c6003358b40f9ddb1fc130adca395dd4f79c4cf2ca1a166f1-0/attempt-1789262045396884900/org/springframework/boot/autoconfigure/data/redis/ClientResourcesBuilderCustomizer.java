/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.lettuce.core.resource.ClientResources$Builder
 */
package org.springframework.boot.autoconfigure.data.redis;

import io.lettuce.core.resource.ClientResources;

public interface ClientResourcesBuilderCustomizer {
    public void customize(ClientResources.Builder var1);
}

