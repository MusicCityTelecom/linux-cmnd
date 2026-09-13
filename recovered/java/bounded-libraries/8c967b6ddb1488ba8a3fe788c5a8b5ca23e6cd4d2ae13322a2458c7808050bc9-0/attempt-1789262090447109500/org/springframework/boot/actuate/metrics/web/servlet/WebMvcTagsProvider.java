/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.micrometer.core.instrument.Tag
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 */
package org.springframework.boot.actuate.metrics.web.servlet;

import io.micrometer.core.instrument.Tag;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface WebMvcTagsProvider {
    public Iterable<Tag> getTags(HttpServletRequest var1, HttpServletResponse var2, Object var3, Throwable var4);

    public Iterable<Tag> getLongRequestTags(HttpServletRequest var1, Object var2);
}

