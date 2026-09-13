/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.client.spi;

import java.util.Deque;
import java.util.Optional;
import javax.ws.rs.ConstrainedTo;
import javax.ws.rs.RuntimeType;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientResponseContext;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.Beta;
import org.glassfish.jersey.spi.Contract;

@Contract
@ConstrainedTo(value=RuntimeType.CLIENT)
@Beta
public interface PostInvocationInterceptor {
    public void afterRequest(ClientRequestContext var1, ClientResponseContext var2);

    public void onException(ClientRequestContext var1, ExceptionContext var2);

    public static interface ExceptionContext {
        public Optional<ClientResponseContext> getResponseContext();

        public Deque<Throwable> getThrowables();

        public void resolve(Response var1);
    }
}

