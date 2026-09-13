/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.inspektr.common.spi.ClientInfoResolver
 *  org.apereo.inspektr.common.spi.DefaultClientInfoResolver
 *  org.apereo.inspektr.common.spi.PrincipalResolver
 *  org.apereo.inspektr.common.web.ClientInfo
 *  org.aspectj.lang.JoinPoint
 *  org.aspectj.lang.ProceedingJoinPoint
 *  org.aspectj.lang.annotation.Around
 *  org.aspectj.lang.annotation.Aspect
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.inspektr.audit;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apereo.inspektr.audit.AspectJAuditPointRuntimeInfo;
import org.apereo.inspektr.audit.AuditActionContext;
import org.apereo.inspektr.audit.AuditPointRuntimeInfo;
import org.apereo.inspektr.audit.AuditTrailManager;
import org.apereo.inspektr.audit.annotation.Audit;
import org.apereo.inspektr.audit.annotation.Audits;
import org.apereo.inspektr.audit.spi.AuditActionResolver;
import org.apereo.inspektr.audit.spi.AuditResourceResolver;
import org.apereo.inspektr.common.spi.ClientInfoResolver;
import org.apereo.inspektr.common.spi.DefaultClientInfoResolver;
import org.apereo.inspektr.common.spi.PrincipalResolver;
import org.apereo.inspektr.common.web.ClientInfo;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
public class AuditTrailManagementAspect {
    private static final Logger LOG = LoggerFactory.getLogger(AuditTrailManagementAspect.class);
    private final PrincipalResolver defaultAuditPrincipalResolver;
    private final Map<String, AuditActionResolver> auditActionResolvers;
    private final Map<String, AuditResourceResolver> auditResourceResolvers;
    private final Map<String, PrincipalResolver> auditPrincipalResolvers;
    private final List<AuditTrailManager> auditTrailManagers;
    private final String applicationCode;
    protected AuditTrailManager.AuditFormats auditFormat = AuditTrailManager.AuditFormats.DEFAULT;
    private ClientInfoResolver clientInfoResolver = new DefaultClientInfoResolver();
    private boolean failOnAuditFailures = true;
    private boolean enabled = true;

    public AuditTrailManagementAspect(String applicationCode, PrincipalResolver defaultAuditPrincipalResolver, List<AuditTrailManager> auditTrailManagers, Map<String, AuditActionResolver> auditActionResolverMap, Map<String, AuditResourceResolver> auditResourceResolverMap) {
        this(applicationCode, defaultAuditPrincipalResolver, auditTrailManagers, auditActionResolverMap, auditResourceResolverMap, new HashMap<String, PrincipalResolver>(), AuditTrailManager.AuditFormats.DEFAULT);
    }

    public AuditTrailManagementAspect(String applicationCode, PrincipalResolver defaultAuditPrincipalResolver, List<AuditTrailManager> auditTrailManagers, Map<String, AuditActionResolver> auditActionResolverMap, Map<String, AuditResourceResolver> auditResourceResolverMap, Map<String, PrincipalResolver> auditPrincipalResolvers, AuditTrailManager.AuditFormats auditFormat) {
        this.defaultAuditPrincipalResolver = defaultAuditPrincipalResolver;
        this.auditPrincipalResolvers = auditPrincipalResolvers;
        this.auditTrailManagers = auditTrailManagers;
        this.applicationCode = applicationCode;
        this.auditActionResolvers = auditActionResolverMap;
        this.auditResourceResolvers = auditResourceResolverMap;
        this.auditFormat = auditFormat;
    }

