/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletResponse
 */
package org.springframework.boot.actuate.web.trace.servlet;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.springframework.boot.actuate.trace.http.TraceableResponse;

final class TraceableHttpServletResponse
implements TraceableResponse {
    private final HttpServletResponse delegate;

    TraceableHttpServletResponse(HttpServletResponse response) {
        this.delegate = response;
    }

    @Override
    public int getStatus() {
        return this.delegate.getStatus();
    }

    @Override
    public Map<String, List<String>> getHeaders() {
        return this.extractHeaders();
    }

    private Map<String, List<String>> extractHeaders() {
        LinkedHashMap<String, List<String>> headers = new LinkedHashMap<String, List<String>>();
        for (String name : this.delegate.getHeaderNames()) {
            headers.put(name, new ArrayList(this.delegate.getHeaders(name)));
        }
        return headers;
    }
}

