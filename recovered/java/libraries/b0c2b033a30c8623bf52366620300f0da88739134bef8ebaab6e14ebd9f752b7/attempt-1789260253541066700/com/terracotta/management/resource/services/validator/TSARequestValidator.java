/*
 * Decompiled with CFR 0.152.
 */
package com.terracotta.management.resource.services.validator;

import java.util.List;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import org.terracotta.management.resource.exceptions.ResourceRuntimeException;
import org.terracotta.management.resource.services.Utils;
import org.terracotta.management.resource.services.validator.RequestValidator;

public class TSARequestValidator
implements RequestValidator {
    @Override
    public void validateSafe(UriInfo info) {
        this.validateAgentSegment(info.getPathSegments());
    }

    @Override
    public void validate(UriInfo info) {
        this.validateAgentSegment(info.getPathSegments());
    }

    private void validateAgentSegment(List<PathSegment> pathSegments) {
        String ids = pathSegments.get(0).getMatrixParameters().getFirst("ids");
        if (Utils.trimToNull(ids) != null && !"embedded".equals(ids)) {
            throw new ResourceRuntimeException(String.format("Agent ID must be '%s'.", "embedded"), Response.Status.BAD_REQUEST.getStatusCode());
        }
    }
}

