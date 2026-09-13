/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.autoconfigure.web.servlet;

@FunctionalInterface
public interface DispatcherServletPath {
    public String getPath();

    default public String getRelativePath(String path) {
        String prefix = this.getPrefix();
        if (!path.startsWith("/")) {
            path = "/" + path;
        }
        return prefix + path;
    }

    default public String getPrefix() {
        String result = this.getPath();
        int index = result.indexOf(42);
        if (index != -1) {
            result = result.substring(0, index);
        }
        if (result.endsWith("/")) {
            result = result.substring(0, result.length() - 1);
        }
        return result;
    }

    default public String getServletUrlMapping() {
        if (this.getPath().equals("") || this.getPath().equals("/")) {
            return "/";
        }
        if (this.getPath().contains("*")) {
            return this.getPath();
        }
        if (this.getPath().endsWith("/")) {
            return this.getPath() + "*";
        }
        return this.getPath() + "/*";
    }
}

