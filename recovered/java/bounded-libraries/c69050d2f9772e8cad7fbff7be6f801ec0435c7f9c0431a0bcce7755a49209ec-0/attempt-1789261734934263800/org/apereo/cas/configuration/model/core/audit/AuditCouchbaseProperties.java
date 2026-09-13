/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.core.audit;

import lombok.Generated;
import org.apereo.cas.configuration.model.support.couchbase.BaseCouchbaseProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-audit-couchbase")
public class AuditCouchbaseProperties
extends BaseCouchbaseProperties {
    private static final long serialVersionUID = 580545095591694L;
    private boolean asynchronous;

    @Generated
    public boolean isAsynchronous() {
        return this.asynchronous;
    }

    @Generated
    public AuditCouchbaseProperties setAsynchronous(boolean asynchronous) {
        this.asynchronous = asynchronous;
        return this;
    }
}

