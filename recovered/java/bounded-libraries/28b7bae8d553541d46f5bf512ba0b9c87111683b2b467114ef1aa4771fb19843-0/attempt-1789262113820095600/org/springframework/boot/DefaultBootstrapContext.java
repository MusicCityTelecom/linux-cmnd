/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.context.ApplicationEvent
 *  org.springframework.context.ApplicationListener
 *  org.springframework.context.ConfigurableApplicationContext
 *  org.springframework.context.event.ApplicationEventMulticaster
 *  org.springframework.context.event.SimpleApplicationEventMulticaster
 *  org.springframework.util.Assert
 */
package org.springframework.boot;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import org.springframework.boot.BootstrapContextClosedEvent;
import org.springframework.boot.BootstrapRegistry;
import org.springframework.boot.ConfigurableBootstrapContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.util.Assert;

public class DefaultBootstrapContext
implements ConfigurableBootstrapContext {
    private final Map<Class<?>, BootstrapRegistry.InstanceSupplier<?>> instanceSuppliers = new HashMap();
    private final Map<Class<?>, Object> instances = new HashMap();
    private final ApplicationEventMulticaster events = new SimpleApplicationEventMulticaster();

    @Override
    public <T> void register(Class<T> type, BootstrapRegistry.InstanceSupplier<T> instanceSupplier) {
        this.register(type, instanceSupplier, true);
    }

    @Override
    public <T> void registerIfAbsent(Class<T> type, BootstrapRegistry.InstanceSupplier<T> instanceSupplier) {
        this.register(type, instanceSupplier, false);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private <T> void register(Class<T> type, BootstrapRegistry.InstanceSupplier<T> instanceSupplier, boolean replaceExisting) {
        Assert.notNull(type, (String)"Type must not be null");
        Assert.notNull(instanceSupplier, (String)"InstanceSupplier must not be null");
        Map<Class<?>, BootstrapRegistry.InstanceSupplier<?>> map = this.instanceSuppliers;
        synchronized (map) {
            boolean alreadyRegistered = this.instanceSuppliers.containsKey(type);
            if (replaceExisting || !alreadyRegistered) {
                Assert.state((!this.instances.containsKey(type) ? 1 : 0) != 0, () -> type.getName() + " has already been created");
                this.instanceSuppliers.put(type, instanceSupplier);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public <T> boolean isRegistered(Class<T> type) {
        Map<Class<?>, BootstrapRegistry.InstanceSupplier<?>> map = this.instanceSuppliers;
        synchronized (map) {
            return this.instanceSuppliers.containsKey(type);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public <T> BootstrapRegistry.InstanceSupplier<T> getRegisteredInstanceSupplier(Class<T> type) {
        Map<Class<?>, BootstrapRegistry.InstanceSupplier<?>> map = this.instanceSuppliers;
        synchronized (map) {
            return this.instanceSuppliers.get(type);
        }
    }

    @Override
    public void addCloseListener(ApplicationListener<BootstrapContextClosedEvent> listener) {
        this.events.addApplicationListener(listener);
    }

    @Override
    public <T> T get(Class<T> type) throws IllegalStateException {
        return this.getOrElseThrow(type, () -> new IllegalStateException(type.getName() + " has not been registered"));
    }

    @Override
    public <T> T getOrElse(Class<T> type, T other) {
        return (T)this.getOrElseSupply(type, () -> other);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public <T> T getOrElseSupply(Class<T> type, Supplier<T> other) {
        Map<Class<?>, BootstrapRegistry.InstanceSupplier<?>> map = this.instanceSuppliers;
        synchronized (map) {
            BootstrapRegistry.InstanceSupplier<?> instanceSupplier = this.instanceSuppliers.get(type);
            return instanceSupplier != null ? this.getInstance(type, instanceSupplier) : other.get();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public <T, X extends Throwable> T getOrElseThrow(Class<T> type, Supplier<? extends X> exceptionSupplier) throws X {
        Map<Class<?>, BootstrapRegistry.InstanceSupplier<?>> map = this.instanceSuppliers;
        synchronized (map) {
            BootstrapRegistry.InstanceSupplier<?> instanceSupplier = this.instanceSuppliers.get(type);
            if (instanceSupplier == null) {
                throw (Throwable)exceptionSupplier.get();
            }
            return this.getInstance(type, instanceSupplier);
        }
    }

    private <T> T getInstance(Class<T> type, BootstrapRegistry.InstanceSupplier<?> instanceSupplier) {
        Object instance = this.instances.get(type);
        if (instance == null) {
            instance = instanceSupplier.get(this);
            if (instanceSupplier.getScope() == BootstrapRegistry.Scope.SINGLETON) {
                this.instances.put(type, instance);
            }
        }
        return (T)instance;
    }

    public void close(ConfigurableApplicationContext applicationContext) {
        this.events.multicastEvent((ApplicationEvent)new BootstrapContextClosedEvent(this, applicationContext));
    }
}

