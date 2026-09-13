/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.hk2.api;

import org.glassfish.hk2.api.Descriptor;
import org.glassfish.hk2.api.HK2RuntimeException;

public class DuplicateServiceException
extends HK2RuntimeException {
    private static final long serialVersionUID = 7182947621027566487L;
    private Descriptor existingDescriptor;

    public DuplicateServiceException() {
    }

    public DuplicateServiceException(Descriptor existingDescriptor) {
        this.existingDescriptor = existingDescriptor;
    }

    public Descriptor getExistingDescriptor() {
        return this.existingDescriptor;
    }

    @Override
    public String toString() {
        return "DuplicateServiceException(" + this.existingDescriptor + "," + System.identityHashCode(this) + ")";
    }
}

