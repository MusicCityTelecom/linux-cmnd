/*
 * Decompiled with CFR 0.152.
 */
package javax.ws.rs.client;

import java.io.IOException;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientResponseContext;

public interface ClientResponseFilter {
    public void filter(ClientRequestContext var1, ClientResponseContext var2) throws IOException;
}

