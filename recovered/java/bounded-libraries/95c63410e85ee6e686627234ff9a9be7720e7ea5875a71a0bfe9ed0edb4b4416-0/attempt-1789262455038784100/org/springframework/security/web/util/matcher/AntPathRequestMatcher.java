/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  org.springframework.http.HttpMethod
 *  org.springframework.util.AntPathMatcher
 *  org.springframework.util.Assert
 *  org.springframework.util.StringUtils
 *  org.springframework.web.util.UrlPathHelper
 */
package org.springframework.security.web.util.matcher;

import java.util.Collections;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.security.web.util.matcher.RequestVariablesExtractor;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.util.UrlPathHelper;

public final class AntPathRequestMatcher
implements RequestMatcher,
RequestVariablesExtractor {
    private static final String MATCH_ALL = "/**";
    private final Matcher matcher;
    private final String pattern;
    private final HttpMethod httpMethod;
    private final boolean caseSensitive;
    private final UrlPathHelper urlPathHelper;

    public AntPathRequestMatcher(String pattern) {
        this(pattern, null);
    }

    public AntPathRequestMatcher(String pattern, String httpMethod) {
        this(pattern, httpMethod, true);
    }

    public AntPathRequestMatcher(String pattern, String httpMethod, boolean caseSensitive) {
        this(pattern, httpMethod, caseSensitive, null);
    }

    public AntPathRequestMatcher(String pattern, String httpMethod, boolean caseSensitive, UrlPathHelper urlPathHelper) {
        Assert.hasText((String)pattern, (String)"Pattern cannot be null or empty");
        this.caseSensitive = caseSensitive;
        if (pattern.equals(MATCH_ALL) || pattern.equals("**")) {
            pattern = MATCH_ALL;
            this.matcher = null;
        } else {
            this.matcher = pattern.endsWith(MATCH_ALL) && pattern.indexOf(63) == -1 && pattern.indexOf(123) == -1 && pattern.indexOf(125) == -1 && pattern.indexOf("*") == pattern.length() - 2 ? new SubpathMatcher(pattern.substring(0, pattern.length() - 3), caseSensitive) : new SpringAntMatcher(pattern, caseSensitive);
        }
        this.pattern = pattern;
        this.httpMethod = StringUtils.hasText((String)httpMethod) ? HttpMethod.valueOf((String)httpMethod) : null;
        this.urlPathHelper = urlPathHelper;
    }

    @Override
    public boolean matches(HttpServletRequest request) {
        if (this.httpMethod != null && StringUtils.hasText((String)request.getMethod()) && this.httpMethod != HttpMethod.resolve((String)request.getMethod())) {
            return false;
        }
        if (this.pattern.equals(MATCH_ALL)) {
            return true;
        }
        String url = this.getRequestPath(request);
        return this.matcher.matches(url);
    }

    @Override
    @Deprecated
    public Map<String, String> extractUriTemplateVariables(HttpServletRequest request) {
        return this.matcher(request).getVariables();
    }

    @Override
    public RequestMatcher.MatchResult matcher(HttpServletRequest request) {
        if (!this.matches(request)) {
            return RequestMatcher.MatchResult.notMatch();
        }
        if (this.matcher == null) {
            return RequestMatcher.MatchResult.match();
        }
        String url = this.getRequestPath(request);
        return RequestMatcher.MatchResult.match(this.matcher.extractUriTemplateVariables(url));
    }

    private String getRequestPath(HttpServletRequest request) {
        if (this.urlPathHelper != null) {
            return this.urlPathHelper.getPathWithinApplication(request);
        }
        String url = request.getServletPath();
        String pathInfo = request.getPathInfo();
        if (pathInfo != null) {
            url = StringUtils.hasLength((String)url) ? url + pathInfo : pathInfo;
        }
        return url;
    }

    public String getPattern() {
        return this.pattern;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof AntPathRequestMatcher)) {
            return false;
        }
        AntPathRequestMatcher other = (AntPathRequestMatcher)obj;
        return this.pattern.equals(other.pattern) && this.httpMethod == other.httpMethod && this.caseSensitive == other.caseSensitive;
    }

    public int hashCode() {
        int result = this.pattern != null ? this.pattern.hashCode() : 0;
        result = 31 * result + (this.httpMethod != null ? this.httpMethod.hashCode() : 0);
        result = 31 * result + (this.caseSensitive ? 1231 : 1237);
        return result;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Ant [pattern='").append(this.pattern).append("'");
        if (this.httpMethod != null) {
            sb.append(", ").append(this.httpMethod);
        }
        sb.append("]");
        return sb.toString();
    }

    private static final class SubpathMatcher
    implements Matcher {
        private final String subpath;
        private final int length;
        private final boolean caseSensitive;

        private SubpathMatcher(String subpath, boolean caseSensitive) {
            Assert.isTrue((!subpath.contains("*") ? 1 : 0) != 0, (String)"subpath cannot contain \"*\"");
            this.subpath = caseSensitive ? subpath : subpath.toLowerCase();
            this.length = subpath.length();
            this.caseSensitive = caseSensitive;
        }

        @Override
        public boolean matches(String path) {
            if (!this.caseSensitive) {
                path = path.toLowerCase();
            }
            return path.startsWith(this.subpath) && (path.length() == this.length || path.charAt(this.length) == '/');
        }

        @Override
        public Map<String, String> extractUriTemplateVariables(String path) {
            return Collections.emptyMap();
        }
    }

    private static final class SpringAntMatcher
    implements Matcher {
        private final AntPathMatcher antMatcher;
        private final String pattern;

        private SpringAntMatcher(String pattern, boolean caseSensitive) {
            this.pattern = pattern;
            this.antMatcher = SpringAntMatcher.createMatcher(caseSensitive);
        }

        @Override
        public boolean matches(String path) {
            return this.antMatcher.match(this.pattern, path);
        }

        @Override
        public Map<String, String> extractUriTemplateVariables(String path) {
            return this.antMatcher.extractUriTemplateVariables(this.pattern, path);
        }

        private static AntPathMatcher createMatcher(boolean caseSensitive) {
            AntPathMatcher matcher = new AntPathMatcher();
            matcher.setTrimTokens(false);
            matcher.setCaseSensitive(caseSensitive);
            return matcher;
        }
    }

    private static interface Matcher {
        public boolean matches(String var1);

        public Map<String, String> extractUriTemplateVariables(String var1);
    }
}

