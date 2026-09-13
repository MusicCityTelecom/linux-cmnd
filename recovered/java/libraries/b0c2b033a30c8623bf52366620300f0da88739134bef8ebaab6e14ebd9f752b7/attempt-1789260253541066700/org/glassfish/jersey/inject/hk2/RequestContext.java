/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.inject.hk2;

import java.lang.annotation.Annotation;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.glassfish.hk2.api.ActiveDescriptor;
import org.glassfish.hk2.api.Context;
import org.glassfish.hk2.api.ServiceHandle;
import org.glassfish.hk2.api.TypeLiteral;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.inject.hk2.Hk2RequestScope;
import org.glassfish.jersey.internal.inject.ForeignDescriptor;
import org.glassfish.jersey.process.internal.RequestScope;
import org.glassfish.jersey.process.internal.RequestScoped;

@Singleton
public class RequestContext
implements Context<RequestScoped> {
    private final RequestScope requestScope;

    @Inject
    public RequestContext(RequestScope requestScope) {
        this.requestScope = requestScope;
    }

    @Override
    public Class<? extends Annotation> getScope() {
        return RequestScoped.class;
    }

    @Override
    public <U> U findOrCreate(ActiveDescriptor<U> activeDescriptor, ServiceHandle<?> root) {
        Hk2RequestScope.Instance instance = (Hk2RequestScope.Instance)this.requestScope.current();
        Object retVal = instance.get(ForeignDescriptor.wrap(activeDescriptor));
        if (retVal == null) {
            retVal = activeDescriptor.create(root);
            instance.put(ForeignDescriptor.wrap(activeDescriptor, obj -> activeDescriptor.dispose(obj)), retVal);
        }
        return (U)retVal;
    }

    @Override
    public boolean containsKey(ActiveDescriptor<?> descriptor) {
        Hk2RequestScope.Instance instance = (Hk2RequestScope.Instance)this.requestScope.current();
        return instance.contains(ForeignDescriptor.wrap(descriptor));
    }

    @Override
    public boolean supportsNullCreation() {
        return true;
    }

    @Override
    public boolean isActive() {
        return this.requestScope.isActive();
    }

    @Override
    public void destroyOne(ActiveDescriptor<?> descriptor) {
        Hk2RequestScope.Instance instance = (Hk2RequestScope.Instance)this.requestScope.current();
        instance.remove(ForeignDescriptor.wrap(descriptor));
    }

    @Override
    public void shutdown() {
        this.requestScope.shutdown();
    }

    public static class Binder
    extends AbstractBinder {
        @Override
        protected void configure() {
            this.bindAsContract(RequestContext.class).to(new TypeLiteral<Context<RequestScoped>>(){}.getType()).in(Singleton.class);
        }
    }
}

