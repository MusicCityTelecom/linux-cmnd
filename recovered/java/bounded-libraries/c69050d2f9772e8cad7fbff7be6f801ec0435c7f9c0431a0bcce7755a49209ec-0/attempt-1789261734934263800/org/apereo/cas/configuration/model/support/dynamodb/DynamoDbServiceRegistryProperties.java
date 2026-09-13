/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.dynamodb;

import lombok.Generated;
import org.apereo.cas.configuration.model.support.dynamodb.AbstractDynamoDbProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-dynamodb-service-registry")
public class DynamoDbServiceRegistryProperties
extends AbstractDynamoDbProperties {
    private static final long serialVersionUID = 809653348774854955L;
    private String tableName = "DynamoDbCasServices";

    @Generated
    public String getTableName() {
        return this.tableName;
    }

    @Generated
    public DynamoDbServiceRegistryProperties setTableName(String tableName) {
        this.tableName = tableName;
        return this;
    }
}

