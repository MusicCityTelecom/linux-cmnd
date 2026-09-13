/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.actuate.endpoint;

import org.springframework.boot.actuate.endpoint.SanitizableData;

@FunctionalInterface
public interface SanitizingFunction {
    public SanitizableData apply(SanitizableData var1);
}

