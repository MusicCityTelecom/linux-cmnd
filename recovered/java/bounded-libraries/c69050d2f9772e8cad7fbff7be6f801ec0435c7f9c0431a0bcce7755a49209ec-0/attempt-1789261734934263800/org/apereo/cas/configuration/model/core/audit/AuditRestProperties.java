/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.core.audit;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.Generated;
import org.apereo.cas.configuration.model.RestEndpointProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-audit-rest", automated=true)
@JsonFilter(value="AuditRestProperties")
public class AuditRestProperties
extends RestEndpointProperties {
    private static final long serialVersionUID = 3893437775090452831L;
    private boolean asynchronous = true;

    @Generated
    public boolean isAsynchronous() {
        return this.asynchronous;
    }

    @Generated
    public AuditRestProperties setAsynchronous(boolean asynchronous) {
        this.asynchronous = asynchronous;
        return this;
    }
}

