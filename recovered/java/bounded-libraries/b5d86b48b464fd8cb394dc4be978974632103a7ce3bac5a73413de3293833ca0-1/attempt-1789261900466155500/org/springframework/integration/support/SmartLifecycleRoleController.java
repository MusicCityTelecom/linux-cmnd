/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.beans.BeansException
 *  org.springframework.beans.factory.NoSuchBeanDefinitionException
 *  org.springframework.context.ApplicationContext
 *  org.springframework.context.ApplicationContextAware
 *  org.springframework.context.ApplicationListener
 *  org.springframework.context.Lifecycle
 *  org.springframework.context.Phased
 *  org.springframework.context.SmartLifecycle
 *  org.springframework.util.Assert
 *  org.springframework.util.CollectionUtils
 *  org.springframework.util.LinkedMultiValueMap
 *  org.springframework.util.MultiValueMap
 */
package org.springframework.integration.support;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.Lifecycle;
import org.springframework.context.Phased;
import org.springframework.context.SmartLifecycle;
import org.springframework.integration.leader.event.AbstractLeaderEvent;
import org.springframework.integration.leader.event.OnGrantedEvent;
import org.springframework.integration.leader.event.OnRevokedEvent;
import org.springframework.integration.support.context.NamedComponent;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class SmartLifecycleRoleController
implements ApplicationListener<AbstractLeaderEvent>,
ApplicationContextAware {
    private static final Log LOGGER = LogFactory.getLog(SmartLifecycleRoleController.class);
    private static final String IN_ROLE = " in role ";
    private final MultiValueMap<String, SmartLifecycle> lifecycles = new LinkedMultiValueMap();
    private final MultiValueMap<String, String> lazyLifecycles = new LinkedMultiValueMap();
    private ApplicationContext applicationContext;

    public SmartLifecycleRoleController() {
    }

    public SmartLifecycleRoleController(List<String> roles, List<SmartLifecycle> lifecycles) {
        Assert.notNull(roles, (String)"'roles' cannot be null");
        Assert.notNull(lifecycles, (String)"'lifecycles' cannot be null");
        Assert.isTrue((roles.size() == lifecycles.size() ? 1 : 0) != 0, (String)"'roles' and 'lifecycles' must be the same length");
        Iterator<SmartLifecycle> iterator = lifecycles.iterator();
        for (String role : roles) {
            SmartLifecycle lifecycle = iterator.next();
            this.addLifecycleToRole(role, lifecycle);
        }
    }

    public SmartLifecycleRoleController(MultiValueMap<String, SmartLifecycle> lifecycles) {
        lifecycles.forEach((role, values) -> values.forEach(lifecycle -> this.addLifecycleToRole((String)role, (SmartLifecycle)lifecycle)));
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public final void addLifecycleToRole(String role, SmartLifecycle lifecycle) {
        List componentsInRole = (List)this.lifecycles.get((Object)role);
        if (CollectionUtils.isEmpty((Collection)componentsInRole)) {
            this.lifecycles.add((Object)role, (Object)lifecycle);
        } else {
            componentsInRole.stream().filter(e -> e == lifecycle || e instanceof NamedComponent && lifecycle instanceof NamedComponent && ((NamedComponent)e).getComponentName().equals(((NamedComponent)lifecycle).getComponentName())).findFirst().ifPresent(e -> {
                throw new IllegalArgumentException("Cannot add the Lifecycle '" + (lifecycle instanceof NamedComponent ? ((NamedComponent)lifecycle).getComponentName() : lifecycle) + "' to the role '" + role + "' because a Lifecycle with the name '" + (e instanceof NamedComponent ? ((NamedComponent)e).getComponentName() : e) + "' is already present.");
            });
            componentsInRole.add(lifecycle);
        }
    }

    public final void addLifecycleToRole(String role, String lifecycleBeanName) {
        Assert.state((this.applicationContext != null ? 1 : 0) != 0, (String)"An application context is required to use this method");
        this.lazyLifecycles.add((Object)role, (Object)lifecycleBeanName);
    }

    public void addLifecyclesToRole(String role, List<String> lifecycleBeanNames) {
        Assert.state((this.applicationContext != null ? 1 : 0) != 0, (String)"An application context is required to use this method");
        lifecycleBeanNames.forEach(lifecycleBeanName -> this.lazyLifecycles.add((Object)role, lifecycleBeanName));
    }

    public void startLifecyclesInRole(String role) {
        ArrayList<SmartLifecycle> componentsInRole;
        if (this.lazyLifecycles.size() > 0) {
            this.addLazyLifecycles();
        }
        if ((componentsInRole = (ArrayList<SmartLifecycle>)this.lifecycles.get((Object)role)) != null) {
            componentsInRole = new ArrayList<SmartLifecycle>(componentsInRole);
            componentsInRole.sort(Comparator.comparingInt(Phased::getPhase));
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug((Object)("Starting " + componentsInRole + IN_ROLE + role));
            }
            componentsInRole.forEach(lifecycle -> {
                try {
                    lifecycle.start();
                }
                catch (Exception e) {
                    LOGGER.error((Object)("Failed to start " + lifecycle + IN_ROLE + role), (Throwable)e);
                }
            });
        } else if (LOGGER.isDebugEnabled()) {
            LOGGER.debug((Object)("No components in role " + role + ". Nothing to start"));
        }
    }

    public void stopLifecyclesInRole(String role) {
        ArrayList componentsInRole;
        if (this.lazyLifecycles.size() > 0) {
            this.addLazyLifecycles();
        }
        if ((componentsInRole = (ArrayList)this.lifecycles.get((Object)role)) != null) {
            componentsInRole = new ArrayList(componentsInRole);
            componentsInRole.sort((o1, o2) -> Integer.compare(o2.getPhase(), o1.getPhase()));
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug((Object)("Stopping " + componentsInRole + IN_ROLE + role));
            }
            componentsInRole.forEach(lifecycle -> {
                try {
                    lifecycle.stop();
                }
                catch (Exception e) {
                    LOGGER.error((Object)("Failed to stop " + lifecycle + IN_ROLE + role), (Throwable)e);
                }
            });
        } else if (LOGGER.isDebugEnabled()) {
            LOGGER.debug((Object)("No components in role " + role + ". Nothing to stop"));
        }
    }

    public Collection<String> getRoles() {
        if (this.lazyLifecycles.size() > 0) {
            this.addLazyLifecycles();
        }
        return Collections.unmodifiableCollection(this.lifecycles.keySet());
    }

    public boolean allEndpointsRunning(String role) {
        Map<String, Boolean> status = this.getEndpointsRunningStatus(role);
        return !status.isEmpty() && status.values().stream().allMatch(b -> b);
    }

    public boolean noEndpointsRunning(String role) {
        Map<String, Boolean> status = this.getEndpointsRunningStatus(role);
        return status.isEmpty() || status.values().stream().noneMatch(b -> b);
    }

    public Map<String, Boolean> getEndpointsRunningStatus(String role) {
        if (this.lazyLifecycles.size() > 0) {
            this.addLazyLifecycles();
        }
        if (!this.lifecycles.containsKey((Object)role)) {
            return Collections.emptyMap();
        }
        AtomicInteger index = new AtomicInteger();
        return ((List)this.lifecycles.get((Object)role)).stream().collect(Collectors.toMap(e -> e instanceof NamedComponent ? ((NamedComponent)e).getComponentName() : e.getClass().getSimpleName() + "#" + index.getAndIncrement(), Lifecycle::isRunning));
    }

    private synchronized void addLazyLifecycles() {
        this.lazyLifecycles.forEach(this::doAddLifecyclesToRole);
        this.lazyLifecycles.clear();
    }

    private void doAddLifecyclesToRole(String role, List<String> lifecycleBeanNames) {
        lifecycleBeanNames.forEach(lifecycleBeanName -> {
            try {
                SmartLifecycle lifecycle = (SmartLifecycle)this.applicationContext.getBean(lifecycleBeanName, SmartLifecycle.class);
                this.addLifecycleToRole(role, lifecycle);
            }
            catch (NoSuchBeanDefinitionException e) {
                LOGGER.warn((Object)("Skipped; no such bean: " + lifecycleBeanName));
            }
        });
    }

    public void onApplicationEvent(AbstractLeaderEvent event) {
        if (event instanceof OnGrantedEvent) {
            this.startLifecyclesInRole(event.getRole());
        } else if (event instanceof OnRevokedEvent) {
            this.stopLifecyclesInRole(event.getRole());
        }
    }

    public boolean removeLifecycle(SmartLifecycle lifecycle) {
        List componentsInRole;
        boolean removed = false;
        Iterator iterator = this.lifecycles.values().iterator();
        while (iterator.hasNext() && !(removed = (componentsInRole = (List)iterator.next()).removeIf(Predicate.isEqual(lifecycle)))) {
        }
        if (removed) {
            this.lifecycles.entrySet().removeIf(entry -> ((List)entry.getValue()).isEmpty());
        }
        return removed;
    }
}

