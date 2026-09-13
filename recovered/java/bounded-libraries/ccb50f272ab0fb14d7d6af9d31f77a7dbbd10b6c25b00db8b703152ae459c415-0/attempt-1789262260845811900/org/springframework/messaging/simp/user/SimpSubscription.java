/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.messaging.simp.user;

import org.springframework.messaging.simp.user.SimpSession;

public interface SimpSubscription {
    public String getId();

    public SimpSession getSession();

    public String getDestination();
}

