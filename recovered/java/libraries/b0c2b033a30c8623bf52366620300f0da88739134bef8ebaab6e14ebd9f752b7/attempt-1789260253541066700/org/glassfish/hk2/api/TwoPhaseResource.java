/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.hk2.api;

import org.glassfish.hk2.api.MultiException;
import org.glassfish.hk2.api.TwoPhaseTransactionData;

public interface TwoPhaseResource {
    public void prepareDynamicConfiguration(TwoPhaseTransactionData var1) throws MultiException;

    public void activateDynamicConfiguration(TwoPhaseTransactionData var1);

    public void rollbackDynamicConfiguration(TwoPhaseTransactionData var1);
}

