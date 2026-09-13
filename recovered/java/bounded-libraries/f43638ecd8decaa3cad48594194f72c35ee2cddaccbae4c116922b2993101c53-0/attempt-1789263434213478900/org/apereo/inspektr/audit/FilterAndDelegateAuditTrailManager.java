/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.inspektr.audit.AuditActionContext
 *  org.apereo.inspektr.audit.AuditTrailManager
 *  org.apereo.inspektr.audit.AuditTrailManager$AuditFormats
 *  org.apereo.inspektr.audit.AuditTrailManager$WhereClauseFields
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.boot.actuate.audit.listener.AuditApplicationEvent
 *  org.springframework.context.ApplicationEvent
 *  org.springframework.context.ApplicationEventPublisher
 *  org.springframework.context.ApplicationEventPublisherAware
 */
package org.apereo.inspektr.audit;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.apereo.inspektr.audit.AuditActionContext;
import org.apereo.inspektr.audit.AuditTrailManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.audit.listener.AuditApplicationEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

public class FilterAndDelegateAuditTrailManager
implements AuditTrailManager,
ApplicationEventPublisherAware {
    private static final Logger LOGGER = LoggerFactory.getLogger(FilterAndDelegateAuditTrailManager.class);
    private final Collection<AuditTrailManager> auditTrailManagers;
    private final List<String> supportedActionsPerformed;
    private final List<String> excludedActionsPerformed;
    private ApplicationEventPublisher applicationEventPublisher;

    public FilterAndDelegateAuditTrailManager(Collection<AuditTrailManager> auditTrailManagers, List<String> supportedActionsPerformed, List<String> excludedActionsPerformed) {
        this.auditTrailManagers = auditTrailManagers;
        this.supportedActionsPerformed = supportedActionsPerformed;
        this.excludedActionsPerformed = excludedActionsPerformed;
    }

    public void setAuditFormat(AuditTrailManager.AuditFormats auditFormat) {
        this.auditTrailManagers.forEach(mgr -> mgr.setAuditFormat(auditFormat));
    }

    public void record(AuditActionContext auditActionContext) {
        boolean matched = this.supportedActionsPerformed.stream().anyMatch(action -> {
            String actionPerformed = auditActionContext.getActionPerformed();
            return "*".equals(action) || Pattern.compile(action).matcher(actionPerformed).find();
        });
        if (matched) {
            matched = this.excludedActionsPerformed.stream().noneMatch(action -> {
                String actionPerformed = auditActionContext.getActionPerformed();
                return "*".equals(action) || Pattern.compile(action).matcher(actionPerformed).find();
            });
        }
        if (matched) {
            LOGGER.trace("Recording audit action context [{}]", (Object)auditActionContext);
            this.auditTrailManagers.forEach(mgr -> mgr.record(auditActionContext));
            if (this.applicationEventPublisher != null) {
                AuditApplicationEvent auditEvent = new AuditApplicationEvent(auditActionContext.getPrincipal(), auditActionContext.getActionPerformed(), new String[]{auditActionContext.getApplicationCode(), auditActionContext.getClientIpAddress(), auditActionContext.getServerIpAddress(), auditActionContext.getResourceOperatedUpon(), auditActionContext.getWhenActionWasPerformed().toString()});
                this.applicationEventPublisher.publishEvent((ApplicationEvent)auditEvent);
            }
        } else {
            LOGGER.trace("Skipping to record audit action context [{}] as it's not authorized as an audit action among [{}]", (Object)auditActionContext, this.supportedActionsPerformed);
        }
    }

    public Set<? extends AuditActionContext> getAuditRecords(Map<AuditTrailManager.WhereClauseFields, Object> params) {
        return this.auditTrailManagers.stream().map(mgr -> mgr.getAuditRecords(params)).flatMap(Collection::stream).collect(Collectors.toSet());
    }

    public void removeAll() {
        this.auditTrailManagers.forEach(AuditTrailManager::removeAll);
    }

    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }
}

