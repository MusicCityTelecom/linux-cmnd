/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.actuate.trace.http;

import java.net.URI;
import java.util.List;
import java.util.Map;

public interface TraceableRequest {
    public String getMethod();

    public URI getUri();

    public Map<String, List<String>> getHeaders();

    public String getRemoteAddress();
}

