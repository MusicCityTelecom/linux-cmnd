/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.web.reactive.result.method.annotation.RequestMappingHandlerAdapter
 *  org.springframework.web.reactive.result.method.annotation.RequestMappingHandlerMapping
 */
package org.springframework.boot.autoconfigure.web.reactive;

import org.springframework.web.reactive.result.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.reactive.result.method.annotation.RequestMappingHandlerMapping;

public interface WebFluxRegistrations {
    default public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
        return null;
    }

    default public RequestMappingHandlerAdapter getRequestMappingHandlerAdapter() {
        return null;
    }
}

