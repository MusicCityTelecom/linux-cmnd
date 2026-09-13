/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.util.Assert
 */
package org.springframework.security.web.header.writers;

import java.util.Collections;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.web.header.Header;
import org.springframework.security.web.header.HeaderWriter;
import org.springframework.util.Assert;

public class StaticHeadersWriter
implements HeaderWriter {
    private final List<Header> headers;

    public StaticHeadersWriter(List<Header> headers) {
        Assert.notEmpty(headers, (String)"headers cannot be null or empty");
        this.headers = headers;
    }

    public StaticHeadersWriter(String headerName, String ... headerValues) {
        this(Collections.singletonList(new Header(headerName, headerValues)));
    }

    @Override
    public void writeHeaders(HttpServletRequest request, HttpServletResponse response) {
        for (Header header : this.headers) {
            if (response.containsHeader(header.getName())) continue;
            for (String value : header.getValues()) {
                response.addHeader(header.getName(), value);
            }
        }
    }

    public String toString() {
        return this.getClass().getName() + " [headers=" + this.headers + "]";
    }
}

