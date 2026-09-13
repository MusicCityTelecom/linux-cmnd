/*
 * Decompiled with CFR 0.152.
 */
package javax.ws.rs.ext;

import javax.ws.rs.core.Response;

public interface ExceptionMapper<E extends Throwable> {
    public Response toResponse(E var1);
}

