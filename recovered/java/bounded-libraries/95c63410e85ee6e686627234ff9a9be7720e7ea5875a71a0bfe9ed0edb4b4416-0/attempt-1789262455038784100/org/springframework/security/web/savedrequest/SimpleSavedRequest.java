/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.Cookie
 *  org.springframework.util.Assert
 */
package org.springframework.security.web.savedrequest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.servlet.http.Cookie;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.Assert;

public class SimpleSavedRequest
implements SavedRequest {
    private String redirectUrl;
    private List<Cookie> cookies = new ArrayList<Cookie>();
    private String method = "GET";
    private Map<String, List<String>> headers = new HashMap<String, List<String>>();
    private List<Locale> locales = new ArrayList<Locale>();
    private Map<String, String[]> parameters = new HashMap<String, String[]>();

    public SimpleSavedRequest() {
    }

    public SimpleSavedRequest(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public SimpleSavedRequest(SavedRequest request) {
        this.redirectUrl = request.getRedirectUrl();
        this.cookies = request.getCookies();
        for (String headerName : request.getHeaderNames()) {
            this.headers.put(headerName, request.getHeaderValues(headerName));
        }
        this.locales = request.getLocales();
        this.parameters = request.getParameterMap();
        this.method = request.getMethod();
    }

    @Override
    public String getRedirectUrl() {
        return this.redirectUrl;
    }

    @Override
    public List<Cookie> getCookies() {
        return this.cookies;
    }

    @Override
    public String getMethod() {
        return this.method;
    }

    @Override
    public List<String> getHeaderValues(String name) {
        return this.headers.getOrDefault(name, new ArrayList());
    }

    @Override
    public Collection<String> getHeaderNames() {
        return this.headers.keySet();
    }

    @Override
    public List<Locale> getLocales() {
        return this.locales;
    }

    @Override
    public String[] getParameterValues(String name) {
        return this.parameters.getOrDefault(name, new String[0]);
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return this.parameters;
    }

    public void setRedirectUrl(String redirectUrl) {
        Assert.notNull((Object)redirectUrl, (String)"redirectUrl cannot be null");
        this.redirectUrl = redirectUrl;
    }

    public void setCookies(List<Cookie> cookies) {
        Assert.notNull(cookies, (String)"cookies cannot be null");
        this.cookies = cookies;
    }

    public void setMethod(String method) {
        Assert.notNull((Object)method, (String)"method cannot be null");
        this.method = method;
    }

    public void setHeaders(Map<String, List<String>> headers) {
        Assert.notNull(headers, (String)"headers cannot be null");
        this.headers = headers;
    }

    public void setLocales(List<Locale> locales) {
        Assert.notNull((Object)"locales cannot be null");
        this.locales = locales;
    }

    public void setParameters(Map<String, String[]> parameters) {
        Assert.notNull(parameters, (String)"parameters cannot be null");
        this.parameters = parameters;
    }
}

