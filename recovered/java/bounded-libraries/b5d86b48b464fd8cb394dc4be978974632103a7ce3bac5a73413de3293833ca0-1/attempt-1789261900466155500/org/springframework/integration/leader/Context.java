/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.lang.Nullable
 */
package org.springframework.integration.leader;

import org.springframework.lang.Nullable;

@FunctionalInterface
public interface Context {
    public boolean isLeader();

    default public void yield() {
    }

    @Nullable
    default public String getRole() {
        return null;
    }
}

