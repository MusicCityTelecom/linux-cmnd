/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.mfa.webauthn;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.Generated;
import org.apereo.cas.configuration.model.support.dynamodb.AbstractDynamoDbProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-webauthn-dynamodb")
@JsonFilter(value="WebAuthnDynamoDbMultifactorProperties")
public class WebAuthnDynamoDbMultifactorProperties
extends AbstractDynamoDbProperties {
    private static final long serialVersionUID = -2261683393319585262L;
    private String tableName = "DynamoDbCasWebAuthnRecords";

    @Generated
    public String getTableName() {
        return this.tableName;
    }

    @Generated
    public WebAuthnDynamoDbMultifactorProperties setTableName(String tableName) {
        this.tableName = tableName;
        return this;
    }
}

