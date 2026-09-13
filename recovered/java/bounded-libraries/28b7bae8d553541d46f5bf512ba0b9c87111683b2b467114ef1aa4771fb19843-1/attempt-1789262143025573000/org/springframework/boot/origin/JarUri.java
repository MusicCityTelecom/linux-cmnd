/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.origin;

import java.net.URI;

final class JarUri {
    private static final String JAR_SCHEME = "jar:";
    private static final String JAR_EXTENSION = ".jar";
    private final String uri;
    private final String description;

    private JarUri(String uri) {
        this.uri = uri;
        this.description = this.extractDescription(uri);
    }

    private String extractDescription(String uri) {
        uri = uri.substring(JAR_SCHEME.length());
        int firstDotJar = uri.indexOf(JAR_EXTENSION);
        String firstJar = this.getFilename(uri.substring(0, firstDotJar + JAR_EXTENSION.length()));
        int lastDotJar = (uri = uri.substring(firstDotJar + JAR_EXTENSION.length())).lastIndexOf(JAR_EXTENSION);
        if (lastDotJar == -1) {
            return firstJar;
        }
        return firstJar + uri.substring(0, lastDotJar + JAR_EXTENSION.length());
    }

    private String getFilename(String string) {
        int lastSlash = string.lastIndexOf(47);
        return lastSlash == -1 ? string : string.substring(lastSlash + 1);
    }

    String getDescription() {
        return this.description;
    }

    String getDescription(String existing) {
        return existing + " from " + this.description;
    }

    public String toString() {
        return this.uri;
    }

    static JarUri from(URI uri) {
        return JarUri.from(uri.toString());
    }

    static JarUri from(String uri) {
        if (uri.startsWith(JAR_SCHEME) && uri.contains(JAR_EXTENSION)) {
            return new JarUri(uri);
        }
        return null;
    }
}

