/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.couchdb;

import lombok.Generated;
import org.apereo.cas.configuration.model.support.couchdb.BaseCouchDbProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-couchdb-core")
public abstract class BaseAsynchronousCouchDbProperties
extends BaseCouchDbProperties {
    private static final long serialVersionUID = -7920471433876478891L;
    private boolean asynchronous = true;

    @Generated
    public boolean isAsynchronous() {
        return this.asynchronous;
    }

    @Generated
    public BaseAsynchronousCouchDbProperties setAsynchronous(boolean asynchronous) {
        this.asynchronous = asynchronous;
        return this;
    }
}

