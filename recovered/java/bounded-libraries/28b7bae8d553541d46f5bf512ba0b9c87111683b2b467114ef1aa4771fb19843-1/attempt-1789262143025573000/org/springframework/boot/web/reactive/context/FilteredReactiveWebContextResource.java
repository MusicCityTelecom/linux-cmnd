/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.io.AbstractResource
 *  org.springframework.core.io.Resource
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.web.reactive.context;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import org.springframework.core.io.AbstractResource;
import org.springframework.core.io.Resource;
import org.springframework.util.StringUtils;

class FilteredReactiveWebContextResource
extends AbstractResource {
    private final String path;

    FilteredReactiveWebContextResource(String path) {
        this.path = path;
    }

    public boolean exists() {
        return false;
    }

    public Resource createRelative(String relativePath) throws IOException {
        String pathToUse = StringUtils.applyRelativePath((String)this.path, (String)relativePath);
        return new FilteredReactiveWebContextResource(pathToUse);
    }

    public String getDescription() {
        return "ReactiveWebContext resource [" + this.path + "]";
    }

    public InputStream getInputStream() throws IOException {
        throw new FileNotFoundException(this.getDescription() + " cannot be opened because it does not exist");
    }
}