    @Around(value="@annotation(audits)", argNames="audits")
    public Object handleAuditTrail(ProceedingJoinPoint joinPoint, Audits audits) throws Throwable {
        if (!this.enabled) {
            return joinPoint.proceed();
        }
        Object retVal = null;
        String currentPrincipal = null;
        String[] actions = new String[audits.value().length];
        String[][] auditableResources = new String[audits.value().length][];
        try {
            retVal = joinPoint.proceed();
            currentPrincipal = this.getCurrentPrincipal(joinPoint, audits, retVal);
            if (currentPrincipal != null) {
                for (int i = 0; i < audits.value().length; ++i) {
                    AuditActionResolver auditActionResolver = this.auditActionResolvers.get(audits.value()[i].actionResolverName());
                    AuditResourceResolver auditResourceResolver = this.auditResourceResolvers.get(audits.value()[i].resourceResolverName());
                    auditResourceResolver.setAuditFormat(this.auditFormat);
                    auditableResources[i] = auditResourceResolver.resolveFrom((JoinPoint)joinPoint, retVal);
                    actions[i] = auditActionResolver.resolveFrom((JoinPoint)joinPoint, retVal, audits.value()[i]);
                }
            }
            Object i = retVal;
            return i;
        }
        catch (Throwable t) {
            Exception e = this.wrapIfNecessary(t);
            currentPrincipal = this.getCurrentPrincipal(joinPoint, audits, (Object)e);
            if (currentPrincipal != null) {
                for (int i = 0; i < audits.value().length; ++i) {
                    AuditResourceResolver auditResourceResolver = this.auditResourceResolvers.get(audits.value()[i].resourceResolverName());
                    auditResourceResolver.setAuditFormat(this.auditFormat);
                    auditableResources[i] = auditResourceResolver.resolveFrom((JoinPoint)joinPoint, e);
                    actions[i] = this.auditActionResolvers.get(audits.value()[i].actionResolverName()).resolveFrom((JoinPoint)joinPoint, e, audits.value()[i]);
                }
            }
            throw t;
        }
        finally {
            for (int i = 0; i < audits.value().length; ++i) {
                this.executeAuditCode(currentPrincipal, auditableResources[i], joinPoint, retVal, actions[i], audits.value()[i]);
            }
        }
    }

    @Around(value="@annotation(audit)", argNames="audit")
    public Object handleAuditTrail(ProceedingJoinPoint joinPoint, Audit audit) throws Throwable {
        if (!this.enabled) {
            return joinPoint.proceed();
        }
        AuditActionResolver auditActionResolver = this.auditActionResolvers.get(audit.actionResolverName());
        AuditResourceResolver auditResourceResolver = this.auditResourceResolvers.get(audit.resourceResolverName());
        auditResourceResolver.setAuditFormat(this.auditFormat);
        String currentPrincipal = null;
        String[] auditResource = new String[]{null};
        String action = null;
        Object retVal = null;
        try {
            retVal = joinPoint.proceed();
            currentPrincipal = this.getCurrentPrincipal(joinPoint, audit, retVal);
            auditResource = auditResourceResolver.resolveFrom((JoinPoint)joinPoint, retVal);
            action = auditActionResolver.resolveFrom((JoinPoint)joinPoint, retVal, audit);
            Object object = retVal;
            this.executeAuditCode(currentPrincipal, auditResource, joinPoint, retVal, action, audit);
            return object;
        }
        catch (Throwable t) {
            try {
                Exception e = this.wrapIfNecessary(t);
                currentPrincipal = this.getCurrentPrincipal(joinPoint, audit, (Object)e);
                auditResource = auditResourceResolver.resolveFrom((JoinPoint)joinPoint, e);
                action = auditActionResolver.resolveFrom((JoinPoint)joinPoint, e, audit);
                throw t;
            }
            catch (Throwable throwable) {
                this.executeAuditCode(currentPrincipal, auditResource, joinPoint, retVal, action, audit);
                throw throwable;
            }
        }
    }

    public void setFailOnAuditFailures(boolean failOnAuditFailures) {
        this.failOnAuditFailures = failOnAuditFailures;
    }

