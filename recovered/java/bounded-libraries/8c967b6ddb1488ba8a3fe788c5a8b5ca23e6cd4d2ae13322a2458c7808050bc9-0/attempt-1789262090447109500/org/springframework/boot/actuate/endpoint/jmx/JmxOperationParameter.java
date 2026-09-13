/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.actuate.endpoint.jmx;

public interface JmxOperationParameter {
    public String getName();

    public Class<?> getType();

    public String getDescription();
}

