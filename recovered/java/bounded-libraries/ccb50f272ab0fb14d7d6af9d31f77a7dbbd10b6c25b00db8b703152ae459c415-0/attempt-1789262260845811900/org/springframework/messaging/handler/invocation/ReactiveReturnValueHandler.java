/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Publisher
 *  org.springframework.core.MethodParameter
 *  org.springframework.core.ReactiveAdapter
 *  org.springframework.core.ReactiveAdapterRegistry
 *  org.springframework.util.concurrent.ListenableFuture
 *  org.springframework.util.concurrent.MonoToListenableFutureAdapter
 *  reactor.core.publisher.Mono
 */
package org.springframework.messaging.handler.invocation;

import org.reactivestreams.Publisher;
import org.springframework.core.MethodParameter;
import org.springframework.core.ReactiveAdapter;
import org.springframework.core.ReactiveAdapterRegistry;
import org.springframework.messaging.handler.invocation.AbstractAsyncReturnValueHandler;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.MonoToListenableFutureAdapter;
import reactor.core.publisher.Mono;

public class ReactiveReturnValueHandler
extends AbstractAsyncReturnValueHandler {
    private final ReactiveAdapterRegistry adapterRegistry;

    public ReactiveReturnValueHandler() {
        this(ReactiveAdapterRegistry.getSharedInstance());
    }

    public ReactiveReturnValueHandler(ReactiveAdapterRegistry adapterRegistry) {
        this.adapterRegistry = adapterRegistry;
    }

    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return this.adapterRegistry.getAdapter(returnType.getParameterType()) != null;
    }

    @Override
    public boolean isAsyncReturnValue(Object returnValue, MethodParameter returnType) {
        ReactiveAdapter adapter = this.adapterRegistry.getAdapter(returnType.getParameterType(), returnValue);
        return adapter != null && !adapter.isMultiValue() && !adapter.isNoValue();
    }

    @Override
    public ListenableFuture<?> toListenableFuture(Object returnValue, MethodParameter returnType) {
        ReactiveAdapter adapter = this.adapterRegistry.getAdapter(returnType.getParameterType(), returnValue);
        if (adapter != null) {
            return new MonoToListenableFutureAdapter(Mono.from((Publisher)adapter.toPublisher(returnValue)));
        }
        return null;
    }
}

