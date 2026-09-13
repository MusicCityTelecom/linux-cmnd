/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.micrometer.core.instrument.Tag
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.http.HttpStatus
 *  org.springframework.util.StringUtils
 *  org.springframework.web.servlet.HandlerMapping
 *  org.springframework.web.util.pattern.PathPattern
 */
package org.springframework.boot.actuate.metrics.web.servlet;

import io.micrometer.core.instrument.Tag;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.boot.actuate.metrics.http.Outcome;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.util.pattern.PathPattern;

public final class WebMvcTags {
    private static final String DATA_REST_PATH_PATTERN_ATTRIBUTE = "org.springframework.data.rest.webmvc.RepositoryRestHandlerMapping.EFFECTIVE_REPOSITORY_RESOURCE_LOOKUP_PATH";
    private static final Tag URI_NOT_FOUND = Tag.of((String)"uri", (String)"NOT_FOUND");
    private static final Tag URI_REDIRECTION = Tag.of((String)"uri", (String)"REDIRECTION");
    private static final Tag URI_ROOT = Tag.of((String)"uri", (String)"root");
    private static final Tag URI_UNKNOWN = Tag.of((String)"uri", (String)"UNKNOWN");
    private static final Tag EXCEPTION_NONE = Tag.of((String)"exception", (String)"None");
    private static final Tag STATUS_UNKNOWN = Tag.of((String)"status", (String)"UNKNOWN");
    private static final Tag METHOD_UNKNOWN = Tag.of((String)"method", (String)"UNKNOWN");
    private static final Pattern TRAILING_SLASH_PATTERN = Pattern.compile("/$");
    private static final Pattern MULTIPLE_SLASH_PATTERN = Pattern.compile("//+");

    private WebMvcTags() {
    }

    public static Tag method(HttpServletRequest request) {
        return request != null ? Tag.of((String)"method", (String)request.getMethod()) : METHOD_UNKNOWN;
    }

    public static Tag status(HttpServletResponse response) {
        return response != null ? Tag.of((String)"status", (String)Integer.toString(response.getStatus())) : STATUS_UNKNOWN;
    }

    public static Tag uri(HttpServletRequest request, HttpServletResponse response) {
        return WebMvcTags.uri(request, response, false);
    }

    public static Tag uri(HttpServletRequest request, HttpServletResponse response, boolean ignoreTrailingSlash) {
        if (request != null) {
            String pathInfo;
            HttpStatus status;
            String pattern = WebMvcTags.getMatchingPattern(request);
            if (pattern != null) {
                if (ignoreTrailingSlash && pattern.length() > 1) {
                    pattern = TRAILING_SLASH_PATTERN.matcher(pattern).replaceAll("");
                }
                if (pattern.isEmpty()) {
                    return URI_ROOT;
                }
                return Tag.of((String)"uri", (String)pattern);
            }
            if (response != null && (status = WebMvcTags.extractStatus(response)) != null) {
                if (status.is3xxRedirection()) {
                    return URI_REDIRECTION;
                }
                if (status == HttpStatus.NOT_FOUND) {
                    return URI_NOT_FOUND;
                }
            }
            if ((pathInfo = WebMvcTags.getPathInfo(request)).isEmpty()) {
                return URI_ROOT;
            }
        }
        return URI_UNKNOWN;
    }

    private static HttpStatus extractStatus(HttpServletResponse response) {
        try {
            return HttpStatus.valueOf((int)response.getStatus());
        }
        catch (IllegalArgumentException ex) {
            return null;
        }
    }

    private static String getMatchingPattern(HttpServletRequest request) {
        PathPattern dataRestPathPattern = (PathPattern)request.getAttribute(DATA_REST_PATH_PATTERN_ATTRIBUTE);
        if (dataRestPathPattern != null) {
            return dataRestPathPattern.getPatternString();
        }
        return (String)request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
    }

    private static String getPathInfo(HttpServletRequest request) {
        String pathInfo = request.getPathInfo();
        String uri = StringUtils.hasText((String)pathInfo) ? pathInfo : "/";
        uri = MULTIPLE_SLASH_PATTERN.matcher(uri).replaceAll("/");
        return TRAILING_SLASH_PATTERN.matcher(uri).replaceAll("");
    }

    public static Tag exception(Throwable exception) {
        if (exception != null) {
            String simpleName = exception.getClass().getSimpleName();
            return Tag.of((String)"exception", (String)(StringUtils.hasText((String)simpleName) ? simpleName : exception.getClass().getName()));
        }
        return EXCEPTION_NONE;
    }

    public static Tag outcome(HttpServletResponse response) {
        Outcome outcome = response != null ? Outcome.forStatus(response.getStatus()) : Outcome.UNKNOWN;
        return outcome.asTag();
    }
}

