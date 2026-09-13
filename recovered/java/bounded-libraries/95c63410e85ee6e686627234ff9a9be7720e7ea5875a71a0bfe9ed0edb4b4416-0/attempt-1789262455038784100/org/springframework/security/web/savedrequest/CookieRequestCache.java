/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.Cookie
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.util.Assert
 *  org.springframework.util.StringUtils
 *  org.springframework.web.util.UriComponents
 *  org.springframework.web.util.UriComponentsBuilder
 *  org.springframework.web.util.WebUtils
 */
package org.springframework.security.web.savedrequest;

import java.util.Base64;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.security.web.savedrequest.SavedRequestAwareWrapper;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.security.web.util.matcher.AnyRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.WebUtils;

public class CookieRequestCache
implements RequestCache {
    private RequestMatcher requestMatcher = AnyRequestMatcher.INSTANCE;
    protected final Log logger = LogFactory.getLog(this.getClass());
    private static final String COOKIE_NAME = "REDIRECT_URI";
    private static final int COOKIE_MAX_AGE = -1;

    @Override
    public void saveRequest(HttpServletRequest request, HttpServletResponse response) {
        if (!this.requestMatcher.matches(request)) {
            this.logger.debug((Object)"Request not saved as configured RequestMatcher did not match");
            return;
        }
        String redirectUrl = UrlUtils.buildFullRequestUrl(request);
        Cookie savedCookie = new Cookie(COOKIE_NAME, CookieRequestCache.encodeCookie(redirectUrl));
        savedCookie.setMaxAge(-1);
        savedCookie.setSecure(request.isSecure());
        savedCookie.setPath(CookieRequestCache.getCookiePath(request));
        savedCookie.setHttpOnly(true);
        response.addCookie(savedCookie);
    }

    @Override
    public SavedRequest getRequest(HttpServletRequest request, HttpServletResponse response) {
        Cookie savedRequestCookie = WebUtils.getCookie((HttpServletRequest)request, (String)COOKIE_NAME);
        if (savedRequestCookie == null) {
            return null;
        }
        String originalURI = CookieRequestCache.decodeCookie(savedRequestCookie.getValue());
        UriComponents uriComponents = UriComponentsBuilder.fromUriString((String)originalURI).build();
        DefaultSavedRequest.Builder builder = new DefaultSavedRequest.Builder();
        int port = this.getPort(uriComponents);
        return builder.setScheme(uriComponents.getScheme()).setServerName(uriComponents.getHost()).setRequestURI(uriComponents.getPath()).setQueryString(uriComponents.getQuery()).setServerPort(port).setMethod(request.getMethod()).build();
    }

    private int getPort(UriComponents uriComponents) {
        int port = uriComponents.getPort();
        if (port != -1) {
            return port;
        }
        if ("https".equalsIgnoreCase(uriComponents.getScheme())) {
            return 443;
        }
        return 80;
    }

    @Override
    public HttpServletRequest getMatchingRequest(HttpServletRequest request, HttpServletResponse response) {
        SavedRequest saved = this.getRequest(request, response);
        if (!this.matchesSavedRequest(request, saved)) {
            this.logger.debug((Object)"saved request doesn't match");
            return null;
        }
        this.removeRequest(request, response);
        return new SavedRequestAwareWrapper(saved, request);
    }

    @Override
    public void removeRequest(HttpServletRequest request, HttpServletResponse response) {
        Cookie removeSavedRequestCookie = new Cookie(COOKIE_NAME, "");
        removeSavedRequestCookie.setSecure(request.isSecure());
        removeSavedRequestCookie.setHttpOnly(true);
        removeSavedRequestCookie.setPath(CookieRequestCache.getCookiePath(request));
        removeSavedRequestCookie.setMaxAge(0);
        response.addCookie(removeSavedRequestCookie);
    }

    private static String encodeCookie(String cookieValue) {
        return Base64.getEncoder().encodeToString(cookieValue.getBytes());
    }

    private static String decodeCookie(String encodedCookieValue) {
        return new String(Base64.getDecoder().decode(encodedCookieValue.getBytes()));
    }

    private static String getCookiePath(HttpServletRequest request) {
        String contextPath = request.getContextPath();
        return !StringUtils.isEmpty((Object)contextPath) ? contextPath : "/";
    }

    private boolean matchesSavedRequest(HttpServletRequest request, SavedRequest savedRequest) {
        if (savedRequest == null) {
            return false;
        }
        String currentUrl = UrlUtils.buildFullRequestUrl(request);
        return savedRequest.getRedirectUrl().equals(currentUrl);
    }

    public void setRequestMatcher(RequestMatcher requestMatcher) {
        Assert.notNull((Object)requestMatcher, (String)"requestMatcher should not be null");
        this.requestMatcher = requestMatcher;
    }
}

