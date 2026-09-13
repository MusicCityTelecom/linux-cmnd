/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 */
package org.apereo.cas.configuration.model.core.monitor;

import com.fasterxml.jackson.annotation.JsonFilter;
import org.apereo.cas.configuration.model.support.mongo.BaseMongoDbProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-mongo-monitor")
@JsonFilter(value="MongoDbMonitorProperties")
public class MongoDbMonitorProperties
extends BaseMongoDbProperties {
    private static final long serialVersionUID = -1918436901491275547L;
}

