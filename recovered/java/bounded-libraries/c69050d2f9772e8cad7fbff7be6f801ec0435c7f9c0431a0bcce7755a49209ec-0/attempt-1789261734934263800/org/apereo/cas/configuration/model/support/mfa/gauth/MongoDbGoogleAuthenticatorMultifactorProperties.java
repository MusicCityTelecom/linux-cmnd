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
import org.apereo.cas.configuration.model.support.mongo.SingleCollectionMongoDbProperties;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-gauth-mongo")
@JsonFilter(value="MongoDbGoogleAuthenticatorMultifactorProperties")
public class MongoDbGoogleAuthenticatorMultifactorProperties
extends SingleCollectionMongoDbProperties {
    private static final long serialVersionUID = -200556119517414696L;
    @RequiredProperty
    private String tokenCollection;

    public MongoDbGoogleAuthenticatorMultifactorProperties() {
        this.setCollection("MongoDbGoogleAuthenticatorRepository");
        this.setTokenCollection("MongoDbGoogleAuthenticatorTokenRepository");
    }

    @Generated
    public String getTokenCollection() {
        return this.tokenCollection;
    }

    @Generated
    public MongoDbGoogleAuthenticatorMultifactorProperties setTokenCollection(String tokenCollection) {
        this.tokenCollection = tokenCollection;
        return this;
    }
}

