/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.internal.inject;

import org.glassfish.jersey.internal.inject.InjectionManager;

public interface InjectionManagerFactory {
    default public InjectionManager create() {
        return this.create(null);
    }

    public InjectionManager create(Object var1);
}

