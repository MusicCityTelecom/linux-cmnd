/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.context.ConfigurableApplicationContext
 */
package org.springframework.boot.context.event;

import java.time.Duration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.event.SpringApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;

public class ApplicationStartedEvent
extends SpringApplicationEvent {
    private final ConfigurableApplicationContext context;
    private final Duration timeTaken;

    @Deprecated
    public ApplicationStartedEvent(SpringApplication application, String[] args, ConfigurableApplicationContext context) {
        this(application, args, context, null);
    }

    public ApplicationStartedEvent(SpringApplication application, String[] args, ConfigurableApplicationContext context, Duration timeTaken) {
        super(application, args);
        this.context = context;
        this.timeTaken = timeTaken;
    }

    public ConfigurableApplicationContext getApplicationContext() {
        return this.context;
    }

    public Duration getTimeTaken() {
        return this.timeTaken;
    }
}

