/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.autoconfigure.security.reactive;

import org.springframework.boot.autoconfigure.security.reactive.StaticResourceRequest;

public final class PathRequest {
    private PathRequest() {
    }

    public static StaticResourceRequest toStaticResources() {
        return StaticResourceRequest.INSTANCE;
    }
}

