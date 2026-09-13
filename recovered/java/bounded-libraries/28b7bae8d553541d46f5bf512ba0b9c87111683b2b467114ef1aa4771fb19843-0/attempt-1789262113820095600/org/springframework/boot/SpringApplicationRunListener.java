/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.context.ConfigurableApplicationContext
 *  org.springframework.core.env.ConfigurableEnvironment
 */
package org.springframework.boot;

import java.time.Duration;
import org.springframework.boot.ConfigurableBootstrapContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

public interface SpringApplicationRunListener {
    default public void starting(ConfigurableBootstrapContext bootstrapContext) {
    }

    default public void environmentPrepared(ConfigurableBootstrapContext bootstrapContext, ConfigurableEnvironment environment) {
    }

    default public void contextPrepared(ConfigurableApplicationContext context) {
    }

    default public void contextLoaded(ConfigurableApplicationContext context) {
    }

    default public void started(ConfigurableApplicationContext context, Duration timeTaken) {
        this.started(context);
    }

    @Deprecated
    default public void started(ConfigurableApplicationContext context) {
    }

    default public void ready(ConfigurableApplicationContext context, Duration timeTaken) {
        this.running(context);
    }

    @Deprecated
    default public void running(ConfigurableApplicationContext context) {
    }

    default public void failed(ConfigurableApplicationContext context, Throwable exception) {
    }
}

