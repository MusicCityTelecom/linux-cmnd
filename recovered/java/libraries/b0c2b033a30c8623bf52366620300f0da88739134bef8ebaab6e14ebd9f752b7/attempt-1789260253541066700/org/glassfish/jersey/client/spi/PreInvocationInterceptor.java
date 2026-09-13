/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.client.spi;

import javax.ws.rs.ConstrainedTo;
import javax.ws.rs.RuntimeType;
import javax.ws.rs.client.ClientRequestContext;
import org.glassfish.jersey.Beta;
import org.glassfish.jersey.spi.Contract;

@Contract
@ConstrainedTo(value=RuntimeType.CLIENT)
@Beta
public interface PreInvocationInterceptor {
    public void beforeRequest(ClientRequestContext var1);
}

