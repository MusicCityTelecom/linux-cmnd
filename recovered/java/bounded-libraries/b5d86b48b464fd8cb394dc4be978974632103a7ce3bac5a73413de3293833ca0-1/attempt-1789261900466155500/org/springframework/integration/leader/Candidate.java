/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.integration.leader;

import org.springframework.integration.leader.Context;

public interface Candidate {
    public String getRole();

    public String getId();

    public void onGranted(Context var1) throws InterruptedException;

    public void onRevoked(Context var1);
}

