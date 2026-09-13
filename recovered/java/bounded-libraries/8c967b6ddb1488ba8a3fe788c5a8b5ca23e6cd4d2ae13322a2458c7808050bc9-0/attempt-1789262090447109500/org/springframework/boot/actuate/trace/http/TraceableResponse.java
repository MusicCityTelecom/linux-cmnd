/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.actuate.trace.http;

import java.util.List;
import java.util.Map;

public interface TraceableResponse {
    public int getStatus();

    public Map<String, List<String>> getHeaders();
}

