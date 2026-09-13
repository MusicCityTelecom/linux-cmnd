/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 */
package org.springframework.security.web.access.intercept;

import java.util.Collections;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public final class RequestAuthorizationContext {
    private final HttpServletRequest request;
    private final Map<String, String> variables;

    public RequestAuthorizationContext(HttpServletRequest request) {
        this(request, Collections.emptyMap());
    }

    public RequestAuthorizationContext(HttpServletRequest request, Map<String, String> variables) {
        this.request = request;
        this.variables = variables;
    }

    public HttpServletRequest getRequest() {
        return this.request;
    }

    public Map<String, String> getVariables() {
        return this.variables;
    }
}

