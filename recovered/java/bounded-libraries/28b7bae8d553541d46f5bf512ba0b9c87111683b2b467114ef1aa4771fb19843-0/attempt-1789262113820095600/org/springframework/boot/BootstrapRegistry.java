/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.context.ApplicationListener
 *  org.springframework.util.Assert
 */
package org.springframework.boot;

import java.util.function.Supplier;
import org.springframework.boot.BootstrapContext;
import org.springframework.boot.BootstrapContextClosedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.util.Assert;

public interface BootstrapRegistry {
    public <T> void register(Class<T> var1, InstanceSupplier<T> var2);

    public <T> void registerIfAbsent(Class<T> var1, InstanceSupplier<T> var2);

    public <T> boolean isRegistered(Class<T> var1);

    public <T> InstanceSupplier<T> getRegisteredInstanceSupplier(Class<T> var1);

    public void addCloseListener(ApplicationListener<BootstrapContextClosedEvent> var1);

    public static enum Scope {
        SINGLETON,
        PROTOTYPE;

    }

    @FunctionalInterface
    public static interface InstanceSupplier<T> {
        public T get(BootstrapContext var1);

        default public Scope getScope() {
            return Scope.SINGLETON;
        }

        default public InstanceSupplier<T> withScope(final Scope scope) {
            Assert.notNull((Object)((Object)scope), (String)"Scope must not be null");
            final InstanceSupplier parent = this;
            return new InstanceSupplier<T>(){

                @Override
                public T get(BootstrapContext context) {
                    return parent.get(context);
                }

                @Override
                public Scope getScope() {
                    return scope;
                }
            };
        }

        public static <T> InstanceSupplier<T> of(T instance) {
            return registry -> instance;
        }

        public static <T> InstanceSupplier<T> from(Supplier<T> supplier) {
            return registry -> supplier != null ? supplier.get() : null;
        }
    }
}

