/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.core.events;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.Generated;
import org.apereo.cas.configuration.model.support.dynamodb.AbstractDynamoDbProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-events-dynamodb")
@JsonFilter(value="DynamoDbEventsProperties")
public class DynamoDbEventsProperties
extends AbstractDynamoDbProperties {
    private static final long serialVersionUID = 612447148774854955L;
    private String tableName = "DynamoDbCasEvents";

    @Generated
    public String getTableName() {
        return this.tableName;
    }

    @Generated
    public DynamoDbEventsProperties setTableName(String tableName) {
        this.tableName = tableName;
        return this;
    }
}

