/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.context.event;

import org.springframework.boot.ConfigurableBootstrapContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.event.SpringApplicationEvent;

public class ApplicationStartingEvent
extends SpringApplicationEvent {
    private final ConfigurableBootstrapContext bootstrapContext;

    public ApplicationStartingEvent(ConfigurableBootstrapContext bootstrapContext, SpringApplication application, String[] args) {
        super(application, args);
        this.bootstrapContext = bootstrapContext;
    }

    public ConfigurableBootstrapContext getBootstrapContext() {
        return this.bootstrapContext;
    }
}

