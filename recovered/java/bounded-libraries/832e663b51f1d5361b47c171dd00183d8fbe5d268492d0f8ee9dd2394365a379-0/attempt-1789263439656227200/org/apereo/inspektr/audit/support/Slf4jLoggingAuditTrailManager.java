/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.inspektr.audit.support;

import org.apereo.inspektr.audit.AuditActionContext;
import org.apereo.inspektr.audit.support.AbstractStringAuditTrailManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Slf4jLoggingAuditTrailManager
extends AbstractStringAuditTrailManager {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void record(AuditActionContext auditActionContext) {
        this.log.info(this.toString(auditActionContext));
    }

    @Override
    public void removeAll() {
    }
}

