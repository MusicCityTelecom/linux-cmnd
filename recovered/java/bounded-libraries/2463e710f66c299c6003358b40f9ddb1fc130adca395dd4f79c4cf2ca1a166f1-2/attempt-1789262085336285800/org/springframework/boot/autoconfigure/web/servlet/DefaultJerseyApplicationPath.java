/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.ws.rs.ApplicationPath
 *  org.glassfish.jersey.server.ResourceConfig
 *  org.springframework.core.annotation.MergedAnnotations
 *  org.springframework.core.annotation.MergedAnnotations$SearchStrategy
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.autoconfigure.web.servlet;

import javax.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.boot.autoconfigure.web.servlet.JerseyApplicationPath;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.util.StringUtils;

public class DefaultJerseyApplicationPath
implements JerseyApplicationPath {
    private final String applicationPath;
    private final ResourceConfig config;

    public DefaultJerseyApplicationPath(String applicationPath, ResourceConfig config) {
        this.applicationPath = applicationPath;
        this.config = config;
    }

    @Override
    public String getPath() {
        return this.resolveApplicationPath();
    }

    private String resolveApplicationPath() {
        if (StringUtils.hasLength((String)this.applicationPath)) {
            return this.applicationPath;
        }
        return MergedAnnotations.from(this.config.getApplication().getClass(), (MergedAnnotations.SearchStrategy)MergedAnnotations.SearchStrategy.TYPE_HIERARCHY).get(ApplicationPath.class).getValue("value", String.class).orElse("/*");
    }
}

