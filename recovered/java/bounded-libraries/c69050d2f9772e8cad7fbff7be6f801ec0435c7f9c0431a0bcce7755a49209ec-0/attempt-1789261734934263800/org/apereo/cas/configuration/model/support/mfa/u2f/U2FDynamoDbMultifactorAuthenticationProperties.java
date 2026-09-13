/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.mfa.u2f;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.Generated;
import org.apereo.cas.configuration.model.support.dynamodb.AbstractDynamoDbProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-u2f-dynamodb")
@JsonFilter(value="U2FDynamoDbMultifactorAuthenticationProperties")
public class U2FDynamoDbMultifactorAuthenticationProperties
extends AbstractDynamoDbProperties {
    private static final long serialVersionUID = 612447148774854955L;
    private String tableName = "DynamoDbU2FDevices";

    @Generated
    public String getTableName() {
        return this.tableName;
    }

    @Generated
    public U2FDynamoDbMultifactorAuthenticationProperties setTableName(String tableName) {
        this.tableName = tableName;
        return this;
    }
}

