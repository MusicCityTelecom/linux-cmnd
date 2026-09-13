/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.http.HttpStatus
 *  org.springframework.http.server.reactive.ServerHttpResponse
 */
package org.springframework.boot.actuate.web.trace.reactive;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.boot.actuate.trace.http.TraceableResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;

class TraceableServerHttpResponse
implements TraceableResponse {
    private final int status;
    private final Map<String, List<String>> headers;

    TraceableServerHttpResponse(ServerHttpResponse response) {
        this.status = response.getStatusCode() != null ? response.getStatusCode().value() : HttpStatus.OK.value();
        this.headers = new LinkedHashMap<String, List<String>>((Map<String, List<String>>)response.getHeaders());
    }

    @Override
    public int getStatus() {
        return this.status;
    }

    @Override
    public Map<String, List<String>> getHeaders() {
        return this.headers;
    }
}

