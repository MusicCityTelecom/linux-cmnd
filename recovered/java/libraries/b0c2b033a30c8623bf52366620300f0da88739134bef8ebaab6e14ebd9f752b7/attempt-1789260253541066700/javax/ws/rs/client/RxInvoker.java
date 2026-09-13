/*
 * Decompiled with CFR 0.152.
 */
package javax.ws.rs.client;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;

public interface RxInvoker<T> {
    public T get();

    public <R> T get(Class<R> var1);

    public <R> T get(GenericType<R> var1);

    public T put(Entity<?> var1);

    public <R> T put(Entity<?> var1, Class<R> var2);

    public <R> T put(Entity<?> var1, GenericType<R> var2);

    public T post(Entity<?> var1);

    public <R> T post(Entity<?> var1, Class<R> var2);

    public <R> T post(Entity<?> var1, GenericType<R> var2);

    public T delete();

    public <R> T delete(Class<R> var1);

    public <R> T delete(GenericType<R> var1);

    public T head();

    public T options();

    public <R> T options(Class<R> var1);

    public <R> T options(GenericType<R> var1);

    public T trace();

    public <R> T trace(Class<R> var1);

    public <R> T trace(GenericType<R> var1);

    public T method(String var1);

    public <R> T method(String var1, Class<R> var2);

    public <R> T method(String var1, GenericType<R> var2);

    public T method(String var1, Entity<?> var2);

    public <R> T method(String var1, Entity<?> var2, Class<R> var3);

    public <R> T method(String var1, Entity<?> var2, GenericType<R> var3);
}

