/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.inspektr.audit.AuditActionContext
 */
package org.apereo.cas.support.events.audit;

import lombok.Generated;
import org.apereo.cas.support.events.AbstractCasEvent;
import org.apereo.inspektr.audit.AuditActionContext;

public class CasAuditActionContextRecordedEvent
extends AbstractCasEvent {
    private static final long serialVersionUID = -1262975970594313844L;
    private final AuditActionContext auditActionContext;

    public CasAuditActionContextRecordedEvent(Object source, AuditActionContext auditActionContext) {
        super(source);
        this.auditActionContext = auditActionContext;
    }

    @Generated
    public AuditActionContext getAuditActionContext() {
        return this.auditActionContext;
    }
}

