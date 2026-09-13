/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.rsocket.Payload
 *  org.springframework.util.MimeType
 */
package org.springframework.messaging.rsocket;

import io.rsocket.Payload;
import java.util.Map;
import org.springframework.util.MimeType;

public interface MetadataExtractor {
    public static final String ROUTE_KEY = "route";

    public Map<String, Object> extract(Payload var1, MimeType var2);
}

