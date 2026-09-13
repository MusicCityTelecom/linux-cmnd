/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.lang.Nullable
 */
package org.springframework.messaging.simp.user;

import java.util.Set;
import org.springframework.lang.Nullable;
import org.springframework.messaging.simp.user.SimpSubscription;
import org.springframework.messaging.simp.user.SimpSubscriptionMatcher;
import org.springframework.messaging.simp.user.SimpUser;

public interface SimpUserRegistry {
    @Nullable
    public SimpUser getUser(String var1);

    public Set<SimpUser> getUsers();

    public int getUserCount();

    public Set<SimpSubscription> findSubscriptions(SimpSubscriptionMatcher var1);
}

