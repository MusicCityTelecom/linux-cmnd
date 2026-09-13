/*
 * Decompiled with CFR 0.152.
 */
package org.apereo.cas.configuration.model.support.mongo.serviceregistry;

import org.apereo.cas.configuration.model.support.mongo.SingleCollectionMongoDbProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-mongo-service-registry")
public class MongoDbServiceRegistryProperties
extends SingleCollectionMongoDbProperties {
    private static final long serialVersionUID = -227092724742371662L;

    public MongoDbServiceRegistryProperties() {
        this.setCollection("cas-service-registry");
    }
}

