/*
 * Decompiled with CFR 0.152.
 */
package net.sf.ehcache.management.resource.services.validator;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import org.terracotta.management.resource.exceptions.ResourceRuntimeException;
import org.terracotta.management.resource.services.Utils;
import org.terracotta.management.resource.services.validator.RequestValidator;

public abstract class AbstractEhcacheRequestValidator
implements RequestValidator {
    @Override
    public abstract void validateSafe(UriInfo var1);

    @Override
    public void validate(UriInfo info) {
        this.validateCacheRequestSegment(info.getPathSegments());
    }

    protected void validateCacheRequestSegment(List<PathSegment> pathSegments) {
        if (pathSegments.size() >= 3) {
            HashSet<String> cNames;
            String cacheNames = pathSegments.get(2).getMatrixParameters().getFirst("names");
            HashSet<String> hashSet = cNames = Utils.trimToNull(cacheNames) == null ? null : new HashSet<String>(Arrays.asList(cacheNames.split(",")));
            if (cNames == null) {
                throw new ResourceRuntimeException("No cache specified. Unsafe requests must specify a single cache name.", Response.Status.BAD_REQUEST.getStatusCode());
            }
            if (cNames.size() != 1) {
                throw new ResourceRuntimeException("Multiple caches specified. Unsafe requests must specify a single cache name.", Response.Status.BAD_REQUEST.getStatusCode());
            }
        }
        this.validateCacheManagerRequestSegment(pathSegments);
    }

    protected void validateCacheManagerRequestSegment(List<PathSegment> pathSegments) {
        if (pathSegments.size() >= 2) {
            HashSet<String> cmNames;
            String cacheManagerNames = this.getCacheManagerPathSegmentAccordingToVersion(pathSegments).getMatrixParameters().getFirst("names");
            HashSet<String> hashSet = cmNames = Utils.trimToNull(cacheManagerNames) == null ? null : new HashSet<String>(Arrays.asList(cacheManagerNames.split(",")));
            if (cmNames == null) {
                throw new ResourceRuntimeException("No cache manager specified. Unsafe requests must specify a single cache manager name.", Response.Status.BAD_REQUEST.getStatusCode());
            }
            if (cmNames.size() != 1) {
                throw new ResourceRuntimeException("Multiple cache managers specified. Unsafe requests must specify a single cache manager name.", Response.Status.BAD_REQUEST.getStatusCode());
            }
        }
        this.validateAgentSegment(pathSegments);
    }

    protected PathSegment getCacheManagerPathSegmentAccordingToVersion(List<PathSegment> pathSegments) {
        PathSegment cacheManagerPathSegment = null;
        cacheManagerPathSegment = "v2".equals(pathSegments.get(0).getPath()) ? pathSegments.get(2) : pathSegments.get(1);
        return cacheManagerPathSegment;
    }

    protected abstract void validateAgentSegment(List<PathSegment> var1);
}

