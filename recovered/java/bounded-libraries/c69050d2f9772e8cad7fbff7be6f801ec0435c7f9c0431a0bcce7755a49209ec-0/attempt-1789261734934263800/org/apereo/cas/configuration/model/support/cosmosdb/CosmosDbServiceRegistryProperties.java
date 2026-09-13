/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.cosmosdb;

import lombok.Generated;
import org.apereo.cas.configuration.model.support.cosmosdb.BaseCosmosDbProperties;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-cosmosdb-service-registry")
public class CosmosDbServiceRegistryProperties
extends BaseCosmosDbProperties {
    private static final long serialVersionUID = 6194689836396653458L;
    @RequiredProperty
    private String container = "CasCosmosDbServiceRegistry";

    @Generated
    public String getContainer() {
        return this.container;
    }

    @Generated
    public CosmosDbServiceRegistryProperties setContainer(String container) {
        this.container = container;
        return this;
    }
}

