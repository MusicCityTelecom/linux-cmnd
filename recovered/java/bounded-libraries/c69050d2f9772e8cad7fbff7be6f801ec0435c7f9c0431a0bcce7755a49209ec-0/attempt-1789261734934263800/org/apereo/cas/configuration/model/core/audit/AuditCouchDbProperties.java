/*
 * Decompiled with CFR 0.152.
 */
package org.apereo.cas.configuration.model.core.audit;

import org.apereo.cas.configuration.model.support.couchdb.BaseAsynchronousCouchDbProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-audit-couchdb")
public class AuditCouchDbProperties
extends BaseAsynchronousCouchDbProperties {
    private static final long serialVersionUID = -5607529769937667881L;

    public AuditCouchDbProperties() {
        this.setDbName("audit");
    }
}

