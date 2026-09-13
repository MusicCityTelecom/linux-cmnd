/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.internal.spi;

import javax.ws.rs.core.FeatureContext;

public interface AutoDiscoverable {
    public static final int DEFAULT_PRIORITY = 2000;

    public void configure(FeatureContext var1);
}

