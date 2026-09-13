/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.context.ApplicationEvent
 */
package org.springframework.boot.context.event;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationEvent;

public abstract class SpringApplicationEvent
extends ApplicationEvent {
    private final String[] args;

    public SpringApplicationEvent(SpringApplication application, String[] args) {
        super((Object)application);
        this.args = args;
    }

    public SpringApplication getSpringApplication() {
        return (SpringApplication)this.getSource();
    }

    public final String[] getArgs() {
        return this.args;
    }
}

