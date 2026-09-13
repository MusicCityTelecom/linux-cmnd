/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.aspectj.lang.JoinPoint
 */
package org.apereo.inspektr.audit;

import org.apereo.inspektr.audit.AuditPointRuntimeInfo;
import org.aspectj.lang.JoinPoint;

public class AspectJAuditPointRuntimeInfo
implements AuditPointRuntimeInfo {
    private JoinPoint currentJoinPoint;

    public AspectJAuditPointRuntimeInfo(JoinPoint currentJoinPoint) {
        this.currentJoinPoint = currentJoinPoint;
    }

    @Override
    public String asString() {
        return this.currentJoinPoint.toLongString();
    }
}

