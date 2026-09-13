/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.Assert
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.actuate.endpoint.web;

import java.util.List;
import org.springframework.boot.actuate.endpoint.EndpointId;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

@FunctionalInterface
public interface PathMapper {
    public String getRootPath(EndpointId var1);

    public static String getRootPath(List<PathMapper> pathMappers, EndpointId endpointId) {
        Assert.notNull((Object)endpointId, (String)"EndpointId must not be null");
        if (pathMappers != null) {
            for (PathMapper mapper : pathMappers) {
                String path = mapper.getRootPath(endpointId);
                if (!StringUtils.hasText((String)path)) continue;
                return path;
            }
        }
        return endpointId.toString();
    }
}

