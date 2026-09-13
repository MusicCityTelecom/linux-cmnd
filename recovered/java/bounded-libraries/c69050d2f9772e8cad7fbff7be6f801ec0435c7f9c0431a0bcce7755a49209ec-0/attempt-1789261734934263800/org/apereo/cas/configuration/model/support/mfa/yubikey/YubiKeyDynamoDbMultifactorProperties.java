/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.mfa.yubikey;

import lombok.Generated;
import org.apereo.cas.configuration.model.support.dynamodb.AbstractDynamoDbProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-yubikey-dynamodb")
public class YubiKeyDynamoDbMultifactorProperties
extends AbstractDynamoDbProperties {
    private static final long serialVersionUID = 321667148774858855L;
    private String tableName = "DynamoDbYubiKeyDevices";

    @Generated
    public String getTableName() {
        return this.tableName;
    }

    @Generated
    public YubiKeyDynamoDbMultifactorProperties setTableName(String tableName) {
        this.tableName = tableName;
        return this;
    }
}

