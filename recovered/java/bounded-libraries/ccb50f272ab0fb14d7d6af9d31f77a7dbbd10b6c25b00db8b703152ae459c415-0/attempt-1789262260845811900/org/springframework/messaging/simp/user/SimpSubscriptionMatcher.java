/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.messaging.simp.user;

import org.springframework.messaging.simp.user.SimpSubscription;

@FunctionalInterface
public interface SimpSubscriptionMatcher {
    public boolean match(SimpSubscription var1);
}

