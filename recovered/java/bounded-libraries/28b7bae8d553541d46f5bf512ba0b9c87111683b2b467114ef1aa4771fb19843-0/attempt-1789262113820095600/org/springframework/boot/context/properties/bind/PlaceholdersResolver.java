/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.context.properties.bind;

@FunctionalInterface
public interface PlaceholdersResolver {
    public static final PlaceholdersResolver NONE = value -> value;

    public Object resolvePlaceholders(Object var1);
}

