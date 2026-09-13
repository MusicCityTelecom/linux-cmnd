/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.actuate.endpoint.invoker.cache;

import java.util.function.Function;
import org.springframework.boot.actuate.endpoint.EndpointId;
import org.springframework.boot.actuate.endpoint.OperationType;
import org.springframework.boot.actuate.endpoint.invoke.OperationInvoker;
import org.springframework.boot.actuate.endpoint.invoke.OperationInvokerAdvisor;
import org.springframework.boot.actuate.endpoint.invoke.OperationParameters;
import org.springframework.boot.actuate.endpoint.invoker.cache.CachingOperationInvoker;

public class CachingOperationInvokerAdvisor
implements OperationInvokerAdvisor {
    private final Function<EndpointId, Long> endpointIdTimeToLive;

    public CachingOperationInvokerAdvisor(Function<EndpointId, Long> endpointIdTimeToLive) {
        this.endpointIdTimeToLive = endpointIdTimeToLive;
    }

    @Override
    public OperationInvoker apply(EndpointId endpointId, OperationType operationType, OperationParameters parameters, OperationInvoker invoker) {
        Long timeToLive;
        if (operationType == OperationType.READ && CachingOperationInvoker.isApplicable(parameters) && (timeToLive = this.endpointIdTimeToLive.apply(endpointId)) != null && timeToLive > 0L) {
            return new CachingOperationInvoker(invoker, timeToLive);
        }
        return invoker;
    }
}

