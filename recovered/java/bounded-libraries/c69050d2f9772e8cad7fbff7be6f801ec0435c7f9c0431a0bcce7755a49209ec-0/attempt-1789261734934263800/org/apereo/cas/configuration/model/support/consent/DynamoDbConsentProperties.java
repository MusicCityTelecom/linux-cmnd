/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.consent;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.Generated;
import org.apereo.cas.configuration.model.support.dynamodb.AbstractDynamoDbProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-consent-dynamodb")
@JsonFilter(value="DynamoDbConsentProperties")
public class DynamoDbConsentProperties
extends AbstractDynamoDbProperties {
    private static final long serialVersionUID = -9012260892496773705L;
    private String tableName = "DynamoDbConsentRecords";

    @Generated
    public String getTableName() {
        return this.tableName;
    }

    @Generated
    public DynamoDbConsentProperties setTableName(String tableName) {
        this.tableName = tableName;
        return this;
    }
}

