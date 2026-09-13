/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.hk2.api;

import org.glassfish.hk2.api.HK2RuntimeException;
import org.glassfish.hk2.api.Injectee;

public class UnsatisfiedDependencyException
extends HK2RuntimeException {
    private static final long serialVersionUID = 1191047707346290567L;
    private final transient Injectee injectionPoint;

    public UnsatisfiedDependencyException() {
        this((Injectee)null);
    }

    public UnsatisfiedDependencyException(Injectee injectee) {
        super("There was no object available for injection at " + (injectee == null ? "<null>" : injectee.toString()));
        this.injectionPoint = injectee;
    }

    public Injectee getInjectee() {
        return this.injectionPoint;
    }
}

