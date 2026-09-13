/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.message.filtering.spi;

import org.glassfish.jersey.spi.Contract;

@Contract
public interface EntityInspector {
    public void inspect(Class<?> var1, boolean var2);
}

