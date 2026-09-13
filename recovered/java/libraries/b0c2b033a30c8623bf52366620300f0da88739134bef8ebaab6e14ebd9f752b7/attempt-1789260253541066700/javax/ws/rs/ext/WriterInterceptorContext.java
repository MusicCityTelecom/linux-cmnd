/*
 * Decompiled with CFR 0.152.
 */
package javax.ws.rs.ext;

import java.io.IOException;
import java.io.OutputStream;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.InterceptorContext;

public interface WriterInterceptorContext
extends InterceptorContext {
    public void proceed() throws IOException, WebApplicationException;

    public Object getEntity();

    public void setEntity(Object var1);

    public OutputStream getOutputStream();

    public void setOutputStream(OutputStream var1);

    public MultivaluedMap<String, Object> getHeaders();
}

