/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.ArrayUtils
 *  org.apache.commons.lang3.StringUtils
 *  org.aspectj.lang.JoinPoint
 */
package org.apereo.inspektr.audit.spi.support;

import java.util.Arrays;
import java.util.stream.Collectors;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apereo.inspektr.audit.AuditTrailManager;
import org.apereo.inspektr.audit.spi.support.ReturnValueAsStringResourceResolver;
import org.aspectj.lang.JoinPoint;

public class ShortenedReturnValueAsStringAuditResourceResolver
extends ReturnValueAsStringResourceResolver {
    @Override
    public String[] resolveFrom(JoinPoint auditableTarget, Object retval) {
        String[] resources = super.resolveFrom(auditableTarget, retval);
        if (this.auditFormat == AuditTrailManager.AuditFormats.JSON) {
            return resources;
        }
        if (resources != null) {
            return Arrays.stream(resources).map(r -> StringUtils.abbreviate((String)r, (int)125)).collect(Collectors.toList()).toArray(ArrayUtils.EMPTY_STRING_ARRAY);
        }
        return null;
    }
}

