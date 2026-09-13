/*
 * Decompiled with CFR 0.152.
 */
package javax.ws.rs.container;

import java.io.IOException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;

public interface ContainerResponseFilter {
    public void filter(ContainerRequestContext var1, ContainerResponseContext var2) throws IOException;
}

