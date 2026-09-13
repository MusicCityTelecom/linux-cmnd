/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 */
package org.apereo.cas.configuration.model.support.aup;

import com.fasterxml.jackson.annotation.JsonFilter;
import org.apereo.cas.configuration.model.support.mongo.SingleCollectionMongoDbProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-aup-mongo")
@JsonFilter(value="MongoDbAcceptableUsagePolicyProperties")
public class MongoDbAcceptableUsagePolicyProperties
extends SingleCollectionMongoDbProperties {
    private static final long serialVersionUID = -1918436901491275547L;

    public MongoDbAcceptableUsagePolicyProperties() {
        this.setCollection("MongoDbCasAUPRepository");
    }
}

