/*
 * Decompiled with CFR 0.152.
 */
package javax.ws.rs.client;

import java.util.Locale;
import java.util.concurrent.Future;
import javax.ws.rs.client.AsyncInvoker;
import javax.ws.rs.client.CompletionStageRxInvoker;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.InvocationCallback;
import javax.ws.rs.client.RxInvoker;
import javax.ws.rs.client.SyncInvoker;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

public interface Invocation {
    public Invocation property(String var1, Object var2);

    public Response invoke();

    public <T> T invoke(Class<T> var1);

    public <T> T invoke(GenericType<T> var1);

    public Future<Response> submit();

    public <T> Future<T> submit(Class<T> var1);

    public <T> Future<T> submit(GenericType<T> var1);

    public <T> Future<T> submit(InvocationCallback<T> var1);

    public static interface Builder
    extends SyncInvoker {
        public Invocation build(String var1);

        public Invocation build(String var1, Entity<?> var2);

        public Invocation buildGet();

        public Invocation buildDelete();

        public Invocation buildPost(Entity<?> var1);

        public Invocation buildPut(Entity<?> var1);

        public AsyncInvoker async();

        public Builder accept(String ... var1);

        public Builder accept(MediaType ... var1);

        public Builder acceptLanguage(Locale ... var1);

        public Builder acceptLanguage(String ... var1);

        public Builder acceptEncoding(String ... var1);

        public Builder cookie(Cookie var1);

        public Builder cookie(String var1, String var2);

        public Builder cacheControl(CacheControl var1);

        public Builder header(String var1, Object var2);

        public Builder headers(MultivaluedMap<String, Object> var1);

        public Builder property(String var1, Object var2);

        public CompletionStageRxInvoker rx();

        public <T extends RxInvoker> T rx(Class<T> var1);
    }
}

