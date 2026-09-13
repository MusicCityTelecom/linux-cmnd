/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.actuate.trace.http;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

public enum Include {
    REQUEST_HEADERS,
    RESPONSE_HEADERS,
    COOKIE_HEADERS,
    AUTHORIZATION_HEADER,
    PRINCIPAL,
    REMOTE_ADDRESS,
    SESSION_ID,
    TIME_TAKEN;

    private static final Set<Include> DEFAULT_INCLUDES;

    public static Set<Include> defaultIncludes() {
        return DEFAULT_INCLUDES;
    }

    static {
        LinkedHashSet<Include> defaultIncludes = new LinkedHashSet<Include>();
        defaultIncludes.add(REQUEST_HEADERS);
        defaultIncludes.add(RESPONSE_HEADERS);
        defaultIncludes.add(TIME_TAKEN);
        DEFAULT_INCLUDES = Collections.unmodifiableSet(defaultIncludes);
    }
}

