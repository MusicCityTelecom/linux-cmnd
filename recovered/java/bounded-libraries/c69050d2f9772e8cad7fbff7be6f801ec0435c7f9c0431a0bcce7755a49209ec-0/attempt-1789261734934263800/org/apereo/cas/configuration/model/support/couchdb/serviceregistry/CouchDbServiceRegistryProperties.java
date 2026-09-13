/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 */
package org.apereo.cas.configuration.model.support.couchdb.serviceregistry;

import com.fasterxml.jackson.annotation.JsonFilter;
import org.apereo.cas.configuration.model.support.couchdb.BaseCouchDbProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-couchdb-service-registry")
@JsonFilter(value="CouchDbServiceRegistryProperties")
public class CouchDbServiceRegistryProperties
extends BaseCouchDbProperties {
    private static final long serialVersionUID = -5101551655756163621L;

    public CouchDbServiceRegistryProperties() {
        this.setDbName("service_registry");
    }
}

