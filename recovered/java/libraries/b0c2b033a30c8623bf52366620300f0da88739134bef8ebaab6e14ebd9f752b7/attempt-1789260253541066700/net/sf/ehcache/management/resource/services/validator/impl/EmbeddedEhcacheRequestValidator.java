/*
 * Decompiled with CFR 0.152.
 */
package net.sf.ehcache.management.resource.services.validator.impl;

import java.util.List;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import net.sf.ehcache.management.resource.services.validator.AbstractEhcacheRequestValidator;
import org.terracotta.management.resource.exceptions.ResourceRuntimeException;
import org.terracotta.management.resource.services.Utils;

public final class EmbeddedEhcacheRequestValidator
extends AbstractEhcacheRequestValidator {
    @Override
    public void validateSafe(UriInfo info) {
        this.validateAgentSegment(info.getPathSegments());
    }

    @Override
    protected void validateAgentSegment(List<PathSegment> pathSegments) {
        String ids = pathSegments.get(0).getMatrixParameters().getFirst("ids");
        if (Utils.trimToNull(ids) != null && !"embedded".equals(ids)) {
            throw new ResourceRuntimeException(String.format("Agent ID must be '%s'.", "embedded"), Response.Status.BAD_REQUEST.getStatusCode());
        }
    }
}

