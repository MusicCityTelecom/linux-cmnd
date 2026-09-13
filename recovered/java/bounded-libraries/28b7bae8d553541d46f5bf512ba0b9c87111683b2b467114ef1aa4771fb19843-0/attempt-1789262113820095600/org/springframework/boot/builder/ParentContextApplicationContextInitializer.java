/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.context.ApplicationContext
 *  org.springframework.context.ApplicationContextInitializer
 *  org.springframework.context.ApplicationEvent
 *  org.springframework.context.ApplicationListener
 *  org.springframework.context.ConfigurableApplicationContext
 *  org.springframework.context.event.ContextRefreshedEvent
 *  org.springframework.core.Ordered
 */
package org.springframework.boot.builder;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.Ordered;

public class ParentContextApplicationContextInitializer
implements ApplicationContextInitializer<ConfigurableApplicationContext>,
Ordered {
    private int order = Integer.MIN_VALUE;
    private final ApplicationContext parent;

    public ParentContextApplicationContextInitializer(ApplicationContext parent) {
        this.parent = parent;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getOrder() {
        return this.order;
    }

    public void initialize(ConfigurableApplicationContext applicationContext) {
        if (applicationContext != this.parent) {
            applicationContext.setParent(this.parent);
            applicationContext.addApplicationListener((ApplicationListener)EventPublisher.INSTANCE);
        }
    }

    public static class ParentContextAvailableEvent
    extends ApplicationEvent {
        public ParentContextAvailableEvent(ConfigurableApplicationContext applicationContext) {
            super((Object)applicationContext);
        }

        public ConfigurableApplicationContext getApplicationContext() {
            return (ConfigurableApplicationContext)this.getSource();
        }
    }

    private static class EventPublisher
    implements ApplicationListener<ContextRefreshedEvent>,
    Ordered {
        private static final EventPublisher INSTANCE = new EventPublisher();

        private EventPublisher() {
        }

        public int getOrder() {
            return Integer.MIN_VALUE;
        }

        public void onApplicationEvent(ContextRefreshedEvent event) {
            ApplicationContext context = event.getApplicationContext();
            if (context instanceof ConfigurableApplicationContext && context == event.getSource()) {
                context.publishEvent((ApplicationEvent)new ParentContextAvailableEvent((ConfigurableApplicationContext)context));
            }
        }
    }
}

