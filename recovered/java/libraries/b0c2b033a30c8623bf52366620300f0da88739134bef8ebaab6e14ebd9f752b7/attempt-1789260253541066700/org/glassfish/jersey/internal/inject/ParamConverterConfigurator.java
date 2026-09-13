/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.internal.inject;

import javax.ws.rs.ext.ParamConverterProvider;
import org.glassfish.jersey.internal.BootstrapBag;
import org.glassfish.jersey.internal.BootstrapConfigurator;
import org.glassfish.jersey.internal.inject.Bindings;
import org.glassfish.jersey.internal.inject.InjectionManager;
import org.glassfish.jersey.internal.inject.InstanceBinding;
import org.glassfish.jersey.internal.inject.ParamConverters;

public class ParamConverterConfigurator
implements BootstrapConfigurator {
    @Override
    public void init(InjectionManager injectionManager, BootstrapBag bootstrapBag) {
        InstanceBinding aggregatedConverters = (InstanceBinding)Bindings.service(new ParamConverters.AggregatedProvider()).to(ParamConverterProvider.class);
        injectionManager.register(aggregatedConverters);
    }
}

