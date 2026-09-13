/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.context.ApplicationListener
 *  org.springframework.core.Ordered
 *  org.springframework.core.env.ConfigurableEnvironment
 *  org.springframework.core.env.Environment
 */
package org.springframework.boot.context.config;

import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;

public class AnsiOutputApplicationListener
implements ApplicationListener<ApplicationEnvironmentPreparedEvent>,
Ordered {
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
        ConfigurableEnvironment environment = event.getEnvironment();
        Binder.get((Environment)environment).bind("spring.output.ansi.enabled", AnsiOutput.Enabled.class).ifBound(AnsiOutput::setEnabled);
        AnsiOutput.setConsoleAvailable((Boolean)environment.getProperty("spring.output.ansi.console-available", Boolean.class));
    }

    public int getOrder() {
        return -2147483637;
    }
}

