/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.http.HttpStatus
 */
package org.springframework.security.web.header.writers;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.header.Header;
import org.springframework.security.web.header.HeaderWriter;
import org.springframework.security.web.header.writers.StaticHeadersWriter;

public final class CacheControlHeadersWriter
implements HeaderWriter {
    private static final String EXPIRES = "Expires";
    private static final String PRAGMA = "Pragma";
    private static final String CACHE_CONTROL = "Cache-Control";
    private final HeaderWriter delegate = new StaticHeadersWriter(CacheControlHeadersWriter.createHeaders());

    @Override
    public void writeHeaders(HttpServletRequest request, HttpServletResponse response) {
        if (this.hasHeader(response, CACHE_CONTROL) || this.hasHeader(response, EXPIRES) || this.hasHeader(response, PRAGMA) || response.getStatus() == HttpStatus.NOT_MODIFIED.value()) {
            return;
        }
        this.delegate.writeHeaders(request, response);
    }

    private boolean hasHeader(HttpServletResponse response, String headerName) {
        return response.getHeader(headerName) != null;
    }

    private static List<Header> createHeaders() {
        ArrayList<Header> headers = new ArrayList<Header>(3);
        headers.add(new Header(CACHE_CONTROL, "no-cache, no-store, max-age=0, must-revalidate"));
        headers.add(new Header(PRAGMA, "no-cache"));
        headers.add(new Header(EXPIRES, "0"));
        return headers;
    }
}