    public void setClientInfoResolver(ClientInfoResolver factory) {
        this.clientInfoResolver = factory;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    private String getCurrentPrincipal(ProceedingJoinPoint joinPoint, Audits audits, Object retVal) {
        String currentPrincipal = null;
        for (int i = 0; i < audits.value().length; ++i) {
            String resolverName = audits.value()[i].principalResolverName();
            if (resolverName.trim().length() <= 0) continue;
            PrincipalResolver resolver = this.auditPrincipalResolvers.get(resolverName);
            currentPrincipal = resolver.resolveFrom((JoinPoint)joinPoint, retVal);
        }
        if (currentPrincipal == null) {
            currentPrincipal = this.defaultAuditPrincipalResolver.resolveFrom((JoinPoint)joinPoint, retVal);
        }
        return currentPrincipal;
    }

    private String getCurrentPrincipal(ProceedingJoinPoint joinPoint, Audit audit, Object retVal) {
        String currentPrincipal = null;
        String resolverName = audit.principalResolverName();
        if (resolverName.trim().length() > 0) {
            PrincipalResolver resolver = this.auditPrincipalResolvers.get(resolverName);
            currentPrincipal = resolver.resolveFrom((JoinPoint)joinPoint, retVal);
        }
        if (currentPrincipal == null) {
            currentPrincipal = this.defaultAuditPrincipalResolver.resolveFrom((JoinPoint)joinPoint, retVal);
        }
        return currentPrincipal;
    }

    private void executeAuditCode(String currentPrincipal, String[] auditableResources, ProceedingJoinPoint joinPoint, Object retVal, String action, Audit audit) {
        String applicationCode = audit.applicationCode() != null && audit.applicationCode().length() > 0 ? audit.applicationCode() : this.applicationCode;
        ClientInfo clientInfo = this.clientInfoResolver.resolveFrom((JoinPoint)joinPoint, retVal);
        Date actionDate = new Date();
        AspectJAuditPointRuntimeInfo runtimeInfo = new AspectJAuditPointRuntimeInfo((JoinPoint)joinPoint);
        this.assertNotNull(currentPrincipal, "'principal' cannot be null.\n" + this.getDiagnosticInfo(runtimeInfo));
        this.assertNotNull(action, "'actionPerformed' cannot be null.\n" + this.getDiagnosticInfo(runtimeInfo));
        this.assertNotNull(applicationCode, "'applicationCode' cannot be null.\n" + this.getDiagnosticInfo(runtimeInfo));
        this.assertNotNull(actionDate, "'whenActionPerformed' cannot be null.\n" + this.getDiagnosticInfo(runtimeInfo));
        this.assertNotNull(clientInfo.getClientIpAddress(), "'clientIpAddress' cannot be null.\n" + this.getDiagnosticInfo(runtimeInfo));
        this.assertNotNull(clientInfo.getServerIpAddress(), "'serverIpAddress' cannot be null.\n" + this.getDiagnosticInfo(runtimeInfo));
        for (String auditableResource : auditableResources) {
            this.assertNotNull(auditableResource, "'resourceOperatedUpon' cannot be null.\n" + this.getDiagnosticInfo(runtimeInfo));
            AuditActionContext auditContext = new AuditActionContext(currentPrincipal, auditableResource, action, applicationCode, actionDate, clientInfo.getClientIpAddress(), clientInfo.getServerIpAddress(), clientInfo.getUserAgent());
            try {
                for (AuditTrailManager manager : this.auditTrailManagers) {
                    manager.setAuditFormat(this.auditFormat);
                    manager.record(auditContext);
                }
            }
            catch (Throwable e) {
                if (this.failOnAuditFailures) {
                    throw e;
                }
                LOG.error("Failed to record audit context for " + auditContext.getActionPerformed() + " and principal " + auditContext.getPrincipal(), e);
            }
        }
    }

    private String getDiagnosticInfo(AuditPointRuntimeInfo runtimeInfo) {
        return "Check the correctness of @Audit annotation at the following audit point: " + runtimeInfo.asString();
    }

    private void assertNotNull(Object o, String message) {
        if (o == null) {
            throw new IllegalArgumentException(message);
        }
    }

    private Exception wrapIfNecessary(Throwable t) {
        return t instanceof Exception ? (Exception)t : new Exception(t);
    }
}

