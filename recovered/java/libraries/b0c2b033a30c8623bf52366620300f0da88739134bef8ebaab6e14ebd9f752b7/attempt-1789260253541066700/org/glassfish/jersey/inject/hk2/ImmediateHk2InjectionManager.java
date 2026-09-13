/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.inject.hk2;

import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.glassfish.jersey.inject.hk2.AbstractHk2InjectionManager;
import org.glassfish.jersey.inject.hk2.Hk2Helper;
import org.glassfish.jersey.inject.hk2.LocalizationMessages;
import org.glassfish.jersey.internal.inject.Binder;
import org.glassfish.jersey.internal.inject.Binding;

public class ImmediateHk2InjectionManager
extends AbstractHk2InjectionManager {
    ImmediateHk2InjectionManager(Object parent) {
        super(parent);
    }

    @Override
    public void completeRegistration() throws IllegalStateException {
    }

    @Override
    public void register(Binding binding) {
        Hk2Helper.bind(this.getServiceLocator(), binding);
    }

    @Override
    public void register(Iterable<Binding> descriptors) {
        Hk2Helper.bind(this.getServiceLocator(), descriptors);
    }

    @Override
    public void register(Binder binder) {
        Hk2Helper.bind(this, binder);
    }

    @Override
    public void register(Object provider) {
        if (this.isRegistrable((Class)provider.getClass())) {
            ServiceLocatorUtilities.bind(this.getServiceLocator(), (org.glassfish.hk2.utilities.Binder)provider);
        } else if (Class.class.isInstance(provider) && this.isRegistrable((Class)provider)) {
            ServiceLocatorUtilities.bind(this.getServiceLocator(), (org.glassfish.hk2.utilities.Binder)this.createAndInitialize((Class)provider));
        } else {
            throw new IllegalArgumentException(LocalizationMessages.HK_2_PROVIDER_NOT_REGISTRABLE(provider.getClass()));
        }
    }
}

