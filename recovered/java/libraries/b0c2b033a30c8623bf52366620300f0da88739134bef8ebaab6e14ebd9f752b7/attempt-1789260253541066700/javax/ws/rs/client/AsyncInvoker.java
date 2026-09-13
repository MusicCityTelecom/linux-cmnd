/*
 * Decompiled with CFR 0.152.
 */
package javax.ws.rs.client;

import java.util.concurrent.Future;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.InvocationCallback;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

public interface AsyncInvoker {
    public Future<Response> get();

    public <T> Future<T> get(Class<T> var1);

    public <T> Future<T> get(GenericType<T> var1);

    public <T> Future<T> get(InvocationCallback<T> var1);

    public Future<Response> put(Entity<?> var1);

    public <T> Future<T> put(Entity<?> var1, Class<T> var2);

    public <T> Future<T> put(Entity<?> var1, GenericType<T> var2);

    public <T> Future<T> put(Entity<?> var1, InvocationCallback<T> var2);

    public Future<Response> post(Entity<?> var1);

    public <T> Future<T> post(Entity<?> var1, Class<T> var2);

    public <T> Future<T> post(Entity<?> var1, GenericType<T> var2);

    public <T> Future<T> post(Entity<?> var1, InvocationCallback<T> var2);

    public Future<Response> delete();

    public <T> Future<T> delete(Class<T> var1);

    public <T> Future<T> delete(GenericType<T> var1);

    public <T> Future<T> delete(InvocationCallback<T> var1);

    public Future<Response> head();

    public Future<Response> head(InvocationCallback<Response> var1);

    public Future<Response> options();

    public <T> Future<T> options(Class<T> var1);

    public <T> Future<T> options(GenericType<T> var1);

    public <T> Future<T> options(InvocationCallback<T> var1);

    public Future<Response> trace();

    public <T> Future<T> trace(Class<T> var1);

    public <T> Future<T> trace(GenericType<T> var1);

    public <T> Future<T> trace(InvocationCallback<T> var1);

    public Future<Response> method(String var1);

    public <T> Future<T> method(String var1, Class<T> var2);

    public <T> Future<T> method(String var1, GenericType<T> var2);

    public <T> Future<T> method(String var1, InvocationCallback<T> var2);

    public Future<Response> method(String var1, Entity<?> var2);

    public <T> Future<T> method(String var1, Entity<?> var2, Class<T> var3);

    public <T> Future<T> method(String var1, Entity<?> var2, GenericType<T> var3);

    public <T> Future<T> method(String var1, Entity<?> var2, InvocationCallback<T> var3);
}

