/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.lang.Nullable
 */
package org.springframework.messaging.simp.user;

import java.security.Principal;
import java.util.Set;
import org.springframework.lang.Nullable;
import org.springframework.messaging.simp.user.SimpSession;

public interface SimpUser {
    public String getName();

    @Nullable
    public Principal getPrincipal();

    public boolean hasSessions();

    @Nullable
    public SimpSession getSession(String var1);

    public Set<SimpSession> getSessions();
}

