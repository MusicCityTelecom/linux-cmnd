/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.beans.BeansException
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.beans.factory.BeanFactoryAware
 *  org.springframework.beans.factory.BeanFactoryUtils
 *  org.springframework.beans.factory.ListableBeanFactory
 *  org.springframework.beans.factory.config.DestructionAwareBeanPostProcessor
 *  org.springframework.jmx.export.annotation.ManagedAttribute
 *  org.springframework.jmx.export.annotation.ManagedOperation
 *  org.springframework.jmx.export.annotation.ManagedResource
 *  org.springframework.util.Assert
 *  org.springframework.util.PatternMatchUtils
 *  org.springframework.util.StringUtils
 */
package org.springframework.integration.history;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.config.DestructionAwareBeanPostProcessor;
import org.springframework.integration.support.management.IntegrationManagedResource;
import org.springframework.integration.support.management.ManageableSmartLifecycle;
import org.springframework.integration.support.management.TrackableComponent;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.util.Assert;
import org.springframework.util.PatternMatchUtils;
import org.springframework.util.StringUtils;

@ManagedResource
@IntegrationManagedResource
public class MessageHistoryConfigurer
implements ManageableSmartLifecycle,
BeanFactoryAware,
DestructionAwareBeanPostProcessor {
    private static final Log LOGGER = LogFactory.getLog(MessageHistoryConfigurer.class);
    private final Set<TrackableComponent> currentlyTrackedComponents = ConcurrentHashMap.newKeySet();
    private String[] componentNamePatterns = new String[]{"*"};
    private ListableBeanFactory beanFactory;
    private boolean autoStartup = true;
    private int phase = Integer.MIN_VALUE;
    private volatile boolean running;

    public void setComponentNamePatterns(String[] componentNamePatterns) {
        Assert.notEmpty((Object[])componentNamePatterns, (String)"componentNamePatterns must not be empty");
        Assert.state((!this.running ? 1 : 0) != 0, (String)"'componentNamePatterns' cannot be changed without invoking stop() first");
        Object[] trimmedAndSortedComponentNamePatterns = (String[])componentNamePatterns.clone();
        for (int i = 0; i < componentNamePatterns.length; ++i) {
            trimmedAndSortedComponentNamePatterns[i] = ((String)trimmedAndSortedComponentNamePatterns[i]).trim();
        }
        Arrays.sort(trimmedAndSortedComponentNamePatterns);
        this.componentNamePatterns = trimmedAndSortedComponentNamePatterns;
    }

    @ManagedAttribute(description="comma-delimited list of patterns; must invoke stop() before changing.")
    public void setComponentNamePatternsString(String componentNamePatterns) {
        this.setComponentNamePatterns(StringUtils.delimitedListToStringArray((String)componentNamePatterns, (String)",", (String)" "));
    }

    @ManagedAttribute
    public String getComponentNamePatternsString() {
        return StringUtils.arrayToCommaDelimitedString((Object[])this.componentNamePatterns);
    }

    public void setComponentNamePatternsSet(Set<String> componentNamePatternsSet) {
        Assert.notNull(componentNamePatternsSet, (String)"'componentNamePatternsSet' must not be null");
        Assert.state((!this.running ? 1 : 0) != 0, (String)"'componentNamePatternsSet' cannot be changed without invoking stop() first");
        String patterns = String.join((CharSequence)",", componentNamePatternsSet);
        this.componentNamePatterns = StringUtils.delimitedListToStringArray((String)patterns, (String)",", (String)" ");
    }

    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        Assert.isInstanceOf(ListableBeanFactory.class, (Object)beanFactory, (String)"The provided 'beanFactory' must be of 'ListableBeanFactory' type.");
        this.beanFactory = (ListableBeanFactory)beanFactory;
    }

    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof TrackableComponent && this.running) {
            this.trackComponentIfAny((TrackableComponent)bean);
        }
        return bean;
    }

    private void trackComponentIfAny(TrackableComponent component) {
        String componentName = component.getComponentName();
        boolean shouldTrack = PatternMatchUtils.simpleMatch((String[])this.componentNamePatterns, (String)componentName);
        component.setShouldTrack(shouldTrack);
        if (shouldTrack) {
            this.currentlyTrackedComponents.add(component);
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info((Object)("Enabling MessageHistory tracking for component '" + componentName + "'"));
            }
        }
    }

    public boolean requiresDestruction(Object bean) {
        return bean instanceof TrackableComponent;
    }

    public void postProcessBeforeDestruction(Object bean, String beanName) throws BeansException {
        this.currentlyTrackedComponents.remove(bean);
    }

    @Override
    public boolean isRunning() {
        return this.running;
    }

    public void setAutoStartup(boolean autoStartup) {
        this.autoStartup = autoStartup;
    }

    public boolean isAutoStartup() {
        return this.autoStartup;
    }

    public void setPhase(int phase) {
        this.phase = phase;
    }

    public int getPhase() {
        return this.phase;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    @ManagedOperation
    public void start() {
        Set<TrackableComponent> set = this.currentlyTrackedComponents;
        synchronized (set) {
            if (!this.running) {
                for (TrackableComponent component : MessageHistoryConfigurer.getTrackableComponents(this.beanFactory)) {
                    this.trackComponentIfAny(component);
                    this.running = true;
                }
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    @ManagedOperation
    public void stop() {
        Set<TrackableComponent> set = this.currentlyTrackedComponents;
        synchronized (set) {
            if (this.running) {
                this.currentlyTrackedComponents.forEach(component -> {
                    component.setShouldTrack(false);
                    if (LOGGER.isInfoEnabled()) {
                        LOGGER.info((Object)("Disabling MessageHistory tracking for component '" + component.getComponentName() + "'"));
                    }
                });
                this.currentlyTrackedComponents.clear();
                this.running = false;
            }
        }
    }

    private static Collection<TrackableComponent> getTrackableComponents(ListableBeanFactory beanFactory) {
        return BeanFactoryUtils.beansOfTypeIncludingAncestors((ListableBeanFactory)beanFactory, TrackableComponent.class).values();
    }
}

