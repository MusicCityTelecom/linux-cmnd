/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.lang.Nullable
 *  org.springframework.util.StringUtils
 */
package org.springframework.integration.leader;

import java.util.UUID;
import org.springframework.integration.leader.Candidate;
import org.springframework.integration.leader.Context;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

public abstract class AbstractCandidate
implements Candidate {
    private static final String DEFAULT_ROLE = "leader";
    private final String id;
    private final String role;

    public AbstractCandidate() {
        this(null, null);
    }

    public AbstractCandidate(@Nullable String id, @Nullable String role) {
        this.id = StringUtils.hasText((String)id) ? id : UUID.randomUUID().toString();
        this.role = StringUtils.hasText((String)role) ? role : DEFAULT_ROLE;
    }

    @Override
    public String getRole() {
        return this.role;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public abstract void onGranted(Context var1) throws InterruptedException;

    @Override
    public abstract void onRevoked(Context var1);
}

