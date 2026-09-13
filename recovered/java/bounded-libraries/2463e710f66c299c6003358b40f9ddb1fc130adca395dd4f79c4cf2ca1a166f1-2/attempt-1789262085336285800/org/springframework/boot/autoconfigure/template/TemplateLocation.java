/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.io.Resource
 *  org.springframework.core.io.support.ResourcePatternResolver
 *  org.springframework.util.Assert
 */
package org.springframework.boot.autoconfigure.template;

import java.io.IOException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.Assert;

public class TemplateLocation {
    private final String path;

    public TemplateLocation(String path) {
        Assert.notNull((Object)path, (String)"Path must not be null");
        this.path = path;
    }

    public boolean exists(ResourcePatternResolver resolver) {
        Assert.notNull((Object)resolver, (String)"Resolver must not be null");
        if (resolver.getResource(this.path).exists()) {
            return true;
        }
        try {
            return this.anyExists(resolver);
        }
        catch (IOException ex) {
            return false;
        }
    }

    private boolean anyExists(ResourcePatternResolver resolver) throws IOException {
        String searchPath = this.path;
        if (searchPath.startsWith("classpath:")) {
            searchPath = "classpath*:" + searchPath.substring("classpath:".length());
        }
        if (searchPath.startsWith("classpath*:")) {
            Resource[] resources;
            for (Resource resource : resources = resolver.getResources(searchPath)) {
                if (!resource.exists()) continue;
                return true;
            }
        }
        return false;
    }

    public String toString() {
        return this.path;
    }
}

