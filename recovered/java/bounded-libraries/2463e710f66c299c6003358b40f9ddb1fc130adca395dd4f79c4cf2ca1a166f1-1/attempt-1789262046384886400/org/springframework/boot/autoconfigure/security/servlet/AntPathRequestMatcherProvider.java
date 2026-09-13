/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.security.web.util.matcher.AntPathRequestMatcher
 *  org.springframework.security.web.util.matcher.RequestMatcher
 */
package org.springframework.boot.autoconfigure.security.servlet;

import java.util.function.Function;
import org.springframework.boot.autoconfigure.security.servlet.RequestMatcherProvider;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class AntPathRequestMatcherProvider
implements RequestMatcherProvider {
    private final Function<String, String> pathFactory;

    public AntPathRequestMatcherProvider(Function<String, String> pathFactory) {
        this.pathFactory = pathFactory;
    }

    @Override
    public RequestMatcher getRequestMatcher(String pattern) {
        return new AntPathRequestMatcher(this.pathFactory.apply(pattern));
    }
}

