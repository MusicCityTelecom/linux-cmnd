/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.internal;

import org.glassfish.jersey.internal.BootstrapBag;
import org.glassfish.jersey.internal.inject.InjectionManager;

public interface BootstrapConfigurator {
    public void init(InjectionManager var1, BootstrapBag var2);

    default public void postInit(InjectionManager injectionManager, BootstrapBag bootstrapBag) {
    }
}

