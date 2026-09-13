/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.inject.hk2;

import javax.inject.Singleton;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.inject.hk2.ContextInjectionResolverImpl;
import org.glassfish.jersey.inject.hk2.Hk2RequestScope;
import org.glassfish.jersey.inject.hk2.JerseyClassAnalyzer;
import org.glassfish.jersey.inject.hk2.JerseyErrorService;
import org.glassfish.jersey.inject.hk2.RequestContext;
import org.glassfish.jersey.process.internal.RequestScope;

public class Hk2BootstrapBinder
extends AbstractBinder {
    private final ServiceLocator serviceLocator;

    Hk2BootstrapBinder(ServiceLocator serviceLocator) {
        this.serviceLocator = serviceLocator;
    }

    @Override
    protected void configure() {
        this.install(new JerseyClassAnalyzer.Binder(this.serviceLocator), new RequestContext.Binder(), new ContextInjectionResolverImpl.Binder(), new JerseyErrorService.Binder());
        this.bind(Hk2RequestScope.class).to(RequestScope.class).in(Singleton.class);
    }
}

