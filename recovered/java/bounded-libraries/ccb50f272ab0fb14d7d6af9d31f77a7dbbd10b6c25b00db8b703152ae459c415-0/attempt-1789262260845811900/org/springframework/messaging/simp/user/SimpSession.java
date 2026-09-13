/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.messaging.simp.user;

import java.util.Set;
import org.springframework.messaging.simp.user.SimpSubscription;
import org.springframework.messaging.simp.user.SimpUser;

public interface SimpSession {
    public String getId();

    public SimpUser getUser();

    public Set<SimpSubscription> getSubscriptions();
}

