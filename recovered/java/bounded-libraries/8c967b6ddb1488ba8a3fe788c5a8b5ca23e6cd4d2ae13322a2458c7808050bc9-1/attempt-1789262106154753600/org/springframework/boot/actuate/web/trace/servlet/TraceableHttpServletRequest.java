/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  org.springframework.util.StringUtils
 *  org.springframework.web.util.UriUtils
 */
package org.springframework.boot.actuate.web.trace.servlet;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.boot.actuate.trace.http.TraceableRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.util.UriUtils;

final class TraceableHttpServletRequest
implements TraceableRequest {
    private final HttpServletRequest request;

    TraceableHttpServletRequest(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public String getMethod() {
        return this.request.getMethod();
    }

    @Override
    public URI getUri() {
        String queryString = this.request.getQueryString();
        if (!StringUtils.hasText((String)queryString)) {
            return URI.create(this.request.getRequestURL().toString());
        }
        try {
            StringBuffer urlBuffer = this.appendQueryString(queryString);
            return new URI(urlBuffer.toString());
        }
        catch (URISyntaxException ex) {
            String encoded = UriUtils.encodeQuery((String)queryString, (Charset)StandardCharsets.UTF_8);
            StringBuffer urlBuffer = this.appendQueryString(encoded);
            return URI.create(urlBuffer.toString());
        }
    }

    private StringBuffer appendQueryString(String queryString) {
        return this.request.getRequestURL().append("?").append(queryString);
    }

    @Override
    public Map<String, List<String>> getHeaders() {
        return this.extractHeaders();
    }

    @Override
    public String getRemoteAddress() {
        return this.request.getRemoteAddr();
    }

    private Map<String, List<String>> extractHeaders() {
        LinkedHashMap<String, List<String>> headers = new LinkedHashMap<String, List<String>>();
        Enumeration names = this.request.getHeaderNames();
        while (names.hasMoreElements()) {
            String name = (String)names.nextElement();
            headers.put(name, Collections.list(this.request.getHeaders(name)));
        }
        return headers;
    }
}

