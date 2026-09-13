/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server;

import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.glassfish.jersey.internal.BootstrapBag;
import org.glassfish.jersey.internal.BootstrapConfigurator;
import org.glassfish.jersey.internal.ServiceConfigurationError;
import org.glassfish.jersey.internal.ServiceFinder;
import org.glassfish.jersey.internal.inject.InjectionManager;
import org.glassfish.jersey.internal.util.collection.LazyValue;
import org.glassfish.jersey.internal.util.collection.Values;
import org.glassfish.jersey.model.internal.RankedComparator;
import org.glassfish.jersey.model.internal.RankedProvider;
import org.glassfish.jersey.server.ServerBootstrapBag;
import org.glassfish.jersey.server.spi.ComponentProvider;

class ComponentProviderConfigurator
implements BootstrapConfigurator {
    private static final Comparator<RankedProvider<ComponentProvider>> RANKED_COMPARATOR = new RankedComparator<ComponentProvider>(RankedComparator.Order.DESCENDING);

    ComponentProviderConfigurator() {
    }

    @Override
    public void init(InjectionManager injectionManager, BootstrapBag bootstrapBag) {
        ServerBootstrapBag serverBag = (ServerBootstrapBag)bootstrapBag;
        LazyValue<Collection<ComponentProvider>> componentProviders = Values.lazy(() -> ComponentProviderConfigurator.getRankedComponentProviders().stream().map(RankedProvider::getProvider).peek(provider -> provider.initialize(injectionManager)).collect(Collectors.toList()));
        serverBag.setComponentProviders(componentProviders);
    }

    @Override
    public void postInit(InjectionManager injectionManager, BootstrapBag bootstrapBag) {
        ServerBootstrapBag serverBag = (ServerBootstrapBag)bootstrapBag;
        ((Collection)serverBag.getComponentProviders().get()).forEach(ComponentProvider::done);
    }

    private static Collection<RankedProvider<ComponentProvider>> getRankedComponentProviders() throws ServiceConfigurationError {
        return StreamSupport.stream(ServiceFinder.find(ComponentProvider.class).spliterator(), false).map(RankedProvider::new).sorted(RANKED_COMPARATOR).collect(Collectors.toList());
    }
}

