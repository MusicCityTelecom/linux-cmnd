/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.beans.BeansException
 *  org.springframework.beans.factory.DisposableBean
 *  org.springframework.beans.factory.InitializingBean
 *  org.springframework.context.ApplicationContext
 *  org.springframework.context.ApplicationContextAware
 *  org.springframework.context.ApplicationEvent
 *  org.springframework.context.ConfigurableApplicationContext
 *  org.springframework.context.EnvironmentAware
 *  org.springframework.context.event.GenericApplicationListener
 *  org.springframework.core.ResolvableType
 *  org.springframework.core.env.Environment
 *  org.springframework.core.env.StandardEnvironment
 *  org.springframework.util.Assert
 */
package org.springframework.boot.admin;

import java.lang.management.ManagementFactory;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.admin.SpringApplicationAdminMXBean;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.event.GenericApplicationListener;
import org.springframework.core.ResolvableType;
import org.springframework.core.env.Environment;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.util.Assert;

public class SpringApplicationAdminMXBeanRegistrar
implements ApplicationContextAware,
GenericApplicationListener,
EnvironmentAware,
InitializingBean,
DisposableBean {
    private static final Log logger = LogFactory.getLog(SpringApplicationAdmin.class);
    private ConfigurableApplicationContext applicationContext;
    private Environment environment = new StandardEnvironment();
    private final ObjectName objectName;
    private boolean ready = false;
    private boolean embeddedWebApplication = false;

    public SpringApplicationAdminMXBeanRegistrar(String name) throws MalformedObjectNameException {
        this.objectName = new ObjectName(name);
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Assert.state((boolean)(applicationContext instanceof ConfigurableApplicationContext), (String)"ApplicationContext does not implement ConfigurableApplicationContext");
        this.applicationContext = (ConfigurableApplicationContext)applicationContext;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public boolean supportsEventType(ResolvableType eventType) {
        Class type = eventType.getRawClass();
        if (type == null) {
            return false;
        }
        return ApplicationReadyEvent.class.isAssignableFrom(type) || WebServerInitializedEvent.class.isAssignableFrom(type);
    }

    public boolean supportsSourceType(Class<?> sourceType) {
        return true;
    }

    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof ApplicationReadyEvent) {
            this.onApplicationReadyEvent((ApplicationReadyEvent)event);
        }
        if (event instanceof WebServerInitializedEvent) {
            this.onWebServerInitializedEvent((WebServerInitializedEvent)event);
        }
    }

    public int getOrder() {
        return Integer.MIN_VALUE;
    }

    void onApplicationReadyEvent(ApplicationReadyEvent event) {
        if (this.applicationContext.equals(event.getApplicationContext())) {
            this.ready = true;
        }
    }

    void onWebServerInitializedEvent(WebServerInitializedEvent event) {
        if (this.applicationContext.equals(event.getApplicationContext())) {
            this.embeddedWebApplication = true;
        }
    }

    public void afterPropertiesSet() throws Exception {
        MBeanServer server = ManagementFactory.getPlatformMBeanServer();
        server.registerMBean(new SpringApplicationAdmin(), this.objectName);
        if (logger.isDebugEnabled()) {
            logger.debug((Object)("Application Admin MBean registered with name '" + this.objectName + "'"));
        }
    }

    public void destroy() throws Exception {
        ManagementFactory.getPlatformMBeanServer().unregisterMBean(this.objectName);
    }

    private class SpringApplicationAdmin
    implements SpringApplicationAdminMXBean {
        private SpringApplicationAdmin() {
        }

        @Override
        public boolean isReady() {
            return SpringApplicationAdminMXBeanRegistrar.this.ready;
        }

        @Override
        public boolean isEmbeddedWebApplication() {
            return SpringApplicationAdminMXBeanRegistrar.this.embeddedWebApplication;
        }

        @Override
        public String getProperty(String key) {
            return SpringApplicationAdminMXBeanRegistrar.this.environment.getProperty(key);
        }

        @Override
        public void shutdown() {
            logger.info((Object)"Application shutdown requested.");
            SpringApplicationAdminMXBeanRegistrar.this.applicationContext.close();
        }
    }
}

