/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.context.ApplicationEvent
 *  org.springframework.context.ConfigurableApplicationContext
 */
package org.springframework.boot;

import org.springframework.boot.BootstrapContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;

public class BootstrapContextClosedEvent
extends ApplicationEvent {
    private final ConfigurableApplicationContext applicationContext;

    BootstrapContextClosedEvent(BootstrapContext source, ConfigurableApplicationContext applicationContext) {
        super((Object)source);
        this.applicationContext = applicationContext;
    }

    public BootstrapContext getBootstrapContext() {
        return (BootstrapContext)this.source;
    }

    public ConfigurableApplicationContext getApplicationContext() {
        return this.applicationContext;
    }
}

