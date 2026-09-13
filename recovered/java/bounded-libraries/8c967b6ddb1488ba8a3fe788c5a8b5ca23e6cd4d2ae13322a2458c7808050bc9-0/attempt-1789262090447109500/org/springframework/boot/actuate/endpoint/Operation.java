/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.actuate.endpoint;

import org.springframework.boot.actuate.endpoint.InvocationContext;
import org.springframework.boot.actuate.endpoint.OperationType;

public interface Operation {
    public OperationType getType();

    public Object invoke(InvocationContext var1);
}

