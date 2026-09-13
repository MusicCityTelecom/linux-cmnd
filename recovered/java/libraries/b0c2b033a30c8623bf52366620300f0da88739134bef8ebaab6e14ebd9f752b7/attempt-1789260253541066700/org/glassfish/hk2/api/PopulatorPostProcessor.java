/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.hk2.api;

import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.DescriptorImpl;

public interface PopulatorPostProcessor {
    public DescriptorImpl process(ServiceLocator var1, DescriptorImpl var2);
}

