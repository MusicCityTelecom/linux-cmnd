/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.MimeType
 *  org.springframework.util.MimeTypeUtils
 */
package org.springframework.boot.actuate.endpoint;

import org.springframework.boot.actuate.endpoint.Producible;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;

public enum ApiVersion implements Producible<ApiVersion>
{
    V2("application/vnd.spring-boot.actuator.v2+json"),
    V3("application/vnd.spring-boot.actuator.v3+json");

    public static final ApiVersion LATEST;
    private final MimeType mimeType;

    private ApiVersion(String mimeType) {
        this.mimeType = MimeTypeUtils.parseMimeType((String)mimeType);
    }

    @Override
    public MimeType getProducedMimeType() {
        return this.mimeType;
    }

    static {
        LATEST = V3;
    }
}

