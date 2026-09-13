/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.hk2.api;

import org.glassfish.hk2.api.DynamicConfiguration;
import org.glassfish.hk2.api.Populator;
import org.jvnet.hk2.annotations.Contract;

@Contract
public interface DynamicConfigurationService {
    public DynamicConfiguration createDynamicConfiguration();

    public Populator getPopulator();
}

