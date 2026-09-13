/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.inject.hk2;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Singleton;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.glassfish.jersey.inject.hk2.AbstractHk2InjectionManager;
import org.glassfish.jersey.inject.hk2.Hk2Helper;
import org.glassfish.jersey.inject.hk2.LocalizationMessages;
import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.internal.inject.Binder;
import org.glassfish.jersey.internal.inject.Binding;
import org.glassfish.jersey.internal.inject.Bindings;
import org.glassfish.jersey.internal.inject.InstanceBinding;

public class DelayedHk2InjectionManager
extends AbstractHk2InjectionManager {
    private final AbstractBinder bindings = new AbstractBinder(){

        @Override
        protected void configure() {
        }
    };
    private final List<org.glassfish.hk2.utilities.Binder> providers = new ArrayList<org.glassfish.hk2.utilities.Binder>();
    private boolean completed = false;

    DelayedHk2InjectionManager(Object parent) {
        super(parent);
    }

    @Override
    public void register(Binding binding) {
        if (this.completed && (binding.getScope() == Singleton.class || binding instanceof InstanceBinding)) {
            Hk2Helper.bind(this.getServiceLocator(), binding);
        } else {
            this.bindings.bind(binding);
        }
    }

    @Override
    public void register(Iterable<Binding> bindings) {
        for (Binding binding : bindings) {
            this.bindings.bind(binding);
        }
    }

    @Override
    public void register(Binder binder) {
        for (Binding binding : Bindings.getBindings(this, binder)) {
            this.bindings.bind(binding);
        }
    }

    @Override
    public void register(Object provider) throws IllegalArgumentException {
        if (this.isRegistrable((Class)provider.getClass())) {
            this.providers.add((org.glassfish.hk2.utilities.Binder)provider);
        } else if (Class.class.isInstance(provider) && this.isRegistrable((Class)provider)) {
            this.providers.add((org.glassfish.hk2.utilities.Binder)this.createAndInitialize((Class)provider));
        } else {
            throw new IllegalArgumentException(LocalizationMessages.HK_2_PROVIDER_NOT_REGISTRABLE(provider.getClass()));
        }
    }

    @Override
    public void completeRegistration() throws IllegalStateException {
        Hk2Helper.bind(this, this.bindings);
        ServiceLocatorUtilities.bind(this.getServiceLocator(), this.providers.toArray(new org.glassfish.hk2.utilities.Binder[0]));
        this.completed = true;
    }
}

