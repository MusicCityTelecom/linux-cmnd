/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 */
package org.apereo.cas.configuration.model.core.events;

import com.fasterxml.jackson.annotation.JsonFilter;
import org.apereo.cas.configuration.model.support.mongo.SingleCollectionMongoDbProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-events-mongo")
@JsonFilter(value="MongoDbEventsProperties")
public class MongoDbEventsProperties
extends SingleCollectionMongoDbProperties {
    private static final long serialVersionUID = -1918436901491275547L;

    public MongoDbEventsProperties() {
        this.setCollection("MongoDbCasEventRepository");
    }
}

