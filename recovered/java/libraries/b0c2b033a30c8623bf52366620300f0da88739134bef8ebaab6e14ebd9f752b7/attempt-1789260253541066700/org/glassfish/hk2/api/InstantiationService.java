/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.hk2.api;

import org.glassfish.hk2.api.InstantiationData;
import org.jvnet.hk2.annotations.Contract;

@Contract
public interface InstantiationService {
    public InstantiationData getInstantiationData();
}

