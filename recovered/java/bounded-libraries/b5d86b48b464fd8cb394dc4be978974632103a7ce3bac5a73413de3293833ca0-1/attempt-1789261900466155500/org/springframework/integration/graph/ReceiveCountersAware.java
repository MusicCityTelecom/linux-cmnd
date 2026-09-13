/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.integration.graph;

import java.util.function.Supplier;
import org.springframework.integration.graph.ReceiveCounters;

public interface ReceiveCountersAware {
    public void receiveCounters(Supplier<ReceiveCounters> var1);
}

