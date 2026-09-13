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
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.web.context;

import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.web.context.WebServerApplicationContext;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.util.StringUtils;

public class ServerPortInfoApplicationContextInitializer
implements ApplicationContextInitializer<ConfigurableApplicationContext>,
ApplicationListener<WebServerInitializedEvent> {
    public void initialize(ConfigurableApplicationContext applicationContext) {
        applicationContext.addApplicationListener((ApplicationListener)this);
    }

    public void onApplicationEvent(WebServerInitializedEvent event) {
        String propertyName = "local." + this.getName(event.getApplicationContext()) + ".port";
        this.setPortProperty(event.getApplicationContext(), propertyName, event.getWebServer().getPort());
    }

    private String getName(WebServerApplicationContext context) {
        String name = context.getServerNamespace();
        return StringUtils.hasText((String)name) ? name : "server";
    }

    private void setPortProperty(ApplicationContext context, String propertyName, int port) {
        if (context instanceof ConfigurableApplicationContext) {
            this.setPortProperty(((ConfigurableApplicationContext)context).getEnvironment(), propertyName, port);
        }
        if (context.getParent() != null) {
            this.setPortProperty(context.getParent(), propertyName, port);
        }
    }

    private void setPortProperty(ConfigurableEnvironment environment, String propertyName, int port) {
        MutablePropertySources sources = environment.getPropertySources();
        PropertySource source = sources.get("server.ports");
        if (source == null) {
            source = new MapPropertySource("server.ports", new HashMap());
            sources.addFirst(source);
        }
        ((Map)source.getSource()).put(propertyName, port);
    }
}

