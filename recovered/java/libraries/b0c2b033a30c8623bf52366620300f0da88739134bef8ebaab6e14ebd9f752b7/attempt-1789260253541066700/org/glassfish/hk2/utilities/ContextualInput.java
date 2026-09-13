/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.hk2.utilities;

import org.glassfish.hk2.api.ActiveDescriptor;
import org.glassfish.hk2.api.ServiceHandle;

public class ContextualInput<T> {
    private final ActiveDescriptor<T> descriptor;
    private final ServiceHandle<?> root;

    public ContextualInput(ActiveDescriptor<T> descriptor, ServiceHandle<?> root) {
        this.descriptor = descriptor;
        this.root = root;
    }

    public ActiveDescriptor<T> getDescriptor() {
        return this.descriptor;
    }

    public ServiceHandle<?> getRoot() {
        return this.root;
    }

    public int hashCode() {
        return this.descriptor.hashCode();
    }

    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (!(o instanceof ContextualInput)) {
            return false;
        }
        ContextualInput other = (ContextualInput)o;
        return this.descriptor.equals(other.descriptor);
    }

    public String toString() {
        return "ContextualInput(" + this.descriptor.getImplementation() + "," + this.root + "," + System.identityHashCode(this) + ")";
    }
}

