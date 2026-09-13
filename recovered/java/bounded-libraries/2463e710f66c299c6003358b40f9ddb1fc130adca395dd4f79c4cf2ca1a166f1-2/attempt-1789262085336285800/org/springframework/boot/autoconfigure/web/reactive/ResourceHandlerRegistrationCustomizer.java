/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.web.reactive.config.ResourceHandlerRegistration
 */
package org.springframework.boot.autoconfigure.web.reactive;

import org.springframework.web.reactive.config.ResourceHandlerRegistration;

@FunctionalInterface
public interface ResourceHandlerRegistrationCustomizer {
    public void customize(ResourceHandlerRegistration var1);
}

