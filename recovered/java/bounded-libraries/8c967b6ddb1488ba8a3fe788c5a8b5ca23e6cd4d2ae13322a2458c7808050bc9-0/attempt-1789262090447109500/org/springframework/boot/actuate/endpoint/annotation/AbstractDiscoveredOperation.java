/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.style.ToStringCreator
 */
package org.springframework.boot.actuate.endpoint.annotation;

import org.springframework.boot.actuate.endpoint.InvocationContext;
import org.springframework.boot.actuate.endpoint.Operation;
import org.springframework.boot.actuate.endpoint.OperationType;
import org.springframework.boot.actuate.endpoint.annotation.DiscoveredOperationMethod;
import org.springframework.boot.actuate.endpoint.invoke.OperationInvoker;
import org.springframework.boot.actuate.endpoint.invoke.reflect.OperationMethod;
import org.springframework.core.style.ToStringCreator;

public abstract class AbstractDiscoveredOperation
implements Operation {
    private final OperationMethod operationMethod;
    private final OperationInvoker invoker;

    public AbstractDiscoveredOperation(DiscoveredOperationMethod operationMethod, OperationInvoker invoker) {
        this.operationMethod = operationMethod;
        this.invoker = invoker;
    }

    public OperationMethod getOperationMethod() {
        return this.operationMethod;
    }

    @Override
    public OperationType getType() {
        return this.operationMethod.getOperationType();
    }

    @Override
    public Object invoke(InvocationContext context) {
        return this.invoker.invoke(context);
    }

    public String toString() {
        ToStringCreator creator = new ToStringCreator((Object)this).append("operationMethod", (Object)this.operationMethod).append("invoker", (Object)this.invoker);
        this.appendFields(creator);
        return creator.toString();
    }

    protected void appendFields(ToStringCreator creator) {
    }
}

