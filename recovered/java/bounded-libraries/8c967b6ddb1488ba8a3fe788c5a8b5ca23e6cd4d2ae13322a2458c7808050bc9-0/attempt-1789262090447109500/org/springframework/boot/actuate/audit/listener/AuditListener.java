/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 */
package org.springframework.boot.actuate.audit.listener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.boot.actuate.audit.AuditEventRepository;
import org.springframework.boot.actuate.audit.listener.AbstractAuditListener;

public class AuditListener
extends AbstractAuditListener {
    private static final Log logger = LogFactory.getLog(AuditListener.class);
    private final AuditEventRepository auditEventRepository;

    public AuditListener(AuditEventRepository auditEventRepository) {
        this.auditEventRepository = auditEventRepository;
    }

    @Override
    protected void onAuditEvent(AuditEvent event) {
        if (logger.isDebugEnabled()) {
            logger.debug((Object)event);
        }
        this.auditEventRepository.add(event);
    }
}

