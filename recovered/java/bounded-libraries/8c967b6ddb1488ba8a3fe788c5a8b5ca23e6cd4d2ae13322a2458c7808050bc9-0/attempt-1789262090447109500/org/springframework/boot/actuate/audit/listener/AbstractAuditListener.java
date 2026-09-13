/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.context.ApplicationListener
 */
package org.springframework.boot.actuate.audit.listener;

import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.boot.actuate.audit.listener.AuditApplicationEvent;
import org.springframework.context.ApplicationListener;

public abstract class AbstractAuditListener
implements ApplicationListener<AuditApplicationEvent> {
    public void onApplicationEvent(AuditApplicationEvent event) {
        this.onAuditEvent(event.getAuditEvent());
    }

    protected abstract void onAuditEvent(AuditEvent var1);
}

