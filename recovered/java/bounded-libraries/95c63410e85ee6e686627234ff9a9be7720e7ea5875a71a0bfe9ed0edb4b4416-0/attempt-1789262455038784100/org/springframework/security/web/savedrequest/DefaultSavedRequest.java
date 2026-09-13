/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder
 *  javax.servlet.ServletRequest
 *  javax.servlet.http.Cookie
 *  javax.servlet.http.HttpServletRequest
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.util.Assert
 *  org.springframework.util.ObjectUtils
 */
package org.springframework.security.web.savedrequest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.web.PortResolver;
import org.springframework.security.web.savedrequest.SavedCookie;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

public class DefaultSavedRequest
implements SavedRequest {
    private static final long serialVersionUID = 570L;
    protected static final Log logger = LogFactory.getLog(DefaultSavedRequest.class);
    private static final String HEADER_IF_NONE_MATCH = "If-None-Match";
    private static final String HEADER_IF_MODIFIED_SINCE = "If-Modified-Since";
    private final ArrayList<SavedCookie> cookies = new ArrayList();
    private final ArrayList<Locale> locales = new ArrayList();
    private final Map<String, List<String>> headers = new TreeMap<String, List<String>>(String.CASE_INSENSITIVE_ORDER);
    private final Map<String, String[]> parameters = new TreeMap<String, String[]>();
    private final String contextPath;
    private final String method;
    private final String pathInfo;
    private final String queryString;
    private final String requestURI;
    private final String requestURL;
    private final String scheme;
    private final String serverName;
    private final String servletPath;
    private final int serverPort;

    public DefaultSavedRequest(HttpServletRequest request, PortResolver portResolver) {
        Assert.notNull((Object)request, (String)"Request required");
        Assert.notNull((Object)portResolver, (String)"PortResolver required");
        this.addCookies(request.getCookies());
        Enumeration names = request.getHeaderNames();
        while (names.hasMoreElements()) {
            String name = (String)names.nextElement();
            if (HEADER_IF_MODIFIED_SINCE.equalsIgnoreCase(name) || HEADER_IF_NONE_MATCH.equalsIgnoreCase(name)) continue;
            Enumeration values = request.getHeaders(name);
            while (values.hasMoreElements()) {
                this.addHeader(name, (String)values.nextElement());
            }
        }
        this.addLocales(request.getLocales());
        this.addParameters(request.getParameterMap());
        this.method = request.getMethod();
        this.pathInfo = request.getPathInfo();
        this.queryString = request.getQueryString();
        this.requestURI = request.getRequestURI();
        this.serverPort = portResolver.getServerPort((ServletRequest)request);
        this.requestURL = request.getRequestURL().toString();
        this.scheme = request.getScheme();
        this.serverName = request.getServerName();
        this.contextPath = request.getContextPath();
        this.servletPath = request.getServletPath();
    }

    private DefaultSavedRequest(Builder builder) {
        this.contextPath = builder.contextPath;
        this.method = builder.method;
        this.pathInfo = builder.pathInfo;
        this.queryString = builder.queryString;
        this.requestURI = builder.requestURI;
        this.requestURL = builder.requestURL;
        this.scheme = builder.scheme;
        this.serverName = builder.serverName;
        this.servletPath = builder.servletPath;
        this.serverPort = builder.serverPort;
    }

    private void addCookies(Cookie[] cookies) {
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                this.addCookie(cookie);
            }
        }
    }

    private void addCookie(Cookie cookie) {
        this.cookies.add(new SavedCookie(cookie));
    }

    private void addHeader(String name, String value) {
        List values = this.headers.computeIfAbsent(name, key -> new ArrayList());
        values.add(value);
    }

    private void addLocales(Enumeration<Locale> locales) {
        while (locales.hasMoreElements()) {
            Locale locale = locales.nextElement();
            this.addLocale(locale);
        }
    }

    private void addLocale(Locale locale) {
        this.locales.add(locale);
    }

    private void addParameters(Map<String, String[]> parameters) {
        if (!ObjectUtils.isEmpty(parameters)) {
            for (String paramName : parameters.keySet()) {
                String[] paramValues = parameters.get(paramName);
                if (paramValues instanceof String[]) {
                    this.addParameter(paramName, paramValues);
                    continue;
                }
                logger.warn((Object)"ServletRequest.getParameterMap() returned non-String array");
            }
        }
    }

    private void addParameter(String name, String[] values) {
        this.parameters.put(name, values);
    }

    public boolean doesRequestMatch(HttpServletRequest request, PortResolver portResolver) {
        if (!this.propertyEquals(this.pathInfo, request.getPathInfo())) {
            return false;
        }
        if (!this.propertyEquals(this.queryString, request.getQueryString())) {
            return false;
        }
        if (!this.propertyEquals(this.requestURI, request.getRequestURI())) {
            return false;
        }
        if (!"GET".equals(request.getMethod()) && "GET".equals(this.method)) {
            return false;
        }
        if (!this.propertyEquals(this.serverPort, portResolver.getServerPort((ServletRequest)request))) {
            return false;
        }
        if (!this.propertyEquals(this.requestURL, request.getRequestURL().toString())) {
            return false;
        }
        if (!this.propertyEquals(this.scheme, request.getScheme())) {
            return false;
        }
        if (!this.propertyEquals(this.serverName, request.getServerName())) {
            return false;
        }
        if (!this.propertyEquals(this.contextPath, request.getContextPath())) {
            return false;
        }
        return this.propertyEquals(this.servletPath, request.getServletPath());
    }

    public String getContextPath() {
        return this.contextPath;
    }

    @Override
    public List<Cookie> getCookies() {
        ArrayList<Cookie> cookieList = new ArrayList<Cookie>(this.cookies.size());
        for (SavedCookie savedCookie : this.cookies) {
            cookieList.add(savedCookie.getCookie());
        }
        return cookieList;
    }

    @Override
    public String getRedirectUrl() {
        return UrlUtils.buildFullRequestUrl(this.scheme, this.serverName, this.serverPort, this.requestURI, this.queryString);
    }

    @Override
    public Collection<String> getHeaderNames() {
        return this.headers.keySet();
    }

    @Override
    public List<String> getHeaderValues(String name) {
        List<String> values = this.headers.get(name);
        return values != null ? values : Collections.emptyList();
    }

    @Override
    public List<Locale> getLocales() {
        return this.locales;
    }

    @Override
    public String getMethod() {
        return this.method;
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return this.parameters;
    }

    public Collection<String> getParameterNames() {
        return this.parameters.keySet();
    }

    @Override
    public String[] getParameterValues(String name) {
        return this.parameters.get(name);
    }

    public String getPathInfo() {
        return this.pathInfo;
    }

    public String getQueryString() {
        return this.queryString;
    }

    public String getRequestURI() {
        return this.requestURI;
    }

    public String getRequestURL() {
        return this.requestURL;
    }

    public String getScheme() {
        return this.scheme;
    }

    public String getServerName() {
        return this.serverName;
    }

    public int getServerPort() {
        return this.serverPort;
    }

    public String getServletPath() {
        return this.servletPath;
    }

    private boolean propertyEquals(Object arg1, Object arg2) {
        if (arg1 == null && arg2 == null) {
            return true;
        }
        if (arg1 == null || arg2 == null) {
            return false;
        }
        return arg1.equals(arg2);
    }

    public String toString() {
        return "DefaultSavedRequest [" + this.getRedirectUrl() + "]";
    }

    @JsonIgnoreProperties(ignoreUnknown=true)
    @JsonPOJOBuilder(withPrefix="set")
    public static class Builder {
        private List<SavedCookie> cookies = null;
        private List<Locale> locales = null;
        private Map<String, List<String>> headers = new TreeMap<String, List<String>>(String.CASE_INSENSITIVE_ORDER);
        private Map<String, String[]> parameters = new TreeMap<String, String[]>();
        private String contextPath;
        private String method;
        private String pathInfo;
        private String queryString;
        private String requestURI;
        private String requestURL;
        private String scheme;
        private String serverName;
        private String servletPath;
        private int serverPort = 80;

        public Builder setCookies(List<SavedCookie> cookies) {
            this.cookies = cookies;
            return this;
        }

        public Builder setLocales(List<Locale> locales) {
            this.locales = locales;
            return this;
        }

        public Builder setHeaders(Map<String, List<String>> header) {
            this.headers.putAll(header);
            return this;
        }

        public Builder setParameters(Map<String, String[]> parameters) {
            this.parameters = parameters;
            return this;
        }

        public Builder setContextPath(String contextPath) {
            this.contextPath = contextPath;
            return this;
        }

        public Builder setMethod(String method) {
            this.method = method;
            return this;
        }

        public Builder setPathInfo(String pathInfo) {
            this.pathInfo = pathInfo;
            return this;
        }

        public Builder setQueryString(String queryString) {
            this.queryString = queryString;
            return this;
        }

        public Builder setRequestURI(String requestURI) {
            this.requestURI = requestURI;
            return this;
        }

        public Builder setRequestURL(String requestURL) {
            this.requestURL = requestURL;
            return this;
        }

        public Builder setScheme(String scheme) {
            this.scheme = scheme;
            return this;
        }

        public Builder setServerName(String serverName) {
            this.serverName = serverName;
            return this;
        }

        public Builder setServletPath(String servletPath) {
            this.servletPath = servletPath;
            return this;
        }

        public Builder setServerPort(int serverPort) {
            this.serverPort = serverPort;
            return this;
        }

        public DefaultSavedRequest build() {
            DefaultSavedRequest savedRequest = new DefaultSavedRequest(this);
            if (!ObjectUtils.isEmpty(this.cookies)) {
                for (SavedCookie savedCookie : this.cookies) {
                    savedRequest.addCookie(savedCookie.getCookie());
                }
            }
            if (!ObjectUtils.isEmpty(this.locales)) {
                savedRequest.locales.addAll(this.locales);
            }
            savedRequest.addParameters(this.parameters);
            this.headers.remove(DefaultSavedRequest.HEADER_IF_MODIFIED_SINCE);
            this.headers.remove(DefaultSavedRequest.HEADER_IF_NONE_MATCH);
            for (Map.Entry entry : this.headers.entrySet()) {
                String headerName = (String)entry.getKey();
                List headerValues = (List)entry.getValue();
                for (String headerValue : headerValues) {
                    savedRequest.addHeader(headerName, headerValue);
                }
            }
            return savedRequest;
        }
    }
}

