/*
 * Decompiled with CFR 0.152.
 */
package javax.ws.rs.ext;

import java.io.IOException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.ReaderInterceptorContext;

public interface ReaderInterceptor {
    public Object aroundReadFrom(ReaderInterceptorContext var1) throws IOException, WebApplicationException;
}

