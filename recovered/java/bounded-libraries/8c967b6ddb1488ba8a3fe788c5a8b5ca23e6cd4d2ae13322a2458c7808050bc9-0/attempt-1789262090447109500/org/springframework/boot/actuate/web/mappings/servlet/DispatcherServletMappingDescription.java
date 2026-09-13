/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.actuate.web.mappings.servlet;

import org.springframework.boot.actuate.web.mappings.servlet.DispatcherServletMappingDetails;

public class DispatcherServletMappingDescription {
    private final String handler;
    private final String predicate;
    private final DispatcherServletMappingDetails details;

    DispatcherServletMappingDescription(String predicate, String handler, DispatcherServletMappingDetails details) {
        this.handler = handler;
        this.predicate = predicate;
        this.details = details;
    }

    public String getHandler() {
        return this.handler;
    }

    public String getPredicate() {
        return this.predicate;
    }

    public DispatcherServletMappingDetails getDetails() {
        return this.details;
    }
}

