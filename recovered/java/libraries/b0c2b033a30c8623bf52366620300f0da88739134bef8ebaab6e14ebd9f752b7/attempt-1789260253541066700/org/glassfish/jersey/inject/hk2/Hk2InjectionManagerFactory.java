/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.inject.hk2;

import java.security.AccessController;
import javax.annotation.Priority;
import org.glassfish.jersey.inject.hk2.DelayedHk2InjectionManager;
import org.glassfish.jersey.inject.hk2.ImmediateHk2InjectionManager;
import org.glassfish.jersey.internal.inject.Binding;
import org.glassfish.jersey.internal.inject.Bindings;
import org.glassfish.jersey.internal.inject.InjectionManager;
import org.glassfish.jersey.internal.inject.InjectionManagerFactory;
import org.glassfish.jersey.internal.util.PropertiesHelper;

@Priority(value=10)
public class Hk2InjectionManagerFactory
implements InjectionManagerFactory {
    public static final String HK2_INJECTION_MANAGER_STRATEGY = "org.glassfish.jersey.hk2.injection.manager.strategy";

    @Override
    public InjectionManager create(Object parent) {
        return this.initInjectionManager(Hk2InjectionManagerFactory.getStrategy().createInjectionManager(parent));
    }

    public static boolean isImmediateStrategy() {
        return Hk2InjectionManagerFactory.getStrategy() == Hk2InjectionManagerStrategy.IMMEDIATE;
    }

    private static Hk2InjectionManagerStrategy getStrategy() {
        String value = AccessController.doPrivileged(PropertiesHelper.getSystemProperty(HK2_INJECTION_MANAGER_STRATEGY));
        if (value == null || value.isEmpty()) {
            return Hk2InjectionManagerStrategy.IMMEDIATE;
        }
        if ("immediate".equalsIgnoreCase(value)) {
            return Hk2InjectionManagerStrategy.IMMEDIATE;
        }
        if ("delayed".equalsIgnoreCase(value)) {
            return Hk2InjectionManagerStrategy.DELAYED;
        }
        throw new IllegalStateException("Illegal value of org.glassfish.jersey.hk2.injection.manager.strategy. Expected \"immediate\" or \"delayed\", the actual value is: " + value);
    }

    private InjectionManager initInjectionManager(InjectionManager injectionManager) {
        injectionManager.register((Binding)Bindings.service(injectionManager).to(InjectionManager.class));
        return injectionManager;
    }

    private static enum Hk2InjectionManagerStrategy {
        IMMEDIATE{

            @Override
            InjectionManager createInjectionManager(Object parent) {
                return new ImmediateHk2InjectionManager(parent);
            }
        }
        ,
        DELAYED{

            @Override
            InjectionManager createInjectionManager(Object parent) {
                return new DelayedHk2InjectionManager(parent);
            }
        };


        abstract InjectionManager createInjectionManager(Object var1);
    }
}

