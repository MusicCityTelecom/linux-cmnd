/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.integration.graph;

import java.util.function.Supplier;
import org.springframework.integration.graph.SendTimers;

public interface SendTimersAware {
    public void sendTimers(Supplier<SendTimers> var1);
}

