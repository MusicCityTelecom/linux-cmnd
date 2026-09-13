/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.lang.Nullable
 */
package org.springframework.messaging.simp.user;

import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.user.UserDestinationResult;

@FunctionalInterface
public interface UserDestinationResolver {
    @Nullable
    public UserDestinationResult resolveDestination(Message<?> var1);
}

