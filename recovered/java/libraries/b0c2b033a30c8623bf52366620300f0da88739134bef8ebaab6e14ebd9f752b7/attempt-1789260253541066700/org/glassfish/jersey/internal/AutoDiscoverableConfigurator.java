/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.internal;

import java.util.List;
import java.util.stream.Collectors;
import javax.ws.rs.RuntimeType;
import javax.ws.rs.core.Configuration;
import org.glassfish.jersey.internal.AbstractServiceFinderConfigurator;
import org.glassfish.jersey.internal.BootstrapBag;
import org.glassfish.jersey.internal.inject.Binding;
import org.glassfish.jersey.internal.inject.Bindings;
import org.glassfish.jersey.internal.inject.InjectionManager;
import org.glassfish.jersey.internal.spi.AutoDiscoverable;

public class AutoDiscoverableConfigurator
extends AbstractServiceFinderConfigurator<AutoDiscoverable> {
    public AutoDiscoverableConfigurator(RuntimeType runtimeType) {
        super(AutoDiscoverable.class, runtimeType);
    }

    @Override
    public void init(InjectionManager injectionManager, BootstrapBag bootstrapBag) {
        Configuration configuration = bootstrapBag.getConfiguration();
        List<AutoDiscoverable> autoDiscoverables = this.loadImplementations(configuration.getProperties()).stream().peek(implClass -> injectionManager.register((Binding)Bindings.service(implClass).to(AutoDiscoverable.class))).map(injectionManager::createAndInitialize).collect(Collectors.toList());
        bootstrapBag.setAutoDiscoverables(autoDiscoverables);
    }
}

