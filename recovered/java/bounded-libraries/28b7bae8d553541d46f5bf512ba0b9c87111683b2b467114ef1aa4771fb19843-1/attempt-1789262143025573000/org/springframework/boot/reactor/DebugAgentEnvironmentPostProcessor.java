/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.Ordered
 *  org.springframework.core.env.ConfigurableEnvironment
 *  org.springframework.util.ClassUtils
 */
package org.springframework.boot.reactor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.util.ClassUtils;

public class DebugAgentEnvironmentPostProcessor
implements EnvironmentPostProcessor,
Ordered {
    private static final String REACTOR_DEBUGAGENT_CLASS = "reactor.tools.agent.ReactorDebugAgent";
    private static final String DEBUGAGENT_ENABLED_CONFIG_KEY = "spring.reactor.debug-agent.enabled";

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        Boolean agentEnabled;
        if (ClassUtils.isPresent((String)REACTOR_DEBUGAGENT_CLASS, null) && (agentEnabled = (Boolean)environment.getProperty(DEBUGAGENT_ENABLED_CONFIG_KEY, Boolean.class)) != Boolean.FALSE) {
            try {
                Class<?> debugAgent = Class.forName(REACTOR_DEBUGAGENT_CLASS);
                debugAgent.getMethod("init", new Class[0]).invoke(null, new Object[0]);
            }
            catch (Exception ex) {
                throw new RuntimeException("Failed to init Reactor's debug agent", ex);
            }
        }
    }

    public int getOrder() {
        return Integer.MAX_VALUE;
    }
}

