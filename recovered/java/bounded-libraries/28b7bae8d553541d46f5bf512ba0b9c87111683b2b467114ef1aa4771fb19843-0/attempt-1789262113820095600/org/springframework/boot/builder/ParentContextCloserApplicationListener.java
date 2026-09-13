/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.BeansException
 *  org.springframework.context.ApplicationContext
 *  org.springframework.context.ApplicationContextAware
 *  org.springframework.context.ApplicationListener
 *  org.springframework.context.ConfigurableApplicationContext
 *  org.springframework.context.event.ContextClosedEvent
 *  org.springframework.core.Ordered
 *  org.springframework.util.ObjectUtils
 */
package org.springframework.boot.builder;

import java.lang.ref.WeakReference;
import org.springframework.beans.BeansException;
import org.springframework.boot.builder.ParentContextApplicationContextInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.core.Ordered;
import org.springframework.util.ObjectUtils;

public class ParentContextCloserApplicationListener
implements ApplicationListener<ParentContextApplicationContextInitializer.ParentContextAvailableEvent>,
ApplicationContextAware,
Ordered {
    private int order = 0x7FFFFFF5;
    private ApplicationContext context;

    public int getOrder() {
        return this.order;
    }

    public void setApplicationContext(ApplicationContext context) throws BeansException {
        this.context = context;
    }

    public void onApplicationEvent(ParentContextApplicationContextInitializer.ParentContextAvailableEvent event) {
        this.maybeInstallListenerInParent(event.getApplicationContext());
    }

    private void maybeInstallListenerInParent(ConfigurableApplicationContext child) {
        if (child == this.context && child.getParent() instanceof ConfigurableApplicationContext) {
            ConfigurableApplicationContext parent = (ConfigurableApplicationContext)child.getParent();
            parent.addApplicationListener((ApplicationListener)this.createContextCloserListener(child));
        }
    }

    protected ContextCloserListener createContextCloserListener(ConfigurableApplicationContext child) {
        return new ContextCloserListener(child);
    }

    protected static class ContextCloserListener
    implements ApplicationListener<ContextClosedEvent> {
        private WeakReference<ConfigurableApplicationContext> childContext;

        public ContextCloserListener(ConfigurableApplicationContext childContext) {
            this.childContext = new WeakReference<ConfigurableApplicationContext>(childContext);
        }

        public void onApplicationEvent(ContextClosedEvent event) {
            ConfigurableApplicationContext context = (ConfigurableApplicationContext)this.childContext.get();
            if (context != null && event.getApplicationContext() == context.getParent() && context.isActive()) {
                context.close();
            }
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (obj instanceof ContextCloserListener) {
                ContextCloserListener other = (ContextCloserListener)obj;
                return ObjectUtils.nullSafeEquals(this.childContext.get(), other.childContext.get());
            }
            return super.equals(obj);
        }

        public int hashCode() {
            return ObjectUtils.nullSafeHashCode(this.childContext.get());
        }
    }
}

