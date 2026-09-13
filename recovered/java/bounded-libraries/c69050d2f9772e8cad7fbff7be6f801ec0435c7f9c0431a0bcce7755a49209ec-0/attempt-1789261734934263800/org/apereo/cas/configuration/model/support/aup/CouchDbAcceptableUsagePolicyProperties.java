/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 */
package org.apereo.cas.configuration.model.support.aup;

import com.fasterxml.jackson.annotation.JsonFilter;
import org.apereo.cas.configuration.model.support.couchdb.BaseAsynchronousCouchDbProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-aup-couchdb")
@JsonFilter(value="CouchDbAcceptableUsagePolicyProperties")
public class CouchDbAcceptableUsagePolicyProperties
extends BaseAsynchronousCouchDbProperties {
    private static final long serialVersionUID = 1323894615409106853L;

    public CouchDbAcceptableUsagePolicyProperties() {
        this.setDbName("acceptable_usage_policy");
    }
}

