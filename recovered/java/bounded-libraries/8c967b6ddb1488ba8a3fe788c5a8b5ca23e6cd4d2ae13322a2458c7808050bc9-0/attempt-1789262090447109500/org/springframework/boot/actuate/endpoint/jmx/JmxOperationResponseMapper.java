/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.actuate.endpoint.jmx;

public interface JmxOperationResponseMapper {
    public Class<?> mapResponseType(Class<?> var1);

    public Object mapResponse(Object var1);
}

