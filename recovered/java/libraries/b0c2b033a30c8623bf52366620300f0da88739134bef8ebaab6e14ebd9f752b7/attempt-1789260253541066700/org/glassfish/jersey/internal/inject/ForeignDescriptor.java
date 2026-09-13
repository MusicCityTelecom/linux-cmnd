/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.internal.inject;

import java.util.function.Consumer;
import org.glassfish.jersey.internal.inject.ForeignDescriptorImpl;

public interface ForeignDescriptor {
    public Object get();

    public void dispose(Object var1);

    public static ForeignDescriptor wrap(Object descriptor) {
        return new ForeignDescriptorImpl(descriptor);
    }

    public static ForeignDescriptor wrap(Object descriptor, Consumer<Object> disposeInstance) {
        return new ForeignDescriptorImpl(descriptor, disposeInstance);
    }
}

