/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.mongo;

import lombok.Generated;
import org.apereo.cas.configuration.model.support.mongo.BaseMongoDbProperties;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-mongo-core")
public class SingleCollectionMongoDbProperties
extends BaseMongoDbProperties {
    private static final long serialVersionUID = 4869686250345657447L;
    @RequiredProperty
    private String collection;
    private boolean dropCollection;

    @Generated
    public String getCollection() {
        return this.collection;
    }

    @Generated
    public boolean isDropCollection() {
        return this.dropCollection;
    }

    @Generated
    public SingleCollectionMongoDbProperties setCollection(String collection) {
        this.collection = collection;
        return this;
    }

    @Generated
    public SingleCollectionMongoDbProperties setDropCollection(boolean dropCollection) {
        this.dropCollection = dropCollection;
        return this;
    }
}

