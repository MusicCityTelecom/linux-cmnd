/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.core.audit;

import lombok.Generated;
import org.apereo.cas.configuration.model.support.mongo.SingleCollectionMongoDbProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-audit-mongo")
public class AuditMongoDbProperties
extends SingleCollectionMongoDbProperties {
    private static final long serialVersionUID = 4940497540189318943L;
    private boolean asynchronous = true;

    public AuditMongoDbProperties() {
        this.setCollection("MongoDbCasAuditRepository");
    }

    @Generated
    public boolean isAsynchronous() {
        return this.asynchronous;
    }

    @Generated
    public AuditMongoDbProperties setAsynchronous(boolean asynchronous) {
        this.asynchronous = asynchronous;
        return this;
    }
}

