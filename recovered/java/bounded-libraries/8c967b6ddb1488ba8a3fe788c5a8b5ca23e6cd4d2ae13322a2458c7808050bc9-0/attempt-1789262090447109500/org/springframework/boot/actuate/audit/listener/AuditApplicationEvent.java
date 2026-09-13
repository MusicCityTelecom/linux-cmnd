/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.context.ApplicationEvent
 *  org.springframework.util.Assert
 */
package org.springframework.boot.actuate.audit.listener;

import java.time.Instant;
import java.util.Map;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.util.Assert;

public class AuditApplicationEvent
extends ApplicationEvent {
    private final AuditEvent auditEvent;

    public AuditApplicationEvent(String principal, String type, Map<String, Object> data) {
        this(new AuditEvent(principal, type, data));
    }

    public AuditApplicationEvent(String principal, String type, String ... data) {
        this(new AuditEvent(principal, type, data));
    }

    public AuditApplicationEvent(Instant timestamp, String principal, String type, Map<String, Object> data) {
        this(new AuditEvent(timestamp, principal, type, data));
    }

    public AuditApplicationEvent(AuditEvent auditEvent) {
        super((Object)auditEvent);
        Assert.notNull((Object)auditEvent, (String)"AuditEvent must not be null");
        this.auditEvent = auditEvent;
    }

    public AuditEvent getAuditEvent() {
        return this.auditEvent;
    }
}

