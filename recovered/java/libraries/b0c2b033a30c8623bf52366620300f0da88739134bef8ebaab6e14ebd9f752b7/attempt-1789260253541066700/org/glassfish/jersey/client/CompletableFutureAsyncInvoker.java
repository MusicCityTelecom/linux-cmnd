/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.client;

import java.util.concurrent.CompletableFuture;
import javax.ws.rs.client.AsyncInvoker;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.InvocationCallback;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.client.AbstractNonSyncInvoker;

abstract class CompletableFutureAsyncInvoker
extends AbstractNonSyncInvoker<CompletableFuture>
implements AsyncInvoker {
    CompletableFutureAsyncInvoker() {
    }

    @Override
    public <R> CompletableFuture<R> get(InvocationCallback<R> callback) {
        return this.method("GET", (InvocationCallback)callback);
    }

    @Override
    public <R> CompletableFuture<R> put(Entity<?> entity, InvocationCallback<R> callback) {
        return this.method("PUT", (Entity)entity, (InvocationCallback)callback);
    }

    @Override
    public <R> CompletableFuture<R> post(Entity<?> entity, InvocationCallback<R> callback) {
        return this.method("POST", (Entity)entity, (InvocationCallback)callback);
    }

    @Override
    public <R> CompletableFuture<R> delete(InvocationCallback<R> callback) {
        return this.method("DELETE", (InvocationCallback)callback);
    }

    public CompletableFuture<Response> head(InvocationCallback<Response> callback) {
        return this.method("HEAD", callback);
    }

    @Override
    public <R> CompletableFuture<R> options(InvocationCallback<R> callback) {
        return this.method("OPTIONS", (InvocationCallback)callback);
    }

    @Override
    public <R> CompletableFuture<R> trace(InvocationCallback<R> callback) {
        return this.method("TRACE", (InvocationCallback)callback);
    }

    @Override
    public abstract <R> CompletableFuture<R> method(String var1, InvocationCallback<R> var2);

    @Override
    public abstract <R> CompletableFuture<R> method(String var1, Entity<?> var2, InvocationCallback<R> var3);

    @Override
    public abstract <R> CompletableFuture method(String var1, Entity<?> var2, Class<R> var3);

    @Override
    public abstract <R> CompletableFuture method(String var1, Entity<?> var2, GenericType<R> var3);
}

