/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.actuate.endpoint.invoke;

import org.springframework.boot.actuate.endpoint.EndpointId;
import org.springframework.boot.actuate.endpoint.OperationType;
import org.springframework.boot.actuate.endpoint.invoke.OperationInvoker;
import org.springframework.boot.actuate.endpoint.invoke.OperationParameters;

@FunctionalInterface
public interface OperationInvokerAdvisor {
    public OperationInvoker apply(EndpointId var1, OperationType var2, OperationParameters var3, OperationInvoker var4);
}

