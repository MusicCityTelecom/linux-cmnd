/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  org.springframework.http.HttpMethod
 *  org.springframework.util.AntPathMatcher
 *  org.springframework.util.PathMatcher
 *  org.springframework.web.servlet.handler.HandlerMappingIntrospector
 *  org.springframework.web.servlet.handler.MatchableHandlerMapping
 *  org.springframework.web.servlet.handler.RequestMatchResult
 *  org.springframework.web.util.UrlPathHelper
 */
package org.springframework.security.web.servlet.util.matcher;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.security.web.util.matcher.RequestVariablesExtractor;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;
import org.springframework.web.servlet.handler.MatchableHandlerMapping;
import org.springframework.web.servlet.handler.RequestMatchResult;
import org.springframework.web.util.UrlPathHelper;

public class MvcRequestMatcher
implements RequestMatcher,
RequestVariablesExtractor {
    private final DefaultMatcher defaultMatcher = new DefaultMatcher();
    private final HandlerMappingIntrospector introspector;
    private final String pattern;
    private HttpMethod method;
    private String servletPath;

    public MvcRequestMatcher(HandlerMappingIntrospector introspector, String pattern) {
        this.introspector = introspector;
        this.pattern = pattern;
    }

    @Override
    public boolean matches(HttpServletRequest request) {
        if (this.notMatchMethodOrServletPath(request)) {
            return false;
        }
        MatchableHandlerMapping mapping = this.getMapping(request);
        if (mapping == null) {
            return this.defaultMatcher.matches(request);
        }
        RequestMatchResult matchResult = mapping.match(request, this.pattern);
        return matchResult != null;
    }

    @Override
    @Deprecated
    public Map<String, String> extractUriTemplateVariables(HttpServletRequest request) {
        return this.matcher(request).getVariables();
    }

    @Override
    public RequestMatcher.MatchResult matcher(HttpServletRequest request) {
        if (this.notMatchMethodOrServletPath(request)) {
            return RequestMatcher.MatchResult.notMatch();
        }
        MatchableHandlerMapping mapping = this.getMapping(request);
        if (mapping == null) {
            return this.defaultMatcher.matcher(request);
        }
        RequestMatchResult result = mapping.match(request, this.pattern);
        return result != null ? RequestMatcher.MatchResult.match(result.extractUriTemplateVariables()) : RequestMatcher.MatchResult.notMatch();
    }

    private boolean notMatchMethodOrServletPath(HttpServletRequest request) {
        return this.method != null && !this.method.name().equals(request.getMethod()) || this.servletPath != null && !this.servletPath.equals(request.getServletPath());
    }

    private MatchableHandlerMapping getMapping(HttpServletRequest request) {
        try {
            return this.introspector.getMatchableHandlerMapping(request);
        }
        catch (Throwable ex) {
            return null;
        }
    }

    public void setMethod(HttpMethod method) {
        this.method = method;
    }

    public void setServletPath(String servletPath) {
        this.servletPath = servletPath;
    }

    protected final String getServletPath() {
        return this.servletPath;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Mvc [pattern='").append(this.pattern).append("'");
        if (this.servletPath != null) {
            sb.append(", servletPath='").append(this.servletPath).append("'");
        }
        if (this.method != null) {
            sb.append(", ").append(this.method);
        }
        sb.append("]");
        return sb.toString();
    }

    private class DefaultMatcher
    implements RequestMatcher {
        private final UrlPathHelper pathHelper = new UrlPathHelper();
        private final PathMatcher pathMatcher = new AntPathMatcher();

        private DefaultMatcher() {
        }

        @Override
        public boolean matches(HttpServletRequest request) {
            String lookupPath = this.pathHelper.getLookupPathForRequest(request);
            return this.matches(lookupPath);
        }

        private boolean matches(String lookupPath) {
            return this.pathMatcher.match(MvcRequestMatcher.this.pattern, lookupPath);
        }

        @Override
        public RequestMatcher.MatchResult matcher(HttpServletRequest request) {
            String lookupPath = this.pathHelper.getLookupPathForRequest(request);
            if (this.matches(lookupPath)) {
                Map variables = this.pathMatcher.extractUriTemplateVariables(MvcRequestMatcher.this.pattern, lookupPath);
                return RequestMatcher.MatchResult.match(variables);
            }
            return RequestMatcher.MatchResult.notMatch();
        }
    }
}

