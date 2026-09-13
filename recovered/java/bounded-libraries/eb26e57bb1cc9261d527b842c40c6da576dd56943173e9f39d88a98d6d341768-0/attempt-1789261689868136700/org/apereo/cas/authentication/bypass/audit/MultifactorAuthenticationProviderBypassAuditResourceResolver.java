/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apache.commons.lang3.ArrayUtils
 *  org.apereo.cas.authentication.Authentication
 *  org.apereo.cas.authentication.MultifactorAuthenticationProvider
 *  org.apereo.cas.util.AopUtils
 *  org.apereo.inspektr.audit.AuditTrailManager$AuditFormats
 *  org.apereo.inspektr.audit.spi.AuditResourceResolver
 *  org.aspectj.lang.JoinPoint
 */
package org.apereo.cas.authentication.bypass.audit;

import java.util.HashMap;
import lombok.Generated;
import org.apache.commons.lang3.ArrayUtils;
import org.apereo.cas.authentication.Authentication;
import org.apereo.cas.authentication.MultifactorAuthenticationProvider;
import org.apereo.cas.util.AopUtils;
import org.apereo.inspektr.audit.AuditTrailManager;
import org.apereo.inspektr.audit.spi.AuditResourceResolver;
import org.aspectj.lang.JoinPoint;

public class MultifactorAuthenticationProviderBypassAuditResourceResolver
implements AuditResourceResolver {
    protected AuditTrailManager.AuditFormats auditFormat = AuditTrailManager.AuditFormats.DEFAULT;

    public String[] resolveFrom(JoinPoint joinPoint, Object object) {
        JoinPoint jp = AopUtils.unWrapJoinPoint((JoinPoint)joinPoint);
        Object[] args = jp.getArgs();
        if (args != null) {
            Authentication authn = (Authentication)args[0];
            MultifactorAuthenticationProvider provider = (MultifactorAuthenticationProvider)args[2];
            HashMap<String, Object> values = new HashMap<String, Object>();
            values.put("principal", authn.getPrincipal().getId());
            values.put("provider", provider.getId());
            values.put("execution", object);
            return new String[]{this.toResourceString(values)};
        }
        return ArrayUtils.EMPTY_STRING_ARRAY;
    }

    public String[] resolveFrom(JoinPoint target, Exception exception) {
        HashMap<String, String> values = new HashMap<String, String>();
        values.put("target", target.getTarget().toString());
        values.put("exception", exception.getMessage());
        return new String[]{this.toResourceString(values)};
    }

    protected String toResourceString(Object object) {
        return this.auditFormat.serialize(object);
    }

    @Generated
    public void setAuditFormat(AuditTrailManager.AuditFormats auditFormat) {
        this.auditFormat = auditFormat;
    }
}

