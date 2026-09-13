/*
 * Decompiled with CFR 0.152.
 */
package javax.ws.rs.client;

import java.util.concurrent.CompletionStage;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.RxInvoker;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

public interface CompletionStageRxInvoker
extends RxInvoker<CompletionStage> {
    @Override
    public CompletionStage<Response> get();

    @Override
    public <T> CompletionStage<T> get(Class<T> var1);

    @Override
    public <T> CompletionStage<T> get(GenericType<T> var1);

    @Override
    public CompletionStage<Response> put(Entity<?> var1);

    @Override
    public <T> CompletionStage<T> put(Entity<?> var1, Class<T> var2);

    @Override
    public <T> CompletionStage<T> put(Entity<?> var1, GenericType<T> var2);

    @Override
    public CompletionStage<Response> post(Entity<?> var1);

    @Override
    public <T> CompletionStage<T> post(Entity<?> var1, Class<T> var2);

    @Override
    public <T> CompletionStage<T> post(Entity<?> var1, GenericType<T> var2);

    @Override
    public CompletionStage<Response> delete();

    @Override
    public <T> CompletionStage<T> delete(Class<T> var1);

    @Override
    public <T> CompletionStage<T> delete(GenericType<T> var1);

    @Override
    public CompletionStage<Response> head();

    @Override
    public CompletionStage<Response> options();

    @Override
    public <T> CompletionStage<T> options(Class<T> var1);

    @Override
    public <T> CompletionStage<T> options(GenericType<T> var1);

    @Override
    public CompletionStage<Response> trace();

    @Override
    public <T> CompletionStage<T> trace(Class<T> var1);

    @Override
    public <T> CompletionStage<T> trace(GenericType<T> var1);

    @Override
    public CompletionStage<Response> method(String var1);

    @Override
    public <T> CompletionStage<T> method(String var1, Class<T> var2);

    @Override
    public <T> CompletionStage<T> method(String var1, GenericType<T> var2);

    @Override
    public CompletionStage<Response> method(String var1, Entity<?> var2);

    @Override
    public <T> CompletionStage<T> method(String var1, Entity<?> var2, Class<T> var3);

    @Override
    public <T> CompletionStage<T> method(String var1, Entity<?> var2, GenericType<T> var3);
}

