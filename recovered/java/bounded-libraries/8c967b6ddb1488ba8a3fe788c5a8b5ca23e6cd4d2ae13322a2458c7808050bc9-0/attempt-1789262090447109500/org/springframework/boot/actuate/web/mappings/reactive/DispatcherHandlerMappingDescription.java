/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.actuate.web.mappings.reactive;

import org.springframework.boot.actuate.web.mappings.reactive.DispatcherHandlerMappingDetails;

public class DispatcherHandlerMappingDescription {
    private final String predicate;
    private final String handler;
    private final DispatcherHandlerMappingDetails details;

    DispatcherHandlerMappingDescription(String predicate, String handler, DispatcherHandlerMappingDetails details) {
        this.predicate = predicate;
        this.handler = handler;
        this.details = details;
    }

    public String getHandler() {
        return this.handler;
    }

    public String getPredicate() {
        return this.predicate;
    }

    public DispatcherHandlerMappingDetails getDetails() {
        return this.details;
    }
}

