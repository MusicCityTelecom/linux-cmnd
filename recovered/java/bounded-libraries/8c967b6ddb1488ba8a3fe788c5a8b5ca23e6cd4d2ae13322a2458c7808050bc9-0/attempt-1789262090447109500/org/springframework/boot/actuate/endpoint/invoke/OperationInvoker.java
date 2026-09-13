/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.actuate.endpoint.invoke;

import org.springframework.boot.actuate.endpoint.InvocationContext;
import org.springframework.boot.actuate.endpoint.invoke.MissingParametersException;

@FunctionalInterface
public interface OperationInvoker {
    public Object invoke(InvocationContext var1) throws MissingParametersException;
}

