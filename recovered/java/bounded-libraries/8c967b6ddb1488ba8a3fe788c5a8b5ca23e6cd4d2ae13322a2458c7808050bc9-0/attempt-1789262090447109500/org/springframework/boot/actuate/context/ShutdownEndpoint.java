/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.BeansException
 *  org.springframework.context.ApplicationContext
 *  org.springframework.context.ApplicationContextAware
 *  org.springframework.context.ConfigurableApplicationContext
 */
package org.springframework.boot.actuate.context;

import java.util.Collections;
import java.util.Map;
import org.springframework.beans.BeansException;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;

@Endpoint(id="shutdown", enableByDefault=false)
public class ShutdownEndpoint
implements ApplicationContextAware {
    private static final Map<String, String> NO_CONTEXT_MESSAGE = Collections.unmodifiableMap(Collections.singletonMap("message", "No context to shutdown."));
    private static final Map<String, String> SHUTDOWN_MESSAGE = Collections.unmodifiableMap(Collections.singletonMap("message", "Shutting down, bye..."));
    private ConfigurableApplicationContext context;

    @WriteOperation
    public Map<String, String> shutdown() {
        if (this.context == null) {
            return NO_CONTEXT_MESSAGE;
        }
        try {
            Map<String, String> map = SHUTDOWN_MESSAGE;
            return map;
        }
        finally {
            Thread thread = new Thread(this::performShutdown);
            thread.setContextClassLoader(this.getClass().getClassLoader());
            thread.start();
        }
    }

    private void performShutdown() {
        try {
            Thread.sleep(500L);
        }
        catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        this.context.close();
    }

    public void setApplicationContext(ApplicationContext context) throws BeansException {
        if (context instanceof ConfigurableApplicationContext) {
            this.context = (ConfigurableApplicationContext)context;
        }
    }
}

