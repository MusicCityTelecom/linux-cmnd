/*
 * Decompiled with CFR 0.152.
 */
package javax.ws.rs.client;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

public interface SyncInvoker {
    public Response get();

    public <T> T get(Class<T> var1);

    public <T> T get(GenericType<T> var1);

    public Response put(Entity<?> var1);

    public <T> T put(Entity<?> var1, Class<T> var2);

    public <T> T put(Entity<?> var1, GenericType<T> var2);

    public Response post(Entity<?> var1);

    public <T> T post(Entity<?> var1, Class<T> var2);

    public <T> T post(Entity<?> var1, GenericType<T> var2);

    public Response delete();

    public <T> T delete(Class<T> var1);

    public <T> T delete(GenericType<T> var1);

    public Response head();

    public Response options();

    public <T> T options(Class<T> var1);

    public <T> T options(GenericType<T> var1);

    public Response trace();

    public <T> T trace(Class<T> var1);

    public <T> T trace(GenericType<T> var1);

    public Response method(String var1);

    public <T> T method(String var1, Class<T> var2);

    public <T> T method(String var1, GenericType<T> var2);

    public Response method(String var1, Entity<?> var2);

    public <T> T method(String var1, Entity<?> var2, Class<T> var3);

    public <T> T method(String var1, Entity<?> var2, GenericType<T> var3);
}

