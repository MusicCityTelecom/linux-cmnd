/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.actuate.trace.http;

import java.util.List;
import org.springframework.boot.actuate.trace.http.HttpTrace;

public interface HttpTraceRepository {
    public List<HttpTrace> findAll();

    public void add(HttpTrace var1);
}

