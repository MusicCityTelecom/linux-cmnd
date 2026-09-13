/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.springframework.core.Ordered
 *  org.springframework.core.env.ConfigurableEnvironment
 */
package org.springframework.boot.env;

import org.apache.commons.logging.Log;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.env.RandomValuePropertySource;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;

public class RandomValuePropertySourceEnvironmentPostProcessor
implements EnvironmentPostProcessor,
Ordered {
    public static final int ORDER = -2147483647;
    private final Log logger;

    public RandomValuePropertySourceEnvironmentPostProcessor(Log logger) {
        this.logger = logger;
    }

    public int getOrder() {
        return -2147483647;
    }

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        RandomValuePropertySource.addToEnvironment(environment, this.logger);
    }
}

