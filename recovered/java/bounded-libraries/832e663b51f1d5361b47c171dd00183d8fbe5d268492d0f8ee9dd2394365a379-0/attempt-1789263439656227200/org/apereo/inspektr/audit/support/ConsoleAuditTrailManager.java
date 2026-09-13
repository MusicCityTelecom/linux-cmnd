/*
 * Decompiled with CFR 0.152.
 */
package org.apereo.inspektr.audit.support;

import org.apereo.inspektr.audit.AuditActionContext;
import org.apereo.inspektr.audit.support.AbstractStringAuditTrailManager;

public class ConsoleAuditTrailManager
extends AbstractStringAuditTrailManager {
    @Override
    public void record(AuditActionContext auditActionContext) {
        System.out.println(this.toString(auditActionContext));
    }

    @Override
    public void removeAll() {
    }
}

