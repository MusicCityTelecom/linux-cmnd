/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.actuate.endpoint.invoke;

public interface OperationParameter {
    public String getName();

    public Class<?> getType();

    public boolean isMandatory();
}

