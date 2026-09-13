/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.client.inject;

public interface ParameterUpdater<T, R> {
    public String getName();

    public String getDefaultValueString();

    public R update(T var1);
}

