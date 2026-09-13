/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.client.internal.inject;

import javax.ws.rs.ext.ParamConverterProvider;
import org.glassfish.jersey.client.ClientBootstrapBag;
import org.glassfish.jersey.client.inject.ParameterUpdaterProvider;
import org.glassfish.jersey.client.internal.inject.ParameterUpdaterFactory;
import org.glassfish.jersey.internal.BootstrapBag;
import org.glassfish.jersey.internal.BootstrapConfigurator;
import org.glassfish.jersey.internal.inject.Binding;
import org.glassfish.jersey.internal.inject.Bindings;
import org.glassfish.jersey.internal.inject.InjectionManager;
import org.glassfish.jersey.internal.inject.ParamConverterFactory;
import org.glassfish.jersey.internal.inject.Providers;
import org.glassfish.jersey.internal.util.collection.LazyValue;
import org.glassfish.jersey.internal.util.collection.Values;

public class ParameterUpdaterConfigurator
implements BootstrapConfigurator {
    @Override
    public void init(InjectionManager injectionManager, BootstrapBag bootstrapBag) {
        ClientBootstrapBag clientBag = (ClientBootstrapBag)bootstrapBag;
        LazyValue<ParamConverterFactory> lazyParamConverterFactory = Values.lazy(() -> new ParamConverterFactory(Providers.getProviders(injectionManager, ParamConverterProvider.class), Providers.getCustomProviders(injectionManager, ParamConverterProvider.class)));
        ParameterUpdaterFactory parameterUpdaterFactory = new ParameterUpdaterFactory(lazyParamConverterFactory);
        clientBag.setParameterUpdaterProvider(parameterUpdaterFactory);
        injectionManager.register((Binding)Bindings.service(parameterUpdaterFactory).to(ParameterUpdaterProvider.class));
    }
}

