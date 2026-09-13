/*
 * Decompiled with CFR 0.152.
 */
package javax.ws.rs.client;

import java.io.IOException;
import javax.ws.rs.client.ClientRequestContext;

public interface ClientRequestFilter {
    public void filter(ClientRequestContext var1) throws IOException;
}

