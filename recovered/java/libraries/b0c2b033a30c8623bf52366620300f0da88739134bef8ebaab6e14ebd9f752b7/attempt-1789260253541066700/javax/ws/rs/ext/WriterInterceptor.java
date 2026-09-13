/*
 * Decompiled with CFR 0.152.
 */
package javax.ws.rs.ext;

import java.io.IOException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.WriterInterceptorContext;

public interface WriterInterceptor {
    public void aroundWriteTo(WriterInterceptorContext var1) throws IOException, WebApplicationException;
}

