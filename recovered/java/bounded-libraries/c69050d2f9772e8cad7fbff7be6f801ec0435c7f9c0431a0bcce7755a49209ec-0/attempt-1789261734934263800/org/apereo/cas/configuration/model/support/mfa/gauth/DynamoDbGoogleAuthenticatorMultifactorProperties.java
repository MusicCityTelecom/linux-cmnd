/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.mfa.gauth;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.Generated;
import org.apereo.cas.configuration.model.support.dynamodb.AbstractDynamoDbProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-gauth-dynamodb")
@JsonFilter(value="DynamoDbGoogleAuthenticatorMultifactorProperties")
public class DynamoDbGoogleAuthenticatorMultifactorProperties
extends AbstractDynamoDbProperties {
    private static final long serialVersionUID = -1161683393319585262L;
    private String tableName = "DynamoDbGoogleAuthenticatorRepository";
    private String tokenTableName = "DynamoDbGoogleAuthenticatorTokenRepository";

    @Generated
    public String getTableName() {
        return this.tableName;
    }

    @Generated
    public String getTokenTableName() {
        return this.tokenTableName;
    }

    @Generated
    public DynamoDbGoogleAuthenticatorMultifactorProperties setTableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    @Generated
    public DynamoDbGoogleAuthenticatorMultifactorProperties setTokenTableName(String tokenTableName) {
        this.tokenTableName = tokenTableName;
        return this;
    }
}

