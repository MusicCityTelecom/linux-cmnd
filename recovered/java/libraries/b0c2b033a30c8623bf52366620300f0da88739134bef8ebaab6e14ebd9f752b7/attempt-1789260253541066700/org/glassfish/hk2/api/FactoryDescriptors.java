/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.hk2.api;

import org.glassfish.hk2.api.Descriptor;

public interface FactoryDescriptors {
    public Descriptor getFactoryAsAService();

    public Descriptor getFactoryAsAFactory();
}

