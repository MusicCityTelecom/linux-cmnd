/*
 * Decompiled with CFR 0.152.
 */
package javax.ws.rs.container;

import java.io.IOException;
import javax.ws.rs.container.ContainerRequestContext;

public interface ContainerRequestFilter {
    public void filter(ContainerRequestContext var1) throws IOException;
}

