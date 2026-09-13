/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 */
package org.springframework.security.web.util.matcher;

import javax.servlet.http.HttpServletRequest;
import org.springframework.security.web.util.matcher.RequestMatcher;

public final class AnyRequestMatcher
implements RequestMatcher {
    public static final RequestMatcher INSTANCE = new AnyRequestMatcher();

    private AnyRequestMatcher() {
    }

    @Override
    public boolean matches(HttpServletRequest request) {
        return true;
    }

    public boolean equals(Object obj) {
        return obj instanceof AnyRequestMatcher || obj instanceof AnyRequestMatcher;
    }

    public int hashCode() {
        return 1;
    }

    public String toString() {
        return "any request";
    }
}

