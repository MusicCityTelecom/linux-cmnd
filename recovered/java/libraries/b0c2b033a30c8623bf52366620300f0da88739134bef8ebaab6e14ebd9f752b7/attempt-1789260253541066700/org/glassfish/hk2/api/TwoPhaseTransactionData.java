/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.hk2.api;

import java.util.List;
import org.glassfish.hk2.api.ActiveDescriptor;

public interface TwoPhaseTransactionData {
    public List<ActiveDescriptor<?>> getAllAddedDescriptors();

    public List<ActiveDescriptor<?>> getAllRemovedDescriptors();
}

