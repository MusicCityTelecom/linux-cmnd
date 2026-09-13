/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.glassfish.jersey.server.ResourceConfig
 */
package org.springframework.boot.autoconfigure.jersey;

import org.glassfish.jersey.server.ResourceConfig;

@FunctionalInterface
public interface ResourceConfigCustomizer {
    public void customize(ResourceConfig var1);
}

