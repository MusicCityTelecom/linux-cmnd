/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.integration.leader.event;

import org.springframework.integration.leader.Context;

public interface LeaderEventPublisher {
    public void publishOnGranted(Object var1, Context var2, String var3);

    public void publishOnRevoked(Object var1, Context var2, String var3);

    public void publishOnFailedToAcquire(Object var1, Context var2, String var3);
}

