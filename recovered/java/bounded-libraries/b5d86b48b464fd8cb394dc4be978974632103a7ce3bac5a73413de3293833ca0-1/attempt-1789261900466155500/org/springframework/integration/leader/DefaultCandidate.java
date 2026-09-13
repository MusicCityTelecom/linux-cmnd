/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 */
package org.springframework.integration.leader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.integration.leader.AbstractCandidate;
import org.springframework.integration.leader.Context;

public class DefaultCandidate
extends AbstractCandidate {
    private final Log logger = LogFactory.getLog(this.getClass());
    private volatile Context leaderContext;

    public DefaultCandidate() {
    }

    public DefaultCandidate(String id, String role) {
        super(id, role);
    }

    @Override
    public void onGranted(Context ctx) {
        if (this.logger.isInfoEnabled()) {
            this.logger.info((Object)(this + " has been granted leadership; context: " + ctx));
        }
        this.leaderContext = ctx;
    }

    @Override
    public void onRevoked(Context ctx) {
        if (this.logger.isInfoEnabled()) {
            this.logger.info((Object)(this + " leadership has been revoked: " + ctx));
        }
    }

    public void yieldLeadership() {
        if (this.leaderContext != null) {
            this.leaderContext.yield();
        }
    }

    public String toString() {
        return String.format("DefaultCandidate{role=%s, id=%s}", this.getRole(), this.getId());
    }
}

