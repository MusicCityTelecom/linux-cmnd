/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.core.log.LogMessage
 *  org.springframework.http.HttpMethod
 *  org.springframework.util.StringUtils
 */
package org.springframework.security.web.util.matcher;

import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.log.LogMessage;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.StringUtils;

public final class RegexRequestMatcher
implements RequestMatcher {
    private static final int DEFAULT = 32;
    private static final int CASE_INSENSITIVE = 34;
    private static final Log logger = LogFactory.getLog(RegexRequestMatcher.class);
    private final Pattern pattern;
    private final HttpMethod httpMethod;

    public RegexRequestMatcher(String pattern, String httpMethod) {
        this(pattern, httpMethod, false);
    }

    public RegexRequestMatcher(String pattern, String httpMethod, boolean caseInsensitive) {
        this.pattern = Pattern.compile(pattern, caseInsensitive ? 34 : 32);
        this.httpMethod = StringUtils.hasText((String)httpMethod) ? HttpMethod.valueOf((String)httpMethod) : null;
    }

    @Override
    public boolean matches(HttpServletRequest request) {
        if (this.httpMethod != null && request.getMethod() != null && this.httpMethod != HttpMethod.resolve((String)request.getMethod())) {
            return false;
        }
        String url = request.getServletPath();
        String pathInfo = request.getPathInfo();
        String query = request.getQueryString();
        if (pathInfo != null || query != null) {
            StringBuilder sb = new StringBuilder(url);
            if (pathInfo != null) {
                sb.append(pathInfo);
            }
            if (query != null) {
                sb.append('?').append(query);
            }
            url = sb.toString();
        }
        logger.debug((Object)LogMessage.format((String)"Checking match of request : '%s'; against '%s'", (Object)url, (Object)this.pattern));
        return this.pattern.matcher(url).matches();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Regex [pattern='").append(this.pattern).append("'");
        if (this.httpMethod != null) {
            sb.append(", ").append(this.httpMethod);
        }
        sb.append("]");
        return sb.toString();
    }
}

