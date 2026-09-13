/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.actuate.endpoint.web;

import org.springframework.util.StringUtils;

public class EndpointMapping {
    private final String path;

    public EndpointMapping(String path) {
        this.path = EndpointMapping.normalizePath(path);
    }

    public String getPath() {
        return this.path;
    }

    public String createSubPath(String path) {
        return this.path + EndpointMapping.normalizePath(path);
    }

    private static String normalizePath(String path) {
        if (!StringUtils.hasText((String)path)) {
            return path;
        }
        String normalizedPath = path;
        if (!normalizedPath.startsWith("/")) {
            normalizedPath = "/" + normalizedPath;
        }
        if (normalizedPath.endsWith("/")) {
            normalizedPath = normalizedPath.substring(0, normalizedPath.length() - 1);
        }
        return normalizedPath;
    }
}

