/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.security.web.util.matcher.RequestMatcher
 */
package org.springframework.boot.autoconfigure.security.servlet;

import org.springframework.security.web.util.matcher.RequestMatcher;

@FunctionalInterface
public interface RequestMatcherProvider {
    public RequestMatcher getRequestMatcher(String var1);
}

