/*
 * Decompiled with CFR 0.152.
 */
package org.apereo.cas.util.text;

@FunctionalInterface
public interface MessageSanitizer {
    public static final String BEAN_NAME = "messageSanitizer";

    public String sanitize(String var1);
}

