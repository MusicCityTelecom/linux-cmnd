/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.context.ApplicationContext
 *  org.springframework.context.ApplicationContextInitializer
 *  org.springframework.context.ApplicationListener
 *  org.springframework.context.ConfigurableApplicationContext
 *  org.springframework.core.env.ConfigurableEnvironment
 *  org.springframework.core.env.MapPropertySource
 *  org.springframework.core.env.MutablePropertySources
 *  org.springframework.core.env.PropertySource
 */
package org.springframework.boot.rsocket.context;

import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.rsocket.context.RSocketServerInitializedEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;

public class RSocketPortInfoApplicationContextInitializer
implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    public void initialize(ConfigurableApplicationContext applicationContext) {
        applicationContext.addApplicationListener((ApplicationListener)new Listener(applicationContext));
    }

    private static class Listener
    implements ApplicationListener<RSocketServerInitializedEvent> {
        private static final String PROPERTY_NAME = "local.rsocket.server.port";
        private final ConfigurableApplicationContext applicationContext;

        Listener(ConfigurableApplicationContext applicationContext) {
            this.applicationContext = applicationContext;
        }

        public void onApplicationEvent(RSocketServerInitializedEvent event) {
            if (event.getServer().address() != null) {
                this.setPortProperty((ApplicationContext)this.applicationContext, event.getServer().address().getPort());
            }
        }

        private void setPortProperty(ApplicationContext context, int port) {
            if (context instanceof ConfigurableApplicationContext) {
                this.setPortProperty(((ConfigurableApplicationContext)context).getEnvironment(), port);
            }
            if (context.getParent() != null) {
                this.setPortProperty(context.getParent(), port);
            }
        }

        private void setPortProperty(ConfigurableEnvironment environment, int port) {
            MutablePropertySources sources = environment.getPropertySources();
            PropertySource source = sources.get("server.ports");
            if (source == null) {
                source = new MapPropertySource("server.ports", new HashMap());
                sources.addFirst(source);
            }
            this.setPortProperty(port, source);
        }

        private void setPortProperty(int port, PropertySource<?> source) {
            ((Map)source.getSource()).put(PROPERTY_NAME, port);
        }
    }
}

