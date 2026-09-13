/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 */
package org.apereo.cas.configuration.model.support.mfa.trusteddevice;

import com.fasterxml.jackson.annotation.JsonFilter;
import org.apereo.cas.configuration.model.support.mongo.SingleCollectionMongoDbProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-trusted-mfa-mongo")
@JsonFilter(value="MongoDbTrustedDevicesMultifactorProperties")
public class MongoDbTrustedDevicesMultifactorProperties
extends SingleCollectionMongoDbProperties {
    private static final long serialVersionUID = 4940497540189318943L;

    public MongoDbTrustedDevicesMultifactorProperties() {
        this.setCollection("MongoDbCasTrustedAuthnMfaRepository");
    }
}

