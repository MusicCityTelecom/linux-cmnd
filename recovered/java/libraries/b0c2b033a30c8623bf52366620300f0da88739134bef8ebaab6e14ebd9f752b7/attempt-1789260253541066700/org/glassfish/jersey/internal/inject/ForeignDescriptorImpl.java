/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.internal.inject;

import java.util.function.Consumer;
import org.glassfish.jersey.internal.inject.ForeignDescriptor;

public class ForeignDescriptorImpl
implements ForeignDescriptor {
    private static final Consumer<Object> NOOP_DISPOSE_INSTANCE = instance -> {};
    private final Object foreignDescriptor;
    private final Consumer<Object> disposeInstance;

    public ForeignDescriptorImpl(Object foreignDescriptor) {
        this(foreignDescriptor, NOOP_DISPOSE_INSTANCE);
    }

    public ForeignDescriptorImpl(Object foreignDescriptor, Consumer<Object> disposeInstance) {
        this.foreignDescriptor = foreignDescriptor;
        this.disposeInstance = disposeInstance;
    }

    @Override
    public Object get() {
        return this.foreignDescriptor;
    }

    @Override
    public void dispose(Object instance) {
        this.disposeInstance.accept(instance);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ForeignDescriptorImpl)) {
            return false;
        }
        ForeignDescriptorImpl that = (ForeignDescriptorImpl)o;
        return this.foreignDescriptor.equals(that.foreignDescriptor);
    }

    public int hashCode() {
        return this.foreignDescriptor.hashCode();
    }
}

