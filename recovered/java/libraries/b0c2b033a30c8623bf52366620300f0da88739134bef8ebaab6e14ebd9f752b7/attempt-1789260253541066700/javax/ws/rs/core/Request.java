/*
 * Decompiled with CFR 0.152.
 */
package javax.ws.rs.core;

import java.util.Date;
import java.util.List;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Variant;

public interface Request {
    public String getMethod();

    public Variant selectVariant(List<Variant> var1);

    public Response.ResponseBuilder evaluatePreconditions(EntityTag var1);

    public Response.ResponseBuilder evaluatePreconditions(Date var1);

    public Response.ResponseBuilder evaluatePreconditions(Date var1, EntityTag var2);

    public Response.ResponseBuilder evaluatePreconditions();
}

