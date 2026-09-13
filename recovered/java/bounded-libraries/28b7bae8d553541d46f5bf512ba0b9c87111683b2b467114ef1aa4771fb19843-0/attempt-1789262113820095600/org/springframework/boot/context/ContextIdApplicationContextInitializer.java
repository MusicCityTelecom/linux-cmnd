/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.context.ApplicationContext
 *  org.springframework.context.ApplicationContextInitializer
 *  org.springframework.context.ConfigurableApplicationContext
 *  org.springframework.core.Ordered
 *  org.springframework.core.env.ConfigurableEnvironment
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.context;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.util.StringUtils;

public class ContextIdApplicationContextInitializer
implements ApplicationContextInitializer<ConfigurableApplicationContext>,
Ordered {
    private int order = 0x7FFFFFF5;

    public void setOrder(int order) {
        this.order = order;
    }

    public int getOrder() {
        return this.order;
    }

    public void initialize(ConfigurableApplicationContext applicationContext) {
        ContextId contextId = this.getContextId(applicationContext);
        applicationContext.setId(contextId.getId());
        applicationContext.getBeanFactory().registerSingleton(ContextId.class.getName(), (Object)contextId);
    }

    private ContextId getContextId(ConfigurableApplicationContext applicationContext) {
        ApplicationContext parent = applicationContext.getParent();
        if (parent != null && parent.containsBean(ContextId.class.getName())) {
            return ((ContextId)parent.getBean(ContextId.class)).createChildId();
        }
        return new ContextId(this.getApplicationId(applicationContext.getEnvironment()));
    }

    private String getApplicationId(ConfigurableEnvironment environment) {
        String name = environment.getProperty("spring.application.name");
        return StringUtils.hasText((String)name) ? name : "application";
    }

    static class ContextId {
        private final AtomicLong children = new AtomicLong();
        private final String id;

        ContextId(String id) {
            this.id = id;
        }

        ContextId createChildId() {
            return new ContextId(this.id + "-" + this.children.incrementAndGet());
        }

        String getId() {
            return this.id;
        }
    }
}

