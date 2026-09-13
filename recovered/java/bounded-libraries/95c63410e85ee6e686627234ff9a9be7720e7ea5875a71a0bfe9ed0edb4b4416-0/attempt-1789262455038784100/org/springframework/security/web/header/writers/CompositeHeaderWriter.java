/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.util.Assert
 */
package org.springframework.security.web.header.writers;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.web.header.HeaderWriter;
import org.springframework.util.Assert;

public class CompositeHeaderWriter
implements HeaderWriter {
    private final List<HeaderWriter> headerWriters;

    public CompositeHeaderWriter(List<HeaderWriter> headerWriters) {
        Assert.notEmpty(headerWriters, (String)"headerWriters cannot be empty");
        this.headerWriters = headerWriters;
    }

    @Override
    public void writeHeaders(HttpServletRequest request, HttpServletResponse response) {
        this.headerWriters.forEach(headerWriter -> headerWriter.writeHeaders(request, response));
    }
}

