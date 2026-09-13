/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.env.ConfigurableEnvironment
 */
package org.springframework.boot.env;

import org.springframework.boot.SpringApplication;
import org.springframework.core.env.ConfigurableEnvironment;

@FunctionalInterface
public interface EnvironmentPostProcessor {
    public void postProcessEnvironment(ConfigurableEnvironment var1, SpringApplication var2);
}

