/*
 * Decompiled with CFR 0.152.
 */
package org.apereo.inspektr.audit.spi.support;

import java.util.ArrayList;
import org.apereo.inspektr.audit.AuditTrailManager;
import org.apereo.inspektr.audit.spi.support.AbstractAuditResourceResolver;

public class ParametersAsStringResourceResolver
extends AbstractAuditResourceResolver {
    @Override
    protected String[] createResource(Object[] args) {
        ArrayList<String> stringArgs = new ArrayList<String>();
        for (Object arg : args) {
            stringArgs.add(this.toResourceString(arg));
        }
        return stringArgs.toArray(new String[stringArgs.size()]);
    }

    public String toResourceString(Object arg) {
        if (this.auditFormat == AuditTrailManager.AuditFormats.JSON) {
            return AuditTrailManager.toJson(arg);
        }
        return arg.toString();
    }
}

