/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server;

import java.lang.reflect.Type;
import javax.ws.rs.RuntimeType;
import javax.ws.rs.core.Configuration;
import org.glassfish.jersey.internal.AbstractServiceFinderConfigurator;
import org.glassfish.jersey.internal.BootstrapBag;
import org.glassfish.jersey.internal.inject.Binding;
import org.glassfish.jersey.internal.inject.Bindings;
import org.glassfish.jersey.internal.inject.InjectionManager;
import org.glassfish.jersey.internal.spi.AutoDiscoverable;
import org.glassfish.jersey.server.spi.ContainerProvider;

class ContainerProviderConfigurator
extends AbstractServiceFinderConfigurator<ContainerProvider> {
    ContainerProviderConfigurator(RuntimeType runtimeType) {
        super(ContainerProvider.class, runtimeType);
    }

    @Override
    public void init(InjectionManager injectionManager, BootstrapBag bootstrapBag) {
        Configuration configuration = bootstrapBag.getConfiguration();
        this.loadImplementations(configuration.getProperties()).forEach(implClass -> injectionManager.register((Binding)Bindings.service(implClass).to((Type)((Object)AutoDiscoverable.class))));
    }
}

