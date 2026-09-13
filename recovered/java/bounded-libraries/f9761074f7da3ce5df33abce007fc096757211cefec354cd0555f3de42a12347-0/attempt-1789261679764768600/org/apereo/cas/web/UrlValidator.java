/*
 * Decompiled with CFR 0.152.
 */
package org.apereo.cas.web;

public interface UrlValidator {
    public static final String BEAN_NAME = "urlValidator";

    public boolean isValid(String var1);

    public boolean isValidDomain(String var1);
}

